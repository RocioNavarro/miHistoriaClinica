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
    private boolean currentState;

    @Column(name = "RESULTADO")
    private String result;

    @Column(name = "NOTAS")
    private String notes;

    public Estudio(String name, Date startingDate, boolean currentState, String result, String notes) {
        this.name = name;
        this.startingDate = startingDate;
        this.currentState = currentState;
        this.result = result;
        this.notes = notes;
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

    public boolean isCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
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
