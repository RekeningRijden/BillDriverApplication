package main.domain;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by martijn on 13-5-2016.
 */
public class Car implements Serializable, IEntity {

    private Long id;

    private Long cartrackerId;
    private String licencePlate;

    private List<Ownership> pastOwnerships;

    private Ownership currentOwnership;

    private Rate rate;

    public Car() {
        this.pastOwnerships = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public Ownership getCurrentOwnership() {
        return currentOwnership;
    }

    public void setCurrentOwnership(Ownership currentOwnership) {
        this.currentOwnership = currentOwnership;
    }

    public Long getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(Long cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    @XmlTransient
    public List<Ownership> getPastOwnerships() {
        return pastOwnerships;
    }

    public void setPastOwnerships(List<Ownership> pastOwnerships) {
        this.pastOwnerships = pastOwnerships;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HashCode/Equals">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Car other = (Car) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
