package JUnit_tests;

import com.badlogic.drop.screens.game_screen;
import com.badlogic.drop.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserInitialization() {
        User user = new User("TestUser");
        assertEquals("TestUser", user.getName());
        assertEquals(1, user.getLevel());
        assertEquals(0, user.getScore());
        assertNull(user.getGame_screen());
    }

    @Test
    public void testSetName() {
        User user = new User("TestUser");
        user.setName("NewName");
        assertEquals("NewName", user.getName());
    }

    @Test
    public void testSetLevel() {
        User user = new User("TestUser");
        user.setLevel(5);
        assertEquals(5, user.getLevel());
    }

    @Test
    public void testSetScore() {
        User user = new User("TestUser");
        user.setScore(200);
        assertEquals(200, user.getScore());
    }

    @Test
    public void testSetGameScreen() {
        User user = new User("TestUser");
        game_screen screen = new game_screen(null);
        user.setGame_screen(screen);
        assertEquals(screen, user.getGame_screen());
    }

    @Test
    public void testIncrementLevel() {
        User user = new User("TestUser");
        user.incrementLevel();
        assertEquals(2, user.getLevel());
        assertEquals(100, user.getScore());
    }
}
