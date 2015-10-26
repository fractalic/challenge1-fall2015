import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class BoardTest {

    @Test
    public void construct() {
        Board testBoard = new Board(7);
    }
    
    @Test(expected = InvalidStateException.class)
    public void constructInvalidDimension() {
        Board testBoard = new Board(-1);
    }
    
    @Test
    public void getAvailableDirectionsAtInner() {
        Board testBoard = new Board(7);
        List<Direction> directions = testBoard.getAvailableDirectionsAt(new Location(1,1,6));
        assertTrue(directions.size() == 4);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
    }
    
    @Test
    public void getAvailableDirectionsAtWestEdge() {
        Board testBoard = new Board(7);
        List directions = testBoard.getAvailableDirectionsAt(new Location(0,1,6));
        assertTrue(directions.size() == 3);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
    }
    
    @Test
    public void getAvailableDirectionsAtNorthEdge() {
        Board testBoard = new Board(7);
        List<Direction> directions = testBoard.getAvailableDirectionsAt(new Location(3,6,6));
        assertTrue(directions.size() == 3);
        assertTrue(directions.contains(Direction.WEST));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
    }
    
    @Test
    public void getAvailableDirectionsAtSouthEastCorner() {
        Board testBoard = new Board(7);
        List<Direction> directions = testBoard.getAvailableDirectionsAt(new Location(6,0,6));
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.WEST));
        assertTrue(directions.contains(Direction.NORTH));
    }
    
    @Test
    public void testSetState() {
        Board testBoard = new Board(7);
        List<Direction> directions = testBoard.getAvailableDirectionsAt(new Location(3,2,6));
        
        assertTrue(directions.size() == 4);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
        
        Location modifiedLocation = new Location(3,3,6);
        testBoard.setStateAt(modifiedLocation, Board.LocationState.UNAVAILABLE);
        
        directions = testBoard.getAvailableDirectionsAt(new Location(3,2,6));
        
        assertTrue(directions.size() == 3);
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
        
        directions = testBoard.getAvailableDirectionsAt(new Location(3,3,6));
        
        assertTrue(directions.size() == 4);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
        
    }
    
    @Test
    public void testSetStateMultipleCorner() {
        Board testBoard = new Board(7);
        Location initialLocation = new Location(6,6,6);
        Location testLocation;
        
        List<Direction> directions = testBoard.getAvailableDirectionsAt(initialLocation);
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.WEST));
        
        testLocation = initialLocation.cloneOffset(-1, 0);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        testLocation = initialLocation.cloneOffset(0, -1);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        directions = testBoard.getAvailableDirectionsAt(initialLocation);
        assertTrue(directions.size() == 0);
        
        directions = testBoard.getAvailableDirectionsAt(initialLocation.cloneOffset(-2, 0));
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.WEST));
        
        directions = testBoard.getAvailableDirectionsAt(initialLocation.cloneOffset(0, -2));
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.WEST));
        
        directions = testBoard.getAvailableDirectionsAt(initialLocation.cloneOffset(-1, -1));
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.WEST));
        
    }
    
    @Test
    public void testSerialize() {
        Board testBoard = new Board(7);
        Location initialLocation = new Location(0,0,6);
        
        Location testLocation = initialLocation.cloneOffset(2, 6);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(3, 0);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(3, 6);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(3, 1);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(3, 5);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(2, 1);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(3, 6);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        testLocation = initialLocation.cloneOffset(1, 1);
        testBoard.setStateAt(testLocation, Board.LocationState.UNAVAILABLE);
        
        System.out.println(testBoard.serialize(Game.Mode.ONE_PLAYER));
    }

}
