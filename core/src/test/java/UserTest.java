import com.badlogic.drop.screens.game_screen;
import com.badlogic.drop.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {


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
    public void testIncrementLevel() {
        User user = new User("TestUser");
        user.incrementLevel();
        assertEquals(2, user.getLevel());
        assertEquals(100, user.getScore());
    }
}
