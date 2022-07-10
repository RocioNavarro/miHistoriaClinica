package org.austral.ing.lab1.model;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicalHistoryManager {

    private String enfermedadesPasadas;
    private Patient patient;
    private List<Medicamento> medicamentos;
    private String antecedentes;
    private String observation1;
    private String consult;
    private String disease;
    private String physicalExam;
    private String upsAndDowns;
    private String observation2;

    public MedicalHistoryManager(String enfermedades, Patient patient, String antecedentes, String observation1, String consult, String disease, String physicalExam, String upsAndDowns, String observation2) {
        this.enfermedadesPasadas = enfermedades;
        this.patient = patient;
        this.medicamentos = new ArrayList<>();
        this.antecedentes = antecedentes;
        this.observation1 = observation1;
        this.consult = consult;
        this.disease = disease;
        this.physicalExam = physicalExam;
        this.upsAndDowns = upsAndDowns;
        this.observation2 = observation2;
    }

    public static MedicalHistoryManager createFromBody(String body, Patient patient){
        final MultiMap<String> params = new MultiMap<>();
        UrlEncoded.decodeTo(body, params, "UTF-8");

        return MedicalHistoryManager.create(
                params.getValues("enfermedad_pasada"),
                params.getValues("antecedentes"),
                params.getValues("observacion1"),
                params.getValues("consulta"),
                params.getValues("enfermedad_actual"),
                params.getValues("physicalExam"),
                params.getValues("positivos_y_negativos"),
                params.getValues("observacion2"),
                patient);

    }

    public static MedicalHistoryManager createFromZero(Optional<Patient> patient){
        return MedicalHistoryManager.create("","","","","","","","",patient.get());
    }

    private static MedicalHistoryManager create(List<String> enfermedad_pasada, List<String> antecedentes, List<String> observacion1, List<String> consulta, List<String> enfermedad_actual, List<String> physicalExam, List<String> positivos_y_negativos, List<String> observacion2,Patient patient) {

        return new MedicalHistoryManager(enfermedad_pasada.get(0),patient,antecedentes.get(0),observacion1.get(0),consulta.get(0),enfermedad_actual.get(0),physicalExam.get(0),positivos_y_negativos.get(0),observacion2.get(0));
    }

    private static MedicalHistoryManager create(String enfermedad_pasada, String antecedentes, String observacion1, String consulta, String enfermedad_actual, String physicalExam, String positivos_y_negativos, String observacion2,Patient patient) {

        return new MedicalHistoryManager(enfermedad_pasada,patient,antecedentes,observacion1,consulta,enfermedad_actual,physicalExam,positivos_y_negativos,observacion2);
    }

    public String getEnfermedadesPasadas() {
        return enfermedadesPasadas;
    }

    public void setEnfermedadesPasadas(String enfermedadesPasadas) {
        this.enfermedadesPasadas = enfermedadesPasadas;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getObservation1() {
        return observation1;
    }

    public void setObservation1(String observation1) {
        this.observation1 = observation1;
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

    public void setUpsAndDowns(String upsAndDowns) {
        this.upsAndDowns = upsAndDowns;
    }

    public String getObservation2() {
        return observation2;
    }

    public void setObservation2(String observation2) {
        this.observation2 = observation2;
    }
}
