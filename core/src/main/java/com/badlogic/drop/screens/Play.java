package com.badlogic.drop.screens;

// import PhysicsActors.PhysicsActor;

import com.badlogic.drop.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Play implements Screen {
    private final float PIXELS_TO_METERS = 32f;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
    private MyGame game;

    public Play(MyGame game) {
        this.game = game;
    }


    @Override
    public void show() {
        world = new World(new Vector2(0, -9.80665f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / PIXELS_TO_METERS,
            Gdx.graphics.getHeight() / PIXELS_TO_METERS);

        // Input processing
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(game.pause_screen);
                    return true;
                }
                return false;
            }
        });

        // Define body for the ball
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 10);

        // Define shape for the ball
        CircleShape shape = new CircleShape();
        shape.setRadius(1f); // 1 meter radius

        // Define fixture for the ball
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        // Create the body and fixture
        Body ballBody = world.createBody(bodyDef);
        ballBody.createFixture(fixtureDef);


        shape.dispose();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-55, 0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-500, 0), new Vector2(500, 0)});

        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        fixtureDef.shape = groundShape;
        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);


        //boxhshape
        PolygonShape boxshape = new PolygonShape();
        boxshape.setAsBox(0.5f, 2f);

        fixtureDef.shape = boxshape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;


    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update physics simulation
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        // Render Box2D debug view
        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
