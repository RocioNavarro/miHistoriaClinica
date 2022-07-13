package org.austral.ing.lab1.persistence;

import javax.persistence.EntityManager;

public class Estudios {

    private EntityManager entityManager;

    public Estudios(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
