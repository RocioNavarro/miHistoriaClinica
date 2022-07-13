package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class Patient_Medicamentos {

    private EntityManager entityManager;

    public Patient_Medicamentos(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
