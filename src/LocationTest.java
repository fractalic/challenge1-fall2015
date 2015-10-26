import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

    @Test
    public void construct() {
        Location constrainedLocation = new Location(3,4,8);
    }
    
    @Test(expected = LocationOutOfBoundsException.class)
    public void constructOutOfBoundsLow() {
        Location badlyConstrainedLocation = new Location(10, 20, 11);
    }
    
    @Test
    public void getLocation() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location constrainedLocation = new Location(x, y, max);
        assertEquals(x,constrainedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(y, constrainedLocation.getCoordinate(Location.Coordinate.SECOND));
    }
    
    @Test
    public void getConstraints() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location constrainedLocation = new Location(x, y, max);
        assertEquals(max,constrainedLocation.getUpperBound());
    }
    
    @Test
    public void testClone() {
        int x = 10;
        int y = 7;
        int max = 15;
        Location testLocation = new Location(x, y, max);
        Location clonedLocation = testLocation.clone();
        assertEquals(testLocation.getUpperBound(), clonedLocation.getUpperBound());
        assertEquals(testLocation.getCoordinate(Location.Coordinate.FIRST),
                     clonedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(testLocation.getCoordinate(Location.Coordinate.SECOND),
                clonedLocation.getCoordinate(Location.Coordinate.SECOND));
    }
    
    @Test
    public void testCloneOffset() {
        int x = 1;
        int xOffset = 3;
        int y = 2;
        int yOffset = 7;
        int max = 15;
        Location testLocation = new Location(x, y, max);
        Location clonedLocation = testLocation.cloneOffset(xOffset, yOffset);
        assertEquals(testLocation.getUpperBound(), clonedLocation.getUpperBound());
        assertEquals(testLocation.getCoordinate(Location.Coordinate.FIRST) + xOffset,
                     clonedLocation.getCoordinate(Location.Coordinate.FIRST));
        assertEquals(testLocation.getCoordinate(Location.Coordinate.SECOND) + yOffset,
                clonedLocation.getCoordinate(Location.Coordinate.SECOND));
    }
    
    @Test(expected = LocationOutOfBoundsException.class)
    public void testOutOfBoundsCloneOffset() {
        int x = 1;
        int xOffset = 3;
        int y = 2;
        int yOffset = 7;
        int max = 8;
        Location testLocation = new Location(x, y, max);
        Location clonedLocation = testLocation.cloneOffset(xOffset, yOffset);
    }
    
    @Test
    public void testEquals() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location location1 = new Location(x, y, max);
        Location location2 = new Location(x, y, max);
        assertEquals(location1, location2);
    }
    
    @Test
    public void testToString() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location location1 = new Location(x, y, max);
        assertEquals("[10,20]", location1.toString());
    }
    
    @Test
    public void testToDirection() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location location1 = new Location(x, y, max);
        Location location2 = new Location(x+1, y, max);
        assertEquals(Direction.EAST, location1.getDirectionTo(location2));
    }
    
    @Test
    public void testToDirectionNull() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location location1 = new Location(x, y, max);
        Location location2 = new Location(x+1, y+1, max);
        assertTrue(location1.getDirectionTo(location2) == Direction.NOT_A_DIRECTION);
    }
    
    @Test(expected = LocationOutOfBoundsException.class)
    public void testToDirectionOutOfBounds() {
        int x = 10;
        int y = 20;
        int max = 100;
        Location location1 = new Location(x, y, max);
        Location location2 = new Location(x, y, max+1);
        location1.getDirectionTo(location2);
    }

}
