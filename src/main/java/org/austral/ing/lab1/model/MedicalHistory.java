package org.austral.ing.lab1.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MedicalHistory {
    @Id
    private Long medicalHistoryNumber;

    @Column(name = "ENFERMEDADES_PASADAS")
    private String enfermedades;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "medicalHistoryNumber")
    private Patient patient;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicamento> medicamentosEC  = new ArrayList<>();

    @Column(name = "ANTECEDENTES")
    private String antecedentes;

    @Column(name = "OBSERVACIONES_1")
    private String observations1;

    @Column(name = "MOTIVO_DE_CONSULTA")
    private String consult;

    @Column(name = "ENFERMEDAD_ACTUAL")
    private String disease;

    @Column(name = "EXAMEN_FISICO")
    private String physicalExam;

    @Column(name = "DATOS_POSITIVOS_Y_NEGATIVOS")
    private String upsAndDowns;

    @Column(name = "OBSERVACIONES_2")
    private String observations2;

    public MedicalHistory(Patient patient, String enfermedades, String antecedentes,
                          String observations1, String consult, String disease, String physicalExam, String upsAndDowns, String observations2) {
        this.patient = patient;
        this.enfermedades = enfermedades;
        this.antecedentes = antecedentes;
        this.observations1 = observations1;
        this.consult = consult;
        this.disease = disease;
        this.physicalExam = physicalExam;
        this.upsAndDowns = upsAndDowns;
        this.observations2 = observations2;

    }

    public static MedicalHistory create(Patient patient, String enfermedades, String antecedentes, String observations1, String consult, String disease, String physicalExam, String upsAndDowns, String observations2) {
        return new MedicalHistory(patient,enfermedades,antecedentes,observations1,consult,disease,physicalExam,upsAndDowns,observations2);
    }

    public Long getMedicalHistoryNumber() {
        return medicalHistoryNumber;
    }

    public void setMedicalHistoryNumber(Long id) {
        this.medicalHistoryNumber = id;
    }

    public String getEnfermedades() {
        return enfermedades;
    }

    public void setEnfermedades(String enfermedades) {
        this.enfermedades = enfermedades;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }


    public String getObservations1() {
        return observations1;
    }

    public void setObservations1(String observations1) {
        this.observations1 = observations1;
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }

    public String getUpsAndDowns() {
        return upsAndDowns;
    }

    public void setUpsAndDowns(String upAndDowns) {
        this.upsAndDowns = upAndDowns;
    }

    public String getObservations2() {
        return observations2;
    }

    public void setObservations2(String observations2) {
        this.observations2 = observations2;
    }
}
