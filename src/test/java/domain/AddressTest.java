package domain;

import main.domain.Address;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by martijn on 22-6-2016.
 */
public class AddressTest {

    @Test
    public void testGettersAndSetters() {
        Address address = new Address();

        /* Id */
        address.setId(1L);
        assertSame(1L, address.getId());

        /* Street */
        address.setStreet("testStreet");
        assertSame("testStreet", address.getStreet());

        /* StreetNr */
        address.setStreetNr("2");
        assertSame("2", address.getStreetNr());

        /* ZipCode */
        address.setZipCode("1234AB");
        assertSame("1234AB", address.getZipCode());

        /* City */
        address.setCity("Eindhoven");
        assertSame("Eindhoven", address.getCity());

        /* Country */
        address.setCountry("Nederland");
        assertSame("Nederland", address.getCountry());
    }
}
