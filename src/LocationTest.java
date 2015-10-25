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
    
    @Test
    public void getLocation() {
        int x = 10;
        int y = 20;
        int min = 1;
        int max = 100;
        Location constrainedLocation = new Location(x, y, min, max);
        assertEquals(x,constrainedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(y, constrainedLocation.getCoordinate(Location.Coordinate.SECOND));
    }
    
    @Test
    public void getConstraints() {
        int x = 10;
        int y = 20;
        int min = 1;
        int max = 100;
        Location constrainedLocation = new Location(x, y, min, max);
        assertEquals(true, constrainedLocation.isConstrained());
        assertEquals(max,constrainedLocation.getConstraintMax());
        assertEquals(min, constrainedLocation.getConstraintMin());
    }
    
    @Test
    public void setLocation() {
        int x = 10;
        int setX = 15;
        int setY = 25;
        int y = 20;
        int min = 1;
        int max = 100;
        Location constrainedLocation = new Location(x, y, min, max);
        assertEquals(true, constrainedLocation.isConstrained());
        
        assertEquals(x,constrainedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(y, constrainedLocation.getCoordinate(Location.Coordinate.SECOND));
        
        constrainedLocation.set(setX, setY);
        assertEquals(setX,constrainedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(setY, constrainedLocation.getCoordinate(Location.Coordinate.SECOND));
    }
    
    @Test(expected = LocationOutOfBoundsException.class)
    public void setOutOfBoundsLocation() {
        int x = 10;
        int setX = 15;
        int y = 20;
        int setY = 25;
        int min = 1;
        int max = 22;
        Location constrainedLocation = new Location(x, y, min, max);
        assertEquals(true, constrainedLocation.isConstrained());
        
        assertEquals(x,constrainedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(y, constrainedLocation.getCoordinate(Location.Coordinate.SECOND));
        
        constrainedLocation.set(setX, setY);
    }

}
