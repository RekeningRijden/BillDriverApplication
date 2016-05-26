package main.domain;

import main.domain.enums.PaymentStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Eric on 02-04-16.
 */
public class Invoice implements Serializable, IEntity {

    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Date period;
    private BigDecimal totalAmount;

    public Invoice() {
        this.totalAmount = BigDecimal.ZERO;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    //</editor-fold>
}
