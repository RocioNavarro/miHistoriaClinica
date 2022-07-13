package org.austral.ing.lab1.model;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MedicamentoForm {

    private String name;
    private String frecuency;
    private Date startingDate;
    private String status;
    private String observation;

    public MedicamentoForm(String name, String frecuency, Date startingDate, String status, String observation) {
        this.name = name;
        this.frecuency = frecuency;
        this.startingDate = startingDate;
        this.status = status;
        this.observation = observation;
    }

    public static MedicamentoForm createFromBody(String body){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return MedicamentoForm.create(
                params.getValues("name"),
                params.getValues("frecuency"),
                params.getValues("date"),
                params.getValues("status"),
                params.getValues("observation"));
    }

    private static MedicamentoForm create(List<String> name, List<String> frecuency, List<String> date, List<String> status, List<String> observation) {

        Date date1 = parseDate(date);

        return new MedicamentoForm(name.get(0),frecuency.get(0), date1, status.get(0), observation.get(0));
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

}
