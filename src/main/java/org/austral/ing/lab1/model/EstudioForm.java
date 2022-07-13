package org.austral.ing.lab1.model;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EstudioForm {

    private String name;
    private Date startingDate;
    private String status;
    private String result;
    private String observation;

    public EstudioForm(String name, Date startingDate, String status, String result, String observation) {
        this.name = name;
        this.startingDate = startingDate;
        this.status = status;
        this.result = result;
        this.observation = observation;
    }

    public static EstudioForm createFromBody(String body){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return EstudioForm.create(
                params.getValues("name"),
                params.getValues("date"),
                params.getValues("status"),
                params.getValues("result"),
                params.getValues("observation"));
    }

    private static EstudioForm create(List<String> name, List<String> date, List<String> status, List<String> result, List<String> observation) {

        Date date1 = parseDate(date);

        return new EstudioForm(name.get(0),date1,status.get(0),result.get(0),observation.get(0));
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
