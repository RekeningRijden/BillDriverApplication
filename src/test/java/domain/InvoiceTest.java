package domain;

import main.domain.Invoice;
import main.domain.Ownership;
import main.domain.enums.PaymentStatus;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by martijn on 22-6-2016.
 */
public class InvoiceTest {

    @Test
    public void testGettersAndSetters() {
        Invoice invoice = new Invoice();
        Date date = new Date();
        Ownership ownership = new Ownership();

        /* Id */
        invoice.setId(1L);
        assertSame(1L, invoice.getId());

        /* Payment Status */
        invoice.setPaymentStatus(PaymentStatus.OPEN);
        assertSame(PaymentStatus.OPEN, invoice.getPaymentStatus());

        /* Period */
        invoice.setPeriod(date);
        assertSame(date, invoice.getPeriod());

        /* Total Amount */
        invoice.setTotalAmount(BigDecimal.valueOf(1));
        assertSame(BigDecimal.valueOf(1), invoice.getTotalAmount());

        /* Ownership */
        invoice.setOwnership(ownership);
        assertSame(ownership, invoice.getOwnership());
    }

    @Test
    public void testConstructor() {
        Invoice invoice = new Invoice();

        assertNotNull(invoice.getTotalAmount());
        assertSame(BigDecimal.ZERO, invoice.getTotalAmount());
    }
}
