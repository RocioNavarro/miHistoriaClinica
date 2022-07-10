package org.austral.ing.lab1;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.austral.ing.lab1.model.*;
import org.austral.ing.lab1.persistence.MedicalHistories;
import org.austral.ing.lab1.persistence.Medics;
import org.austral.ing.lab1.persistence.Patients;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class HCSystem {
    private final EntityManagerFactory factory;

    private HCSystem(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public static HCSystem create(String persistanceUnitName) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory(persistanceUnitName);
        return new HCSystem(factory);
    }

    public Optional<Patient> registerPatient(RegisterPatient form) {
        return runInTransaction(datasource -> {
            final Patients patients = datasource.patients();
            final MedicalHistories medicalHistories = datasource.medicalHistories();
            if (patients.exists(form.getDni())){
                return Optional.empty();
        }else {
               Optional<Patient> newPatient = Optional.of(patients.createPatient(form));
               MedicalHistoryManager.createFromZero(newPatient);
               medicalHistories.createMedicalHistory(MedicalHistoryManager.createFromZero(newPatient));
                return newPatient;
            }
        });
    }

    public Optional<Medic> registerMedic(RegisterMedic form) {
        return runInTransaction(datasource -> {
            final Medics medics = datasource.medics();
            if (medics.exists(form.getMatricula())){
                return Optional.empty();
            }
            return Optional.of(medics.createMedic(form));
        });
    }

    //Verfica el login del Paciente
    public Optional<Patient> checkLoginPatient(LoginPatientRequest form) {
        return runInTransaction(ds -> {
            final Patients patients = ds.patients();
            return patients.findByDni(form.getDni())
                    .filter(foundUser -> validPassword(form.getPassword(), foundUser));
        });
    }

    //Verifica el login del Medico
    public Optional<Medic> checkLoginMedic(LoginMedicRequest form) {
        return runInTransaction(ds -> {
            final Medics medics = ds.medics();
            return medics.findByMatricula(form.getMatricula())
                    .filter(foundUser -> validPassword(form.getPassword(), foundUser));
        });
    }

    //Encuentra Paciente por dni
    public Optional<Patient> findByDni(int dni) {
        return runInTransaction(
                ds -> ds.patients().findByDni(dni)
        );
    }
    //Encuentra Medico por matricula
    public Optional<Medic> findByMatricula(long matricula) {
        return runInTransaction(
                ds -> ds.medics().findByMatricula(matricula)
        );
    }

    //Crea Token 6 digitos
    public Token createToken(){
        String token ="";
        for(int i = 1; i<=6; i++){
            int number = (int)(Math.random()*9 + 1);
             token += String.valueOf(number);
        }
        return Token.create(Integer.parseInt(token));
    }
    public String createNumber(){
        String token= "";
        for(int i = 1; i<=6; i++){
            int number = (int)(Math.random()*9 + 1);
            token += String.valueOf(number);
        }
        return token;
    }

    //Crea Qr con un token adentro
    public BitMatrix qrCodeGenerator(Token token) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        return qrCodeWriter.encode(String.valueOf(token.getToken()), BarcodeFormat.QR_CODE,350,350);
    }

    public List<Patient> listPatients() {
        return runInTransaction(
                ds -> ds.patients().list()
        );
    }
    public List<Medic> listMedics() {
        return runInTransaction(
                ds -> ds.medics().list()
        );
    }

    //Transforma Qr en imagen
    public BufferedImage qrCodeDecoder(BitMatrix bitMatrix) {
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    //Aniadir medic a la lista del paciente y viceversa
    public void addMedicToPatientList(Patient patient, Medic medic) {
        medic.addPatient(patient);
    }

    public Optional<Patient> searchByMHN(int mhn){
        return runInTransaction(
                ds -> ds.patients().findByMHN(mhn)
        );
    }

    private <E> E runInTransaction(Function<HCSystemRepository, E> closure){
        final EntityManager entityManager = factory.createEntityManager();
        final HCSystemRepository ds = HCSystemRepository.create(entityManager);

        try{
            entityManager.getTransaction().begin();
            final E result = closure.apply(ds);
            entityManager.getTransaction().commit();
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
    public boolean validPassword(String password, Patient foundUser) {
        // Super dummy implementation. Zero security
        return foundUser.getPassword().equals(password);
    }
    public boolean validPassword(String password, Medic foundUser) {
        // Super dummy implementation. Zero security
        return foundUser.getPassword().equals(password);
    }



}
