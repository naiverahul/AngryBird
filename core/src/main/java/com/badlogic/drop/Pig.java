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

public class Pig extends Actor {
    private Texture pig_texture;
    private Body pig_body;
    private Vector2 initial_position;
    private int health = 10;

    public Pig(World world, String texture_path, Vector2 position) {
        this.pig_texture = new Texture(Gdx.files.internal(texture_path));
        this.initial_position = position;

        // Creating pig body definition
        BodyDef pig_body_def = new BodyDef();
        pig_body_def.type = BodyDef.BodyType.DynamicBody;
        pig_body_def.position.set(position);

        // creting body in the world
        pig_body = world.createBody(pig_body_def);

        // creating shape for the body
        CircleShape shape = new CircleShape();
        shape.setRadius(pig_texture.getWidth() / 2f / 100f);

        // creating fixture definition
        FixtureDef pig_fixture_def = new FixtureDef();
        pig_fixture_def.shape = shape;
        pig_fixture_def.density = 1f;
        pig_fixture_def.friction = 0.5f;
        pig_fixture_def.restitution = 0.3f;

        // Attach fixture to the body
        pig_body.createFixture(pig_fixture_def);

        // disposing the shape
        shape.dispose();

        pig_body.setUserData(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(pig_body.getPosition().x * 100 - pig_texture.getWidth() / 2f,
                pig_body.getPosition().y * 100f - pig_texture.getHeight() / 2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pig_texture, getX(), getY());
    }

    public void dispose() {
        pig_texture.dispose();
    }

    public Texture getTexture() {
        return pig_texture;
    }

    public Body getBody() {
        return pig_body;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove(); // Remove the pig from the stage when health is zero
        }
    }

    public int getHealth(){
        return health;
    }

    // public void setPosition(Vector2 new_position) {
    // pig_body.setTransform(new_position.x / 100f, new_position.y/100f);
    // }

}
