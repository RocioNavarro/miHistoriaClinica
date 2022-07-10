package org.austral.ing.lab1.model;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.List;

import static org.austral.ing.lab1.json.JsonParser.fromJson;

public class RegisterMedic {
    private final Long matricula;
    private final String password;
    private final String name;
    private final String lastname;
    private final int contact;
    private final String speciality;

    public RegisterMedic(String name, String lastname, String password, Long matricula, String speciality, int contact) {
        this.matricula = matricula;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.contact = contact;
        this.speciality = speciality;
    }

    public static RegisterMedic createFromJson(String body) {
        return fromJson(body, RegisterMedic.class);
    }

    public static RegisterMedic createFromBody(String body){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return RegisterMedic.create(
                params.getValues("name"),
                params.getValues("lastname"),
                params.getValues("password"),
                params.getValues("matricula"),
                params.getValues("especialidad"),
                params.getValues("contact"));
    }

    private static RegisterMedic create(List<String> names, List<String> lastnames, List<String> passwords, List<String> matriculas, List<String> especialidades, List<String> contacts) {
        String name = names.get(0);
        String lastname = lastnames.get(0);
        String password = passwords.get(0);
        Long matricula = Long.parseLong(matriculas.get(0));
        String especialidad = especialidades.get(0);
        int contact = Integer.parseInt(contacts.get(0));

        return new RegisterMedic(name,lastname,password,matricula,especialidad,contact);
    }

    public Long getMatricula() {
        return matricula;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getContact() {
        return contact;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPassword() {
        return password;
    }
}
