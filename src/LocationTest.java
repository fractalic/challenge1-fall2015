import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

    @Test
    public void construct() {
        Location unconstrainedLocation = new Location(10,20);
        Location constrainedLocation = new Location(3,4,0,8);
    }
    
    @Test(expected = LocationOutOfBoundsException.class)
    public void constructOutOfBoundsLow() {
        Location badlyConstrainedLocation = new Location(10, 20, 11, 100);
    }

}
