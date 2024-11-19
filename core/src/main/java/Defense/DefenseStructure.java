package Defense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;

public class DefenseStructure extends Actor {
    private Texture texture;
    private Body body;
    private String type;  // "wood", "stone", or "glass"
    private int strength; // Determines how much damage it can take

    public DefenseStructure(BodyDef bodyDef, FixtureDef fixtureDef, World world, String type, String texturePath, int strength, Vector2 position) {
        this.type = type;
        this.strength = strength;
        this.texture = new Texture(Gdx.files.internal(texturePath));

        // Set the position and size based on the texture
        setPosition(position.x, position.y);
        setSize(texture.getWidth(), texture.getHeight());

        // Create body definition
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);

        // Create body in the world
        body = world.createBody(bodyDef);

        // Create shape for the body
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / 100f, getHeight() / 2f / 100f); // Adjusted for Box2D scale

        // Create fixture definition
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Attach fixture to the body
        body.createFixture(fixtureDef);

        // Dispose shape
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw the structure at its position
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void takeDamage(int damage) {
        this.strength -= damage;
        if (this.strength <= 0) {
            this.remove();  // Remove the structure from the stage if it’s destroyed
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Additional behavior (e.g., falling when damaged) can be added here
    }

    public void dispose() {
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }
}
