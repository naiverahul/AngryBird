package JUnit_tests;

import com.badlogic.drop.Bird;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class BirdTest {

    @Test
    public void testBirdInitialization() {
        World world = new World(new Vector2(0, -9.8f), true);
        Bird bird = new Bird(world, "Birdimages/redbird.png", new Vector2(0, 0), 100);
        assertNotNull(bird.getTexture());
        assertNotNull(bird.getBody());
        assertEquals(100, bird.birdhealth);
    }

    @Test
    public void testSetDestroy() {
        World world = new World(new Vector2(0, -9.8f), true);
        Bird bird = new Bird(world, "Birdimages/redbird.png", new Vector2(0, 0), 100);
        bird.setdestroy(new LinkedList<>(), new ArrayList<>(), 0);
        assertTrue(bird.destroy());
    }
}
