package domain;

import main.domain.Position;
import main.domain.TrackingPeriod;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by martijn on 24-6-2016.
 */
public class TrackingPeriodTest {

    @Test
    public void testGettersAndSetters() {
        TrackingPeriod trackingPeriod = new TrackingPeriod();
        Date date = new Date();
        List<Position> positions = new ArrayList<>();
        Position position = new Position();

        /* Id */
        trackingPeriod.setId(1L);
        assertSame(1L, trackingPeriod.getId());

        /* Serial Number */
        trackingPeriod.setSerialNumber(9L);
        assertSame(9L, trackingPeriod.getSerialNumber());

        /* Started Tracking */
        trackingPeriod.setStartedTracking(date);
        assertSame(date, trackingPeriod.getStartedTracking());

        /* Finished Tracking */
        trackingPeriod.setFinishedTracking(date);
        assertSame(date, trackingPeriod.getFinishedTracking());

        /* Positions */
        positions.add(position);

        trackingPeriod.setPositions(positions);
        assertNotNull(trackingPeriod.getPositions());
        assertSame(1, trackingPeriod.getPositions().size());
        assertSame(position, trackingPeriod.getPositions().get(0));
    }

    @Test
    public void testConstructor() {
        Date date = new Date();
        List<Position> positions = new ArrayList<>();
        Position position = new Position();
        positions.add(position);

        TrackingPeriod trackingPeriod = new TrackingPeriod(1L,9L,date,date,positions);

        assertSame(1L, trackingPeriod.getId());
        assertSame(9L, trackingPeriod.getSerialNumber());
        assertSame(date, trackingPeriod.getStartedTracking());
        assertSame(date, trackingPeriod.getFinishedTracking());
        assertSame(position, positions.get(0));
    }
}
