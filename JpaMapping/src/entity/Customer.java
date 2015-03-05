/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enums.CustomerType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Kasper
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s1")
    @SequenceGenerator(name = "s1", sequenceName = "My_SEQ",
            initialValue = 100000, allocationSize = 1)

//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private CustomerType ct;

    public CustomerType getCt() {
        return ct;
    }

    public void setCt(CustomerType ct) {
        this.ct = ct;
    }

    public Customer(){
        
    }
    public Customer(String name){
        this.name = name;
    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
