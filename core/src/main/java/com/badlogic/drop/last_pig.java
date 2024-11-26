package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class last_pig extends Actor {
    private Texture pigTexture;
    private Body pigBody;
    private int health;

    public last_pig(World world, String texturePath, Vector2 position, int health) {
        this.pigTexture = new Texture(Gdx.files.internal(texturePath));
        this.health = health;

        // Create body definition
        BodyDef bodyDef = new BodyDef();
        // bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        // Create body in the world
        pigBody = world.createBody(bodyDef);

        // Create shape for the body
        CircleShape shape = new CircleShape();
        shape.setRadius(pigTexture.getWidth() / 2f / 100f); // Adjusted for Box2D scale

        // Create fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Attach fixture to the body
        pigBody.createFixture(fixtureDef);

        // Dispose shape
        shape.dispose();

        pigBody.setUserData(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // // Update the actor's position to match the body's position
        // // setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);

        // setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);

        setPosition(pigBody.getPosition().x * 100f - pigTexture.getWidth() / 2f, pigBody.getPosition().y * 100f - pigTexture.getHeight() / 2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pigTexture, getX(), getY());
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove();  // Remove the pig from the stage when health is zero
        }
    }

    public void dispose() {
        pigTexture.dispose();
    }

    public Texture getTexture() {
        return pigTexture;
    }

    public Body getBody() {
        return pigBody;
    }

    public int getHealth() {
        return health;
    }
}