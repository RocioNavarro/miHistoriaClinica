package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Medic;
import org.austral.ing.lab1.model.Patient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class Medics_Patients {
    private final EntityManager entityManager;

    public Medics_Patients(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Medic addLink(Optional<Patient> patient, Optional<Medic> medic){
        medic.get().addPatient(patient.get());
        entityManager.persist(medic.get());
        return medic.get();
    }

    public List<Medic> listMedicsForPatient(long mhn){
        return entityManager.createQuery("SELECT u FROM Patient where u.medicalHistoryNumber Like: mhn").getResultList();
    }

    public List<Patient> listPatientForMedics(long matricula) {
        return entityManager.createQuery("select u FROM  Medic where u.matricula like: matricula").getResultList();
    }
}
