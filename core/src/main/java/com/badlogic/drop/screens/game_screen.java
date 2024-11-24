package com.badlogic.drop.screens;

import com.badlogic.drop.Bird;
import com.badlogic.drop.MyGame;
import com.badlogic.drop.Pig;
import com.badlogic.drop.physics.LevelGenerator;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.Serializable;
import java.util.ArrayList;

public class game_screen implements Screen, Serializable {
    private final MyGame g_original_game_variable;
    private Stage g_stage;
    private FitViewport g_viewport;
    private Texture g_background;
    private ArrayList<Bird> g_bird_list;
    private Bird g_bird_on_catapult;
    private ImageButton g_catapult, g_pause_button, g_back_button;
    private Box2DDebugRenderer g_debug_renderer;
    private int current_bird_index = 0;

    private ArrayList<Pig> g_pig_list;

    private TextButton g_win_button;
    private TextButton g_lose_button;
    private Skin g_skin;

    private LevelGenerator g_level_generator;
    private World world;

    // Bird drag variables
    private Vector2 initialBirdPosition;
    private boolean isDragging = false;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Vector2 dragStart;
    private Vector2 dragEnd;
    private ShapeRenderer shapeRenderer;
    private User current_user;

    public game_screen(MyGame game) {
        this.current_user = game.current_user;
        this.g_original_game_variable = game;
        this.g_viewport = new FitViewport(1920, 1080);
        this.g_stage = new Stage(g_viewport);
        this.world = new World(new Vector2(0, -9.8f), true);
        shapeRenderer = new ShapeRenderer();
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();
        make_ground();
        this.g_background = new Texture("game_screen.png");
        this.g_bird_list = new ArrayList<>();
        this.g_debug_renderer = new Box2DDebugRenderer();

        this.g_level_generator = new LevelGenerator(g_stage);
        this.g_level_generator.generateLevel(bodyDef, fixtureDef, world);

        this.g_pig_list = new ArrayList<>();
        g_initialize_pigs();

        // Set the initial bird position over the catapult
        initialBirdPosition = new Vector2(13000, 22000); // Example catapult position, adjust as needed
        g_bird_list.add(new Bird(world, "Birdimages/bigbird.png", initialBirdPosition));
        this.g_bird_on_catapult = g_bird_list.get(current_bird_index);

        g_bird_on_catapult.setPosition(initialBirdPosition);

        g_initialize_birds();

        g_create_UI();

        g_bird_on_catapult.getBody().setAwake(false);
        addContactListener();
        // Start with the first bird in the queue
    }

    private void g_initialize_pigs() {
        g_pig_list.add(new Pig(world, "Pigimages/pig.png", new Vector2(15000, 22000), 100));
        g_pig_list.add(new Pig(world, "Pigimages/pig2.png", new Vector2(16000, 22000), 100));

    }

    private void g_initialize_birds() {
        g_bird_list.add(new Bird(world, "Birdimages/redbird.png", initialBirdPosition));
        g_bird_list.add(new Bird(world, "Birdimages/yellowbird.png", initialBirdPosition));
        g_bird_list.add(new Bird(world, "Birdimages/blackbird.png", initialBirdPosition));
    }

    private void g_create_UI() {
        Table table = new Table();
        table.debug(Table.Debug.all);
        table.setFillParent(true);
        g_stage.addActor(table);

        this.g_catapult = g_create_button("catapult.png");
        this.g_pause_button = g_create_button("pause.png");
        this.g_back_button = g_create_button("back.png");

        table.add(g_back_button).size(150f, 150f).expand().top().left().pad(20f);
        add_win_lose_screens(table);
        table.add(g_pause_button).size(100f, 100f).top().right().pad(20f);
        table.row();
        table.add(g_catapult).size(200f, 200f).expand().bottom().left().pad(100f);
    }

