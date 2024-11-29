package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class block_struct extends Actor {

    private Texture block_texture;
    private Body block_body;
    private Vector2 initial_position;
    private int health = 10;
    private boolean destroy = false;

    private static final float SCALE_FACTOR = 5f; // Texture is 5 times smaller than Box2D size
    private static final float PPM = 100f;       // Pixels per meter (scale for rendering)

    public block_struct(World lg_world, String block_texture_path, Vector2 block_position, float block_size) {
        this.initial_position = block_position;
        this.block_texture = new Texture(Gdx.files.internal(block_texture_path));

        // Calculate the scaled texture size in pixels
        float texture_width = block_texture.getWidth() / SCALE_FACTOR;
        float texture_height = block_texture.getHeight() / SCALE_FACTOR;

        // Set the Actor size for rendering
        this.setSize(texture_width, texture_height);

        // Create Box2D body definition
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(initial_position);

        block_body = lg_world.createBody(bd);

        // Define the Box2D shape with the desired block size
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(block_size / 2f, block_size / 2f); // Half-width and half-height in world units

        // Define the fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.5f;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        block_body.createFixture(fd);

        shape.dispose();

        block_body.setUserData(this); // Attach this Actor to the body
    }

    public boolean destroy() {
        return this.destroy;
    }

    public void setdestroy() {
        this.destroy = true;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove(); // Remove the structure from the stage if it’s destroyed
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Sync the actor's position with the Box2D body's position
        setPosition((block_body.getPosition().x - getWidth() / 2f / PPM) * PPM,
            (block_body.getPosition().y - getHeight() / 2f / PPM) * PPM);
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        // Draw the texture scaled to the actor's size
        batch.draw(block_texture, getX(), getY(), getWidth(), getHeight());
    }

    public void dispose() {
        block_texture.dispose();
    }

    public Body getBody() {
        return block_body;
    }

    public Texture getTexture() {
        return block_texture;
    }
}
