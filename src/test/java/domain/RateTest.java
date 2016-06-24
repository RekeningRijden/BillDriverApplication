package domain;

import main.domain.Rate;
import org.junit.Test;

import java.math.BigDecimal;

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
        rate.setValue(BigDecimal.valueOf(1));
        assertSame(BigDecimal.valueOf(1), rate.getValue());
    }

    @Test
    public void testConstructor() {
        Rate rate = new Rate("A");

        assertSame(BigDecimal.ZERO, rate.getValue());
        assertSame("A", rate.getName());
    }
}