    private void make_ground() {
        System.out.println("Making ground");
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyDef.position.set(0, 20000);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(-50000000, 0), new Vector2(50000000, 0) });

        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;
        fixtureDef.shape = groundShape;
        world.createBody(this.bodyDef).createFixture(fixtureDef);

        groundShape.dispose();
    }

    private void addContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                // Check if a bird collided with a pig
                if ((a.getBody().getUserData() instanceof Bird && b.getBody().getUserData() instanceof Pig) ||
                        (a.getBody().getUserData() instanceof Pig && b.getBody().getUserData() instanceof Bird)) {
                    Pig pig = (a.getBody().getUserData() instanceof Pig) ? (Pig) a.getBody().getUserData()
                            : (Pig) b.getBody().getUserData();
                    pig.takeDamage(50); // Example damage value
                    // Apply an impulse to the pig to make it move away
                    Vector2 collisionPoint = contact.getWorldManifold().getPoints()[0];
                    Vector2 pigPosition = pig.getBody().getPosition();
                    Vector2 impulseDirection = pigPosition.cpy().sub(collisionPoint).nor();
                    float impulseStrength = 10f; // Adjust this value as needed
                    pig.getBody().applyLinearImpulse(impulseDirection.scl(impulseStrength), pigPosition, true);
                }

                System.out.println("Collision detected between: " + a.getBody() + " and " + b.getBody());
            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("Contact ended");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    private void add_win_lose_screens(Table table) {
        g_skin = new Skin(Gdx.files.internal("uiskin.json"));

        g_win_button = new TextButton("You Win!", g_skin);
        g_lose_button = new TextButton("You Lose!", g_skin);

        g_win_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_original_game_variable.clicksound.play();
                g_original_game_variable.win_screen.dispose();
                g_original_game_variable.win_screen = new win_screen(g_original_game_variable);
                g_original_game_variable.setScreen(g_original_game_variable.win_screen);
                current_user.incrementLevel();
                System.out.println("Level incremented to: " + current_user.getLevel());
            }
        });
        g_lose_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_original_game_variable.clicksound.play();
                g_original_game_variable.lose_screen.dispose();
                g_original_game_variable.lose_screen = new lose_screen(g_original_game_variable);
                g_original_game_variable.setScreen(g_original_game_variable.lose_screen);
            }
        });

        table.add(g_win_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
        table.add(g_lose_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
    }

    private ImageButton g_create_button(String image_path) {
        return new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal(image_path))));
    }

    private void g_change_catapult_bird() {
        current_bird_index = (current_bird_index + 1) % (g_bird_list.size());
        g_bird_on_catapult = g_bird_list.get(current_bird_index);
        g_bird_on_catapult.getBody().setAwake(false);
        while (g_bird_on_catapult.getBody().isAwake()) {
            // Wait for the body to be set to sleep
        }
        g_bird_on_catapult.setPosition(initialBirdPosition); // Reset to initial position
    }

    private Vector2 calculateImpulse(Vector2 start, Vector2 end) {
        Vector2 direction = start.cpy().sub(end); // Vector from release point to start
        float distance = direction.len(); // Calculate the magnitude
        direction.nor(); // Normalize to get the direction
        float forceMultiplier = 1000f; // Adjust this factor to control force
        return direction.scl(distance * forceMultiplier); // Scale by distance
    }

    @Override
    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(g_stage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.UP) {
                    g_change_catapult_bird();
                } else if (keycode == Input.Keys.DOWN) {
                    current_bird_index = (current_bird_index - 1 + g_bird_list.size()) % g_bird_list.size();
                    g_bird_on_catapult = g_bird_list.get(current_bird_index);
                    g_bird_on_catapult.getBody().setAwake(false);
                    g_bird_on_catapult.setPosition(initialBirdPosition); // Reset to initial position
                } else if (keycode == Input.Keys.ESCAPE) {
                    g_original_game_variable.pause_screen.dispose();
                    g_original_game_variable.pause_screen = new pause_screen(g_original_game_variable);
                    g_original_game_variable.setScreen(g_original_game_variable.pause_screen);
                } else if (keycode == Input.Keys.BACKSPACE) {
                    g_original_game_variable.level_screen.dispose();
                    g_original_game_variable.level_screen = new level_screen(g_original_game_variable);
                    g_original_game_variable.setScreen(g_original_game_variable.level_screen);
                } else if (keycode == Input.Keys.ENTER) {
                    float velocityX = (float) (Math.random() * 200000 + 10);
                    float velocityY = (float) (Math.random() * 150000 + 5);
                    g_bird_on_catapult.getBody().setAwake(true); // Wake up the body
                    g_bird_on_catapult.getBody().setLinearVelocity(velocityX, velocityY);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                isDragging = true;
                dragStart = g_stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    dragEnd = g_stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    dragEnd = g_stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    Vector2 impulse = calculateImpulse(dragStart, dragEnd);
                    g_bird_on_catapult.getBody().setAwake(true); // Wake up the body
                    g_bird_on_catapult.getBody().applyLinearImpulse(impulse,
                            g_bird_on_catapult.getBody().getWorldCenter(), true);
                }
                return true;
            }

        });

        for (Pig pig : g_pig_list) {
            g_stage.addActor(pig);
        }

        Gdx.input.setInputProcessor(inputMultiplexer);
        g_debug_renderer.render(world, g_viewport.getCamera().combined);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);

        // Begin drawing
        g_stage.getBatch().begin();
        g_stage.getBatch().draw(g_background, 0, 0, g_viewport.getWorldWidth(), g_viewport.getWorldHeight());

        // Draw the current bird scaled down on the catapult
        if (g_bird_on_catapult != null) {
            Vector2 birdPosition = g_bird_on_catapult.getBody().getPosition();
            float birdWidth = g_bird_on_catapult.getTexture().getWidth() * 0.25f;
            float birdHeight = g_bird_on_catapult.getTexture().getHeight() * 0.25f;
            g_stage.getBatch().draw(g_bird_on_catapult.getTexture(), birdPosition.x, birdPosition.y, birdWidth,
                    birdHeight);
        }

        g_stage.getBatch().end();

        // Draw the drag path
        if (isDragging && dragStart != null && dragEnd != null) {
            // g_stage.getBatch().begin();
            // g_stage.getBatch().setColor(Color.RED);
            // g_stage.getBatch().drawLine(dragStart.x, dragStart.y, dragEnd.x, dragEnd.y);
            // g_stage.getBatch().setColor(Color.WHITE);
            // g_stage.getBatch().end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.line(dragStart.x, dragStart.y, dragEnd.x, dragEnd.y);
            shapeRenderer.end();
        }

        // Stage needs to act and draw UI elements
        g_stage.act(delta);
        g_stage.draw();

        // Step the physics simulation
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        g_viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        g_background.dispose();
        g_stage.dispose();
        world.dispose();
    }
}
