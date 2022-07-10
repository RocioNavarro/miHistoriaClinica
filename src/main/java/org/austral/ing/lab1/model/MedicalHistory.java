package org.austral.ing.lab1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class MedicalHistory {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "ENFERMEDADES")
    private String enfermedades;

    @OneToOne(mappedBy = "Patient", cascade = CascadeType.ALL,
                orphanRemoval = true, fetch = FetchType.LAZY)
                    private Patient patient;

    @Column(name = "ANTECEDENTES")
    private String antecedentes;

    @Column(name = "OBSERVACIONES")
    private String observaciones;
    }
