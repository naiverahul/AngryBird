package JUnit_tests;

import com.badlogic.drop.Pig;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PigTest {

    @Test
    public void testPigInitialization() {
        World world = new World(new Vector2(0, -9.8f), true);
        Pig pig = new Pig(world, "Pigimages/pig.png", new Vector2(0, 0));
        assertNotNull(pig.getTexture());
        assertNotNull(pig.getBody());
        assertEquals(100, pig.pighealth);
    }

    @Test
    public void testTakeDamage() {
        World world = new World(new Vector2(0, -9.8f), true);
        Pig pig = new Pig(world, "Pigimages/pig.png", new Vector2(0, 0));
        pig.takeDamage(10);
        assertEquals(0, pig.getHealth());
    }
}
