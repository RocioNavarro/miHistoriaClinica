package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class Patients_Estudios {

    private EntityManager entityManager;

    public Patients_Estudios(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
