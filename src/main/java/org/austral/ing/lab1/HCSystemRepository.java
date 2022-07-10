package org.austral.ing.lab1;


import org.austral.ing.lab1.persistence.Medics;
import org.austral.ing.lab1.persistence.Medics_Patients;
import org.austral.ing.lab1.persistence.Patients;

import javax.persistence.EntityManager;

public class HCSystemRepository {
    private final Patients patients;
    private final Medics medics;
    private final Medics_Patients medics_patients;

    public HCSystemRepository(EntityManager entityManager) {
        this.patients = new Patients(entityManager);
        this.medics = new Medics(entityManager);
        medics_patients = new Medics_Patients(entityManager);
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
}
