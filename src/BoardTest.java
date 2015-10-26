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
        List directions = testBoard.getAvailableDirectionsAt(new Location(1,1,7));
        assertTrue(directions.size() == 4);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
        assertTrue(directions.contains(Direction.WEST));
    }
    
    @Test
    public void getAvailableDirectionsAtWestEdge() {
        Board testBoard = new Board(7);
        List directions = testBoard.getAvailableDirectionsAt(new Location(0,1,7));
        assertTrue(directions.size() == 3);
        assertTrue(directions.contains(Direction.NORTH));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
    }
    
    @Test
    public void getAvailableDirectionsAtNorthEdge() {
        Board testBoard = new Board(7);
        List directions = testBoard.getAvailableDirectionsAt(new Location(3,6,7));
        assertTrue(directions.size() == 3);
        assertTrue(directions.contains(Direction.WEST));
        assertTrue(directions.contains(Direction.SOUTH));
        assertTrue(directions.contains(Direction.EAST));
    }
    
    @Test
    public void getAvailableDirectionsAtSouthEastCorner() {
        Board testBoard = new Board(7);
        List directions = testBoard.getAvailableDirectionsAt(new Location(6,0,7));
        assertTrue(directions.size() == 2);
        assertTrue(directions.contains(Direction.WEST));
        assertTrue(directions.contains(Direction.NORTH));
    }

}
