package org.austral.ing.lab1.model;

import org.austral.ing.lab1.json.JsonParser;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.List;

public class LoginMedicRequest {
    private final Long matricula;
    private final String password;

    public LoginMedicRequest(Long matricula, String password) {
        this.matricula = matricula;
        this.password = password;
    }

    public static LoginMedicRequest create(List<String> matricula, List<String> password) {
        Long dni1 = Long.parseLong(matricula.get(0));
        return new LoginMedicRequest(dni1, password.get(0));
    }

    public static LoginMedicRequest createFromJson(String body) {
        return JsonParser.fromJson(body, LoginMedicRequest.class);
    }

    public static LoginMedicRequest createFromBody(String body) {
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return LoginMedicRequest.create(
                params.getValues("matricula"),
                params.getValues("password"));
    }

    public String getPassword() {
        return password;
    }

    public Long getMatricula() {
        return matricula;
    }
}
