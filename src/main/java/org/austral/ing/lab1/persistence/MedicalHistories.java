package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.MedicalHistory;
import org.austral.ing.lab1.model.MedicalHistoryManager;
import org.austral.ing.lab1.model.Patient;
import org.austral.ing.lab1.model.RegisterPatient;

import javax.persistence.EntityManager;

public class MedicalHistories {

    private EntityManager entityManager;

    public MedicalHistories(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public MedicalHistory createMedicalHistory(MedicalHistoryManager medicalHistoryManager){
        final MedicalHistory newMedicalHistory = MedicalHistory.create(medicalHistoryManager.getPatient(),medicalHistoryManager.getEnfermedadesPasadas(),medicalHistoryManager.getAntecedentes(),medicalHistoryManager.getObservation1(),medicalHistoryManager.getConsult(),medicalHistoryManager.getDisease(), medicalHistoryManager.getPhysicalExam(), medicalHistoryManager.getUpsAndDowns(), medicalHistoryManager.getObservation2());

        entityManager.persist(newMedicalHistory);

        return newMedicalHistory;
    }
}
