package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class block_struct extends Actor {

    private Texture block_texture;
    private Body block_body;
    private Vector2 initial_position;
    private int health = 10;

    // public block_struct(BodyDef bd, FixtureDef fd, World lg_world, String
    // block_texture_path, Vector2 block_position, int size) {
    public block_struct(World lg_world, String block_texture_path, Vector2 block_position, int size) {
        this.initial_position = block_position;
        this.block_texture = new Texture(Gdx.files.internal(block_texture_path));

        // this.setPosition(initial_position.x, initial_position.y);
        // this.setSize(size, size);

        // bd.type = BodyDef.BodyType.DynamicBody;
        // bd.position.set(initial_position);

        BodyDef bd = new BodyDef();
        bd.type = BodyType.DynamicBody;
        bd.position.set(initial_position);

        block_body = lg_world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        // shape.setAsBox(block_texture.getWidth()/2f/100f,block_texture.getHeight()/2f/100f);
        // shape.setAsBox(size,size);
        // shape.setAsBox(Math.max(block_texture.getWidth(),block_texture.getHeight()),
        // );
        shape.setAsBox(block_texture.getWidth() / 3f, block_texture.getHeight() / 3f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        block_body.createFixture(fd);

        shape.dispose();

        block_body.setUserData(this);
    }

    // @Override
    // public void draw(){}

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.remove(); // Remove the structure from the stage if it’s destroyed
        }
    }
    public int getHealth(){
        return health;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Additional behavior (e.g., falling when damaged) can be added here
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
