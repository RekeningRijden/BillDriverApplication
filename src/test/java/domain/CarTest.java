package domain;

import main.domain.Car;
import main.domain.Ownership;
import main.domain.Rate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by martijn on 22-6-2016.
 */
public class CarTest {

    @Test
    public void testGettersAndSetters() {
        Car car = new Car();
        List<Ownership> ownershipList = new ArrayList<>();
        Ownership ownership = new Ownership();
        Rate rate = new Rate();

        /* Id */
        car.setId(1L);
        assertSame(1L, car.getId());

        /* Cartracker Id */
        car.setCartrackerId(1L);
        assertSame(1L, car.getCartrackerId());

        /* License Plate */
        car.setLicencePlate("33-BB-44");
        assertSame("33-BB-44", car.getLicencePlate());

        /* Past Ownerships */
        ownershipList.add(ownership);
        car.setPastOwnerships(ownershipList);
        assertNotNull(car.getPastOwnerships());
        assertSame(1, car.getPastOwnerships().size());

        /* Current Ownership */
        car.setCurrentOwnership(ownership);
        assertSame(ownership, car.getCurrentOwnership());

        /* Rate */
        car.setRate(rate);
        assertSame(rate, car.getRate());
    }

    @Test
    public void testConstructor() {
        Car car = new Car();

        assertNotNull(car.getPastOwnerships());
    }
}
