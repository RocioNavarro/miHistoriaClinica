package org.austral.ing.lab1;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.austral.ing.lab1.model.*;
import org.austral.ing.lab1.model.LoginPatientRequest;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import static java.util.concurrent.TimeUnit.MINUTES;
import static spark.Spark.*;


public class Routes {

public static final String Register_Template_Patient = "RegisterPatient.ftl";
public static final String Register_Template_Medic = "RegisterMedic.ftl";
public static final String LoginP_Template = "LoginPatient.ftl";
public static final String LoginM_Template = "LoginMedic.ftl";
private static final String HomeM_Template = "StartingHomePage.ftl";

public static final String LoginP_Route = "/login_patient";
public static final String LoginM_Route = "/login_medic";
public static final String RegisterPatient_Route = "/register_patient";
public static final String RegisterMedic_Route = "/register_medic";
public static final String Home_Route = "/home";
public static final String Logout_route = "/logout";
public static final String Patients_List ="/list_patients";

private HCSystem system;

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

        get(RegisterPatient_Route, (request, response) -> render(Register_Template_Patient));
        post(RegisterPatient_Route, (request, response) -> {
            final RegisterPatient form = RegisterPatient.createFromBody(request.body());

            final Optional<Patient> patient = system.registerPatient(form);

            if (patient.isPresent()){
                response.redirect("/login_patient");
                return halt();
            } else {
                final Map<String,Object> model = Map.of("message", "Patient already exists");
                return render(model, Register_Template_Patient);
            }
        });
        get(RegisterMedic_Route, (request, response) -> {
            response.redirect("/registerMedic.html");
            return halt();});

        post(RegisterMedic_Route, (request, response) -> {
            final RegisterMedic form = RegisterMedic.createFromBody(request.body());

            final Optional<Medic> medic = system.registerMedic(form);

            if (medic.isPresent()){
                response.redirect("/login_medic?ok");
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Medic already exists");
                return render(model, Register_Template_Medic);
            }
        });

        get(LoginP_Route, (request, response) -> {
            response.redirect("/LoginPatient.html");
            return halt();
        });

        post(LoginP_Route, (request, response) -> {
            final LoginPatientRequest form = LoginPatientRequest.createFromBody(request.body());
            if (system.checkLoginPatient(form).isPresent()) {
                response.redirect("/home_patient");
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Not a registered Patient, Please try again");
                redirect.get(LoginP_Route, "/LoginPatient.html");
                return halt();
                }
        });
        get(LoginM_Route, (request, response) -> render(LoginM_Template));
        post(LoginM_Route, (request, response) -> {
            final LoginMedicRequest form = LoginMedicRequest.createFromBody(request.body());
            if (system.checkLoginMedic(form).isPresent()) {
                response.redirect(Home_Route);
                return halt();
            }else{
                final Map<String,Object> model = Map.of("message", "Not a registered Medic, Please try again");
                return render(model, LoginM_Template);
            }
        });
        get(Home_Route, (request, response) -> {
            response.redirect("/home.html");
        return halt();});


        }

        private final Cache<Token, Integer> dniByToken = CacheBuilder.newBuilder()
                .expireAfterAccess(30, MINUTES)
                .build();

        private Optional<Token> authenticate(LoginPatientRequest req) {
            return system.findByDni(req.getDni()).flatMap(foundUser -> {
                if (system.validPassword(req.getPassword(), foundUser)) {
                    final Token token = system.createToken();
                    dniByToken.put(token, foundUser.getDni());
                    return Optional.of(token);
                } else {
                    return Optional.empty();
                }
            });
    }
    //Crear un meteodo de validacion despues de recibir un form del medico
    private Object render(Map<String, Object> model, String template){
            return new FreeMarkerEngine().render(new ModelAndView(model,template));
    }
    private Object render(String template){
            return new FreeMarkerEngine().render(new ModelAndView(Collections.emptyMap(), template));
    }

    private Optional<Patient> getAuthenticatedPatient(Request request) {
        final int dni = request.session().attribute("patient");
        return Optional.of(dni).flatMap(system::findByDni);
    }


}
