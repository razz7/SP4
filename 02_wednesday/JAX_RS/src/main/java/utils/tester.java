package utils;

import entities.Address;
import entities.Person;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rh
 */
public class tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Henning", "Henningsen", "12345678", new Date(), new Date()) ;
        Person p2 = new Person("Rasmus", "Rasmussen", "87654321", new Date(), new Date());
        
        Address a1 = new Address("Cph", 2323, "Nørre voldgade 1");
        Address a2 = new Address("Århus", 2454, "Nørre voldgade 2");
        
        p1.setAddress(a1);
        p2.setAddress(a2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();
    }
}
