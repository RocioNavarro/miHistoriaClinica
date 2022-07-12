package org.austral.ing.lab1;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.austral.ing.lab1.json.JsonParser;
import org.austral.ing.lab1.model.*;
import org.austral.ing.lab1.model.LoginPatientRequest;
import spark.Request;

import java.util.*;

import static java.util.concurrent.TimeUnit.MINUTES;
import static spark.Spark.*;


public class Routes{


public static final String LoginP_Route = "/login_patient";
public static final String LoginM_Route = "/login_medic";
public static final String RegisterPatient_Route = "/register_patient";
public static final String RegisterMedic_Route = "/register_medic";
public static final String Home_Route = "/home";
public static final String Logout_route = "/logout";
public static final String Patients_List ="/list_patients";


private HCSystem system;
    JsonParser jsonParser = new JsonParser();
    public void create(HCSystem system){
    this.system = system;
    routes();
}
    private void routes(){
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        });

        options("/*",(request, response) -> {
            response.status(200);
            return "ok";
        });

        get(RegisterPatient_Route, (request, response) -> {response.redirect("registerPatient.html");
        return halt();});
        post(RegisterPatient_Route, (request, response) -> {
            final RegisterPatient form = RegisterPatient.createFromBody(request.body());

            final Optional<Patient> patient = system.registerPatient(form);

            if (patient.isPresent()){
                response.redirect("/login_patient");
                return halt();
            } else {
                final Map<String,Object> model = Map.of("message", "Patient already exists");
                response.redirect(RegisterPatient_Route);
                return halt();
            }
        });
        get(RegisterMedic_Route, (request, response) -> {
            response.redirect("/registerMedic.html");
            return halt();
        });
        post(RegisterMedic_Route, (request, response) -> {
            final RegisterMedic form = RegisterMedic.createFromBody(request.body());

            final Optional<Medic> medic = system.registerMedic(form);

            if (medic.isPresent()){
                response.redirect("/login_medic?ok");
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Medic already exists");
                response.redirect("/registerMedic.html");
                return halt();
            }
        });

        get(LoginP_Route, (request, response) -> {
            response.redirect("/LoginPatient.html");
            return halt();
        });

        post(LoginP_Route, (request, response) -> {
            final LoginPatientRequest form = LoginPatientRequest.createFromBody(request.body());
            if (system.checkLoginPatient(form).isPresent()) {
                authenticatePatient(form);
                response.redirect("/sesion_paciente");
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Not a registered Patient, Please try again");
                redirect.get(LoginP_Route, "/LoginPatient.html");
                return halt();
                }
        });
        get(LoginM_Route, (request, response) -> {response.redirect("LoginMedic.html");
        return halt();});
        post(LoginM_Route, (request, response) -> {
            final LoginMedicRequest form = LoginMedicRequest.createFromBody(request.body());
            if (system.checkLoginMedic(form).isPresent()) {
                authenticateMedic(form);
                response.redirect("/sesion_medico");
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Not a registered Medic, Please try again");
                response.redirect("/LoginMedic.html");
                return halt();
            }
        });

        get(Logout_route,(request, response) -> {logout(request);
        response.redirect(Home_Route);
        return halt();});


        get(Home_Route, (request, response) -> {
            response.redirect("/home.html");
        return halt();});

        get("/sesion_medico", (request, response) -> {
            response.redirect("/SesionMedico.html");
        return halt();
        });

        get("/sesion_paciente", (request, response) -> {
            response.redirect("/SesionPaciente.html");
            return halt();
        });


        post("/medicAddPatient", (request, response) -> {
            Optional<Medic> medic = getAuthenticatedMedic(request);
            Optional<Patient> patient = system.findByDni(Integer.parseInt(request.body()));
            return halt();
        });


        get("/medics_list", (request, response) -> {
            response.redirect("/MedicsList.html");
            Optional<Patient> patient =(getAuthenticatedPatient(request));
            if (isAuthorizedPatient(request)){
               Patient patient1 = patient.get();
            response.type("application/json");
            return JsonParser.toJson(system.getMedics(patient1));
            }
            return halt();
        });

        get(Patients_List, (request, response) -> {
            Optional<Medic> medic =(getAuthenticatedMedic(request));
            if (isAuthorizedMedic(request)){
                Medic medic1 = medic.get();
                response.type("application/json");
                return JsonParser.toJson(system.getPatients(medic1));
            }
            return halt();
        });

    }

    private final Cache<Token, String> dniByToken = CacheBuilder.newBuilder()
                .expireAfterAccess(30, MINUTES)
                .build();

    private final Cache<Token, String> matriculaByToken = CacheBuilder.newBuilder()
            .expireAfterAccess(30, MINUTES)
            .build();

        private Optional<Token> authenticatePatient(LoginPatientRequest req) {
            return system.findByDni(req.getDni()).flatMap(foundUser -> {
                if (system.validPassword(req.getPassword(), foundUser)) {
                    final Token token = system.createToken();
                    dniByToken.put(token, foundUser.getDniString());
                    return Optional.of(token);
                } else {
                    return Optional.empty();
                }
            });
    }
    //Crear un meteodo de validacion despues de recibir un form del medico
    private Optional<Token> authenticateMedic(LoginMedicRequest req) {
        return system.findByMatricula(req.getMatricula()).flatMap(foundUser -> {
            if (system.validPassword(req.getPassword(), foundUser)) {
                final Token token = system.createToken();
                matriculaByToken.put(token, foundUser.getMatriculaString());
                return Optional.of(token);
            } else {
                return Optional.empty();
            }
        });
    }

    private boolean isAuthorizedMedic(Request request) {
        return getToken(request).map(this::isAuthenticatedMedic).orElse(false);
    }
    private boolean isAuthorizedPatient(Request request) {
        return getToken(request).map(this::isAuthenticatedPatient).orElse(false);
    }

    private static Optional<String> getToken(Request request) {
        return Optional.ofNullable(request.headers("Authorized")).map(Routes::getTokenFromHeader);
    }

    private static String getTokenFromHeader(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    private boolean isAuthenticatedPatient(String token) {
        return dniByToken.getIfPresent(token) != null;
    }

    private boolean isAuthenticatedMedic(String token) {
        return matriculaByToken.getIfPresent(token) != null;
    }

    private Optional<Patient> getAuthenticatedPatient(Request req) {
        return getToken(req)
                .map(dniByToken::getIfPresent)
                .flatMap(dni -> system.findByDni(Integer.parseInt(dni)));
    }
    private Optional<Medic> getAuthenticatedMedic(Request req) {
        return getToken(req)
                .map(matriculaByToken::getIfPresent)
                .flatMap(matricula -> system.findByMatricula(Long.parseLong(matricula)));
    }

    private void logout(Request request){
        request.session().removeAttribute("patient");
    }


}
