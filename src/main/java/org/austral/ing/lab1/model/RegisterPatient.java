package org.austral.ing.lab1.model;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.austral.ing.lab1.json.JsonParser.fromJson;

public class RegisterPatient {

    private final String password;
    private final String name;
    private final String lastname;
    private final int contact;
    private final int dni;
    private final Date dateOfBirth;
    private final String lifeInsurance;

    public RegisterPatient(String name, String lastname, String password, int dni, String lifeInsurance, Date dateOfBirth, int contact) {
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.contact = contact;
        this.dni = dni;
        this.dateOfBirth = dateOfBirth;
        this.lifeInsurance = lifeInsurance;
    }

    public static RegisterPatient createFromJson(String body){
        return fromJson(body, RegisterPatient.class);
    }

    public static RegisterPatient createFromBody(String body){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return RegisterPatient.create(
                params.getValues("name"),
                params.getValues("lastname"),
                params.getValues("password"),
                params.getValues("dni"),
                params.getValues("lifeInsurance"),
                params.getValues("dateOfBirth"),
                params.getValues("contact"));
    }

    private static RegisterPatient create(List<String> names, List<String> lastname, List<String> password, List<String> dnis, List<String> lifeInsurance, List<String> dateOfBirth, List<String> contacts) {

        String name = names.get(0);
        int dni = Integer.parseInt(dnis.get(0));
        int contact = Integer.parseInt(contacts.get(0));

        return new RegisterPatient(name,lastname.get(0),password.get(0), dni,lifeInsurance.get(0), parseDate(dateOfBirth), contact);
    }

    private static Date parseDate(List<String> dateOfBirth) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
         return s.parse(dateOfBirth.get(0));
        } catch (ParseException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
        }
    }

    public int getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }


    public int getDni() {
        return dni;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getLifeInsurance() {
        return lifeInsurance;
    }

    public String getPassword() {
        return password;
    }
}
