package domain;

import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by martijn on 22-6-2016.
 */
public class OwnershipTest {

    @Test
    public void testGettersAndSetters() {
        Ownership ownership = new Ownership();
        Date date = new Date();
        Car car = new Car();
        Driver driver = new Driver();

        /* Id */
        ownership.setId(1L);
        assertSame(1L, ownership.getId());

        /* Start Date */
        ownership.setStartDate(date);
        assertSame(date, ownership.getStartDate());

        /* End Date */
        ownership.setEndDate(date);
        assertSame(date, ownership.getEndDate());

        /* Car */
        ownership.setCar(car);
        assertSame(car, ownership.getCar());

        /* Driver */
        ownership.setDriver(driver);
        assertSame(driver, ownership.getDriver());
    }
}
