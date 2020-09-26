package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        EntityManager em = emf.createEntityManager();
        Person p1 = new Person(fName, lName, phone, new Date(), new Date());
        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.getTransaction().commit();
            return new PersonDTO(p1);
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, id);
        em.remove(p);
        em.getTransaction().commit();
        em.close();
        return new PersonDTO(p);
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {

        EntityManager em = emf.createEntityManager();
        try {

            Person p = em.find(Person.class, id);
            if (p == null) {
                throw new PersonNotFoundException("No person with provided id found");
            }
            PersonDTO PDTO = new PersonDTO(p);
            return PDTO;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> q1 = em.createQuery("SELECT p FROM Person p", Person.class);

            return new PersonsDTO(q1.getResultList());
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        System.out.println(p.getFirstname());
        Person p1 = new Person(p.getFirstname(), p.getLastname(), p.getPhone(), p.getCreated(), new Date());
        System.out.println(p1.getFirstname());
        try {
            p1 = em.find(Person.class, p.getId());
            em.getTransaction().begin();
            p1.setFirstname(p.getFirstname());
            p1.setLastname(p.getLastname());
            p1.setPhone(p.getPhone());
            em.merge(p1);
            em.getTransaction().commit();
            PersonDTO newP = new PersonDTO(p1);
            return newP;
        } finally {
            em.close();
        }
    }

}
