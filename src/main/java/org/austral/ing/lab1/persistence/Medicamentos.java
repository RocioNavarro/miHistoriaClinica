package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.*;

import javax.persistence.EntityManager;

public class Medicamentos {

    private EntityManager entityManager;

    public Medicamentos(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Medicamento createMedicamento(MedicamentoForm registerValues){
        final Medicamento newMedicamento = Medicamento.create(registerValues.getName(),registerValues.getFrecuency(),registerValues.getStartingDate(),registerValues.getStatus(),registerValues.getObservation());

        entityManager.persist(newMedicamento);

        return newMedicamento;
    }


}
