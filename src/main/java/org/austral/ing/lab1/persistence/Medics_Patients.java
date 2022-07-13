package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Medic;
import org.austral.ing.lab1.model.Patient;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class Medics_Patients {
    private final EntityManager entityManager;

    public Medics_Patients(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Medic addPatient(long mhn, long matricula){
        Medic medic = entityManager.createQuery("SELECT u FROM Medic u WHERE u.matricula LIKE :matricula", Medic.class)
                .setParameter("matricula",matricula).getResultList().stream()
                .findFirst().get();
        Patient patient = entityManager.createQuery("SELECT u FROM Patient u WHERE u.medicalHistoryNumber LIKE :mhn", Patient.class)
                .setParameter("mhn",mhn).getResultList().stream()
                .findFirst().get();

        medic.addPatient(patient);
        entityManager.flush();
        return medic;
    }

    public List<Medic> listMedicsForPatient(long mhn){
        return entityManager.createQuery("SELECT u FROM Patient where u.medicalHistoryNumber Like: mhn",Patient.class).getResultList().stream().findFirst().get().getMedics();
    }

    public List<Patient> listPatientForMedics() {

        Query query = entityManager.createQuery("SELECT  p.medicalHistoryNumber,p.name, p.lastName,p.contact FROM Patient p");
        return query.getResultList();
    }

}
