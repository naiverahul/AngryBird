package com.badlogic.drop;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Bird extends Actor {
    private Texture birdTexture;
    private Body birdBody;
    private Vector2 initialPosition;
    private boolean isDragging;
    private Vector2 dragStart;
    private Random random = new Random();
    private int damage = 1 + random.nextInt(10);
    private int health = 0;
    private boolean destry=false;
    private FixtureDef fixtureDef;
    public int birdhealth;

    public int getDamage() {
        return damage;
    }
    public int getHealth(){
        return health;
    }

    public boolean destroy(){

        return this.destry;
    }
    public void setdestroy(Queue <Body> bodiestodestroy,ArrayList <Bird> birds,int current_bird_index){
        if(current_bird_index<birds.size()) {
            birds.remove(current_bird_index);
        }
        bodiestodestroy.add(birdBody);
        this.destry = true;
    }

    public Bird(World world, String texturePath, Vector2 position, int birdhealth) {
        this.birdTexture = new Texture(Gdx.files.internal(texturePath));
        this.initialPosition = position;
        this.isDragging = false;
        this.birdhealth = birdhealth;
        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        // Create body in the world
        birdBody = world.createBody(bodyDef);

        // Create shape for the body
        CircleShape shape = new CircleShape();
        shape.setRadius(birdTexture.getWidth() / 10f); // Adjusted for Box2D scale

        // Create fixture definition
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Attach fixture to the body
        birdBody.createFixture(fixtureDef);

        // Dispose shape
        shape.dispose();

        // Add input listener for dragging
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isDragging = true;
                dragStart = new Vector2(x, y);
                birdBody.setAwake(!isDragging);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (isDragging) {
                    Vector2 dragEnd = new Vector2(x, y);
                    Vector2 dragVector = dragStart.cpy().sub(dragEnd);
                    birdBody.setTransform(initialPosition.cpy().sub(dragVector.scl(0.1f)), birdBody.getAngle());
                    isDragging = false;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    Vector2 dragEnd = new Vector2(x, y);
                    Vector2 dragVector = dragStart.cpy().sub(dragEnd);
                    Vector2 impulse = dragVector.scl(10f); // Adjust this factor to control launch force
                    birdBody.applyLinearImpulse(impulse, birdBody.getWorldCenter(), true);
                }
            }
        });

        birdBody.setUserData(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Update the actor's position to match the body's position
        setPosition(birdBody.getPosition().x * 100f - birdTexture.getWidth() / 2f,
            birdBody.getPosition().y * 100f - birdTexture.getHeight() / 2f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(birdTexture, getX(), getY());
    }

    public void dispose() {
        birdTexture.dispose();
    }

    public Texture getTexture() {
        return birdTexture;
    }

    public Body getBody() {
        return birdBody;
    }

    public void setPosition(Vector2 newPosition) {
        birdBody.setTransform(newPosition.x / 100f, newPosition.y / 100f, birdBody.getAngle());
    }
}
