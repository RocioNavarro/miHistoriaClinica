package org.austral.ing.lab1.model;

import org.austral.ing.lab1.json.JsonParser;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.List;

public class LoginPatientRequest {
        private final int dni;
        private final String password;

        public LoginPatientRequest(int dni, String password) {
            this.dni = dni;
            this.password = password;
        }

        public static LoginPatientRequest create(List<String> dni, List<String> password) {
            int dni1 = Integer.parseInt(dni.get(0));
            return new LoginPatientRequest(dni1, password.get(0));
        }

        public static LoginPatientRequest createFromJson(String body) {
            return JsonParser.fromJson(body, LoginPatientRequest.class);
        }

    public static LoginPatientRequest createFromBody(String body) {
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return LoginPatientRequest.create(
                params.getValues("dni"),
                params.getValues("password"));
    }

    public String getPassword() {
            return password;
        }

        public int getDni() {
            return dni;
        }
}
