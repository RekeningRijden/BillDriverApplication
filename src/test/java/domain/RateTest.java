package domain;

import main.domain.Rate;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

/**
 * Created by martijn on 24-6-2016.
 */
public class RateTest {

    @Test
    public void testGettersAndSetters() {
        Rate rate = new Rate();

        /* Id */
        rate.setId(1L);
        assertSame(1L, rate.getId());

        /* Name */
        rate.setName("A");
        assertSame("A", rate.getName());

        /* Value */
        rate.setValue(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE.setScale(2, RoundingMode.CEILING), rate.getValue());
    }

    @Test
    public void testConstructor() {
        Rate rate = new Rate("A");

        assertSame(BigDecimal.ZERO.setScale(2, RoundingMode.CEILING), rate.getValue());
        assertSame("A", rate.getName());
    }
}
