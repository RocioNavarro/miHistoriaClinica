package org.austral.ing.lab1.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Estudio {
    @Id
    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "FECHA_DE_REALIZACION")
    private Date startingDate;

    @Column(name = "ESTADO")
    private String currentState;

    @Column(name = "RESULTADO")
    private String result;

    @Column(name = "NOTAS")
    private String notes;

    public Estudio(String name, Date startingDate, String currentState, String result, String notes) {
        this.name = name;
        this.startingDate = startingDate;
        this.currentState = currentState;
        this.result = result;
        this.notes = notes;
    }

    public static Estudio create(String name, Date startingDate, String status, String result, String observation) {

        return new Estudio(name,startingDate,status,result,observation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String isCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
