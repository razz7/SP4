/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpa2rest2.entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author rh
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Henning", 1995);
        Person p2 = new Person("Rasmus", 1994);

        Address a1 = new Address("Nørrebrogade 1", 2200, "Kbh");
        Address a2 = new Address("Nørrebrogade 2", 2200, "Kbh");

        p1.setAddress(a1);

        p2.setAddress(a2);

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(300);
        Fee f3 = new Fee(700);

        p1.addFee(f1);
        p2.addFee(f3);
        p2.addFee(f2);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast stroke");

        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
            p1.removeSwimStyle(s3);
        em.getTransaction().commit();

        System.out.println("p1: " + p1.getP_id());
        System.out.println("p1 street : " + p1.getAddress().getStreet());

        System.out.println("lad os se om det viker begge veje: "
                + a1.getPerson().getName());

        System.out.println("Hvem har betalt f2 ? Det har: " + f2.getPerson()
                .getName());

        System.out.println("Hvad er der betalt i alt ?");

        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();

        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + ": " + f.getAmount()
                    + " kr. Den: " + f.getPayData() + " Adr: " + f.getPerson()
                    .getAddress().getCity());
        }

    }
}
