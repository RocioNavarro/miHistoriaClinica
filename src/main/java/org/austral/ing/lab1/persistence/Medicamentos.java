package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class Medicamentos {

    private EntityManager entityManager;

    public Medicamentos(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
