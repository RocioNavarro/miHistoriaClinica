package org.austral.ing.lab1.model;


import org.austral.ing.lab1.persistence.Medics;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Patient {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long medicalHistoryNumber;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "APELLIDO")
    private String lastName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DNI")
    private int dni;

    @Column(name = "OBRA_SOCIAL")
    private String lifeInsurance;

    @Column(name = "FECHA_DE_NACIMIENTO")
    private Date birth;

    @Column(name = "CONTACTO")
    private int contact;

    @OneToOne
    MedicalHistory medicalHistory;

    @ManyToMany(mappedBy = "patients")
    private List<Medic> medics = new ArrayList<>();
    public Patient() {

    }

    public Patient(String name, String lastName, String password, int dni, String lifeInsurance, Date birth, int contact) {
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.dni = dni;
        this.lifeInsurance = lifeInsurance;
        this.birth = birth;
        this.contact = contact;
    }

    public static Patient create(String name, String lastname, String password, int dni, String lifeInsurance, Date dateOfBirth, int contact) {
        return new Patient(name,lastname,password,dni,lifeInsurance,dateOfBirth,contact);
    }

    public Long getMedicalHistoryNumber() {
        return medicalHistoryNumber;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getDni() {
        return dni;
    }

    public String getLifeInsurance() {
        return lifeInsurance;
    }

    public Date getBirth() {
        return birth;
    }

    public int getContact() {
        return contact;
    }

    public List<Medic> getMedics() {
        return medics;
    }
}

