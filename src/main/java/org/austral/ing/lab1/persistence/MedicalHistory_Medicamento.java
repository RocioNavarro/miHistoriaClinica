package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class MedicalHistory_Medicamento {
    private EntityManager entityManager;

    public MedicalHistory_Medicamento(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
