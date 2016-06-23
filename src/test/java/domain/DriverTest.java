package domain;

import main.domain.Address;
import main.domain.Driver;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martijn on 22-6-2016.
 */
public class DriverTest {

    @Test
    public void testGettersAndSetters() {
        Driver driver = new Driver();
        Address address = new Address();

        /* Id */
        driver.setId(1L);
        assertSame(1L, driver.getId());

        /* Subscribed to traffic info */
        driver.setSubscribedToTrafficInfo(true);
        assertSame(true, driver.getSubscribedToTrafficInfo());

        /* Firstname */
        driver.setFirstName("Firstname");
        assertSame("Firstname", driver.getFirstName());

        /* Lastname */
        driver.setLastName("Lastname");
        assertSame("Lastname", driver.getLastName());

        /* Address */
        driver.setAddress(address);
        assertSame(address, driver.getAddress());
    }
}
