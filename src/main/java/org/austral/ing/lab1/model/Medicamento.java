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

    @Column(name = "FECHA_DE_FINALIZACION")
    private Date endingDate;

    @Column(name = "ESTADO")
    private boolean currentState;

    @Column(name = "NOTAS")
    private String notes;

    public Medicamento(String name, String frecuencyByWeek, Date startingDate, Date endingDate, boolean currentState, String notes) {
        this.name = name;
        this.frecuency = frecuencyByWeek;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.currentState = currentState;
        this.notes = notes;
    }

    public Medicamento() {

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

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public boolean isCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
