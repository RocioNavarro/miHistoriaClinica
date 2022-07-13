package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Estudio;
import org.austral.ing.lab1.model.EstudioForm;
import org.austral.ing.lab1.model.Patient;
import org.austral.ing.lab1.model.RegisterPatient;

import javax.persistence.EntityManager;

public class Estudios {

    private EntityManager entityManager;

    public Estudios(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Estudio createEstudio(EstudioForm registerValues){
        final Estudio newEstudio = Estudio.create(registerValues.getName(),registerValues.getStartingDate(),registerValues.getStatus(),registerValues.getResult(),registerValues.getObservation());

        entityManager.persist(newEstudio);

        return newEstudio;
    }
}
