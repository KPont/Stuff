/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Customer;
import enums.CustomerType;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;


/**
 *
 * @author Kasper
 */
public class Tester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaMappingPU");
        EntityManager em = emf.createEntityManager();
        
        Customer cust1 = new Customer("Kurt");
        em.getTransaction().begin();
        em.persist(cust1);
        em.getTransaction().commit();
    }
}
