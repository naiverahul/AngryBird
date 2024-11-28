import com.badlogic.drop.MyGame;
import com.badlogic.drop.screens.game_screen;
import com.badlogic.drop.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class game_screenTest {

    @Test
    public void testSaveAndLoadGameState() {
        User user = new User("TestUser");
        MyGame game = new MyGame(user);
        game_screen screen = new game_screen(game);
        screen.saveGameState();
        game_screen loadedScreen = game_screen.loadGameState(game);
        assertNotNull(loadedScreen);
    }
}
