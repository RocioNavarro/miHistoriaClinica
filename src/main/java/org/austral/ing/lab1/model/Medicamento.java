package org.austral.ing.lab1.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Medicamento {

    @Id
    private String name;

    @Column(name = "FRECUENCIA")
    private String frecuency;

    @Column(name = "FECHA_DE_INICIO")
    private Date startingDate;

    @Column(name = "ESTADO")
    private String currentState;

    @Column(name = "NOTAS")
    private String notes;

    public Medicamento(String name, String frecuencyByWeek, Date startingDate, String currentState, String notes) {
        this.name = name;
        this.frecuency = frecuencyByWeek;
        this.startingDate = startingDate;
        this.currentState = currentState;
        this.notes = notes;
    }

    public Medicamento() {

    }

    public static Medicamento create(String name, String frecuency, Date startingDate, String status, String observation) {

        return new Medicamento(name,frecuency,startingDate,status,observation);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrecuency() {
        return frecuency;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
