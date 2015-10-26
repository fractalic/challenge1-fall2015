import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void testPlayer() {
        Player testPlayer = new Player("ben", Player.Type.HUMAN);
    }

    @Test(expected = InvalidStateException.class)
    public void testGetUnsetLocation() {
        Player testPlayer = new Player("ben", Player.Type.HUMAN);
        testPlayer.getLocation();
    }
    
    @Test
    public void testGetName() {
        Player testPlayer = new Player("ben", Player.Type.BOT);
        assertEquals("ben", testPlayer.getName());
    }
    
    @Test
    public void testGetType() {
        Player testPlayer = new Player("ben", Player.Type.BOT_HARD);
        assertEquals(Player.Type.BOT_HARD, testPlayer.getType());
    }

    @Test
    public void testSetLocation() {
        Player testPlayer = new Player("ben", Player.Type.HUMAN);
        Location testLocation = new Location(10, 20, 100);
        testPlayer.setLocation(testLocation);
        assertEquals(testLocation,testPlayer.getLocation());
    }
    
    @Test
    public void testCloneAndID() {
        Player player1 = new Player("ben", Player.Type.BOT);
        player1.setLocation(new Location(0,0, 10));
        Player player2 = player1.clone();
        
        assertTrue(player1.getID() != player2.getID());
        assertEquals(player1.getSharedID(), player2.getSharedID());
        
        assertEquals(player1.getLocation(), player2.getLocation());
        assertEquals(player1.getName(), player2.getName());
        assertEquals(player1.getType(), player2.getType());
    }

}
