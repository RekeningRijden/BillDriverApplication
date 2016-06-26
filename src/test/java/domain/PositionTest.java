package domain;

import main.domain.Position;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by martijn on 23-6-2016.
 */
public class PositionTest {

    @Test
    public void testGettersAndSetters() {
        Position position = new Position();

        /* Id */
        position.setId(1L);
        assertSame(1L, position.getId());

        /* Longtitude */
        position.setLongitude(1.0);
        assertEquals(1.0, position.getLongitude(),0.01);

        /* Latitude */
        position.setLatitude(1.0);
        assertEquals(1.0, position.getLatitude(), 0.01);
    }

    @Test
    public void testConstructor() {
        Position position = new Position(1.0, 2.0);

        assertEquals(1.0, position.getLongitude(), 0.01);
        assertEquals(2.0, position.getLatitude(), 0.01);
    }
}
