package main.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by martijn on 13-5-2016.
 */
public class Rate implements Serializable, IEntity {

    private Long id;

    private String name;

    private BigDecimal value;

    public Rate() {
        this.value = BigDecimal.ZERO;
    }

    public Rate(String name) {
        this();
        this.name = name;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.CEILING);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
    //</editor-fold>
}
