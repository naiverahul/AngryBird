package com.badlogic.drop.saved_files;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bird extends Actor {
    private Texture birdTexture;
    private Body birdBody;

    public Bird(BodyDef bodyDef, FixtureDef fixtureDef, World world, String texturePath, Vector2 position) {
        birdTexture = new Texture(texturePath);

        // Create body definition
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        // Create body in the world
        birdBody = world.createBody(bodyDef);

        // Create shape for the body
        CircleShape shape = new CircleShape();
        shape.setRadius(birdTexture.getWidth() / 2f / 100f); // Adjusted for Box2D scale

        // Create fixture definition
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Attach fixture to the body
        birdBody.createFixture(fixtureDef);

        // Dispose shape
        shape.dispose();
    }

    // Returns the texture of the bird
    public Texture getTexture() {
        return birdTexture;
    }

    // Returns the Box2D body of the bird
    public Body getBody() {
        return birdBody;
    }
    // Applies an impulse to the bird
    public void applyImpulse(Vector2 impulse) {
        birdBody.applyLinearImpulse(impulse, birdBody.getWorldCenter(), true);
    }

    // Sets the position of the bird
    public void setPosition(Vector2 newPosition) {
        birdBody.setTransform(newPosition.x / 100f, newPosition.y / 100f, birdBody.getAngle());
    }

    // Utility method for setting position using separate x and y values
    public void setPosition(float x, float y) {
        birdBody.setTransform(x / 100f, y / 100f, birdBody.getAngle());
    }

    // Disposes of the bird's texture when no longer needed
    public void dispose() {
        birdTexture.dispose();
    }

    // Check if a point is inside the bird's texture bounds (for dragging)
    public boolean containsPoint(int screenX, int screenY) {
        Vector2 birdPos = birdBody.getPosition();
        float radius = birdTexture.getWidth() / 2f;

        // Convert bird's Box2D position to screen coordinates
        float birdX = birdPos.x * 100f;
        float birdY = birdPos.y * 100f;

        // Check if the click is within the bird's circle bounds
        return Math.pow(screenX - birdX, 2) + Math.pow(screenY - birdY, 2) <= Math.pow(radius, 2);
    }
}
