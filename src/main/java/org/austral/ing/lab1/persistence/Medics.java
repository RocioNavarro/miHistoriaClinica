package org.austral.ing.lab1.persistence;

import org.austral.ing.lab1.model.Medic;
import org.austral.ing.lab1.model.Patient;
import org.austral.ing.lab1.model.RegisterMedic;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class Medics {

    private final EntityManager entityManager;

    public Medics(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Medic createMedic(RegisterMedic registerValues){
        final Medic newMedic = Medic.create(registerValues.getMatricula(),registerValues.getName(),registerValues.getLastname(),registerValues.getPassword(),registerValues.getSpeciality(),registerValues.getContact());

        if (exists(newMedic.getMatricula())) throw new IllegalStateException("Medic already exists");

        entityManager.persist(newMedic);

        return newMedic;
    }

    public boolean exists(Long matricula){
        return findByMatricula(matricula).isPresent();
    }

    public List<Medic> list(){
        return entityManager.createQuery("select u from Medic u", Medic.class).getResultList();
    }

    public Optional<Medic> findByMatricula(Long matricula) {
        return entityManager.createQuery("SELECT u FROM Medic u WHERE u.matricula LIKE :matricula", Medic.class)
                .setParameter("matricula",matricula).getResultList().stream()
                .findFirst();
    }


}
