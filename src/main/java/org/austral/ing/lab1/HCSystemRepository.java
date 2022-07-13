package org.austral.ing.lab1;


import org.austral.ing.lab1.persistence.*;

import javax.persistence.EntityManager;

public class HCSystemRepository {
    private final Patients patients;
    private final Medics medics;
    private final Medics_Patients medics_patients;
    private final Medicamentos medicamentos;
    private final MedicalHistories medicalHistories;

    public HCSystemRepository(EntityManager entityManager) {
        this.patients = new Patients(entityManager);
        this.medics = new Medics(entityManager);
        this.medics_patients = new Medics_Patients(entityManager);
        this.medicamentos = new Medicamentos(entityManager);
        this.medicalHistories = new MedicalHistories(entityManager);
    }

    public static HCSystemRepository create(EntityManager entityManager) {
        return new HCSystemRepository(entityManager);
    }

    public Patients patients() {
        return patients;
    }

    public Medics medics(){
        return medics;
    }

    public Medics_Patients medics_patients(){return medics_patients;}

    public Medicamentos medicamentos(){return medicamentos;}

    public MedicalHistories medicalHistories() {
        return medicalHistories;
    }
}
