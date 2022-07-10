package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Patient;
import org.austral.ing.lab1.model.RegisterPatient;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class Patients {

    private final EntityManager entityManager;

    public Patients(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Patient createPatient(RegisterPatient registerValues){
        final Patient newPatient = Patient.create(registerValues.getName(),registerValues.getLastname(),registerValues.getPassword(),registerValues.getDni(),registerValues.getLifeInsurance(),registerValues.getDateOfBirth(),registerValues.getContact());

        if (exists(newPatient.getDni())) throw new IllegalStateException("Patient already exists");

        entityManager.persist(newPatient);

        return newPatient;
    }

    public boolean exists(int dni){
        return findByDni(dni).isPresent();
    }

    public Optional<Patient> findByDni(int dni) {
        return entityManager.createQuery("SELECT u FROM Patient u WHERE u.dni LIKE :dni", Patient.class)
                .setParameter("dni",dni).getResultList().stream()
                .findFirst();
    }

    public List<Patient> list(){
        return entityManager.createQuery("select u from Patient u", Patient.class).getResultList();
    }

    public Optional<Patient> findByMHN(int mhn) {
        return entityManager.createQuery("SELECT u FROM Patient u WHERE u.medicalHistoryNumber LIKE :mhn", Patient.class)
                .setParameter("mhn",mhn).getResultList().stream()
                .findFirst();
    }
}
