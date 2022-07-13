package org.austral.ing.lab1;
import org.austral.ing.lab1.json.JsonParser;
import org.austral.ing.lab1.model.*;
import org.austral.ing.lab1.model.LoginPatientRequest;
import spark.Request;
import spark.Session;

import java.util.*;

import static spark.Spark.*;


public class Routes{

    private static final String Patient_ID= "patient";
    private static final String Medic_ID = "medic";


public static final String LoginP_Route = "/login_patient";
public static final String LoginM_Route = "/login_medic";
public static final String RegisterPatient_Route = "/register_patient";
public static final String RegisterMedic_Route = "/register_medic";
public static final String Home_Route = "/home";
public static final String Logout_route = "/logout";
public static final String Patients_List ="/list_patients";


private HCSystem system;
private JsonParser jsonParser = new JsonParser();

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
            Optional<Patient> patient = system.checkLoginPatient(form);
            if (patient.isPresent()) {
                setAuthenticatedPatient(request,patient.get());
                response.redirect("/sesion_paciente");
                return halt();
            }else{
                redirect.get(LoginP_Route, "/LoginPatient.html");
                return halt();
                }
        });
        get(LoginM_Route, (request, response) -> {response.redirect("LoginMedic.html");
        return halt();});
        post(LoginM_Route, (request, response) -> {
            final LoginMedicRequest form = LoginMedicRequest.createFromBody(request.body());
            Optional<Medic> medic = system.checkLoginMedic(form);
            if (medic.isPresent()) {
                setAuthenticatedMedic(request,medic.get());
                response.redirect("/sesion_medico");
                return halt();
            }else{
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

        get("/medicAddPatient", (request, response) -> {
                response.redirect("pruebaADD.html");
        return halt();});

        post("/medicAddPatient", (request, response) -> {
            Optional<Medic> medic = getAuthenticatedMedic(request);
            Optional<Patient> patient = system.findByDni(JsonParser.fromJson(request.body()));
            system.linkPM(medic,patient);
            return halt();
        });


        get("/medics_list", (request, response) -> {
            Optional<Patient> patient =(getAuthenticatedPatient(request));
            if (patient.isPresent()){
               Patient patient1 = patient.get();
            response.type("application/json");
            return JsonParser.toJson(system.getMedics(patient1));
            }
            return halt();
        });

        get(Patients_List, (request, response) -> {
            Optional<Medic> medic =(getAuthenticatedMedic(request));
            if (medic.isPresent()){
                Medic medic1 = medic.get();
                response.type("application/json");
                return JsonParser.toJson(system.listPatients());
            }
            return halt();
        });

      //  get("/historia_clinica", (request, response) -> )

    }

    public void setAuthenticatedPatient(Request request, Patient patient){
        Session session = request.session(true);
        session.attribute(Patient_ID, patient.getMedicalHistoryNumber());
    }

    public Optional<Patient> getAuthenticatedPatient(Request request){
        final  int patientMHN = request.session().attribute(Patient_ID);
        return Optional.ofNullable(patientMHN).flatMap(system::findByMHN);
    }

    public void setAuthenticatedMedic(Request request, Medic medic){
        Session session = request.session(true);
        session.attribute(Medic_ID, medic.getMatricula());
    }

    public Optional<Medic> getAuthenticatedMedic(Request request){
        final Long medicMatricula = request.session().attribute(Medic_ID);
        return Optional.ofNullable(medicMatricula).flatMap(system::findByMatricula);
    }


    /*   private final Cache<Token, String> dniByToken = CacheBuilder.newBuilder()
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
    }*/

    private void logout(Request request){
        request.session().removeAttribute("patient");
    }


}
