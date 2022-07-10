package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Medic;
import org.austral.ing.lab1.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;

public class Medics_Patients {
    private final EntityManager entityManager;

    public Medics_Patients(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Medic> listMedicsForPatient(int mhn){
        Patient patient = (Patient) entityManager.createQuery("SELECT u FROM Patient where u.medicalHistoryNumber Like: mhn").getResultList().get(0);
        return patient.getMedics();
    }
}
