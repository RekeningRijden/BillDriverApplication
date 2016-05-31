/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author maikel
 */
@Entity
@Table(name = "Driver")
public class Driver implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean subscribedToTrafficInfo;

    @Transient
    private String firstName;
    @Transient
    private String lastName;

    @Transient
    private Address address;

    public Driver() {
        // Empty constructor
    }

    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSubscribedToTrafficInfo() {
        return subscribedToTrafficInfo;
    }

    public void setSubscribedToTrafficInfo(Boolean subscribedToTrafficInfo) {
        this.subscribedToTrafficInfo = subscribedToTrafficInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

//</editor-fold>
}
