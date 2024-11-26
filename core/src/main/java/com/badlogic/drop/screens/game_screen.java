package com.badlogic.drop.screens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.drop.Bird;
import com.badlogic.drop.MyGame;
import com.badlogic.drop.Pig;
import com.badlogic.drop.block_struct;
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

public class game_screen implements Screen, Serializable {
    private final MyGame ori_game_variable;
    private final float gravity = -9.8f;
    private User current_user;

    private World world;
    private Stage stage;
    private FitViewport viewport;
    private Box2DDebugRenderer deburenderer;
    private Skin skin;
    private BodyDef body_def;
    private FixtureDef fixture_def;

    private Texture background;
    private ImageButton pause, back, catapult;
    private TextButton win_button, lose_button;

    private LevelGenerator level_generator;
    private ShapeRenderer shape_renderer;
    private ArrayList<Bird> birds;
    private Bird current_bird;
    private int current_bird_index = 0;
    private Vector2 initial_bird_position, drastart, draend;
    private boolean is_dragging = false;

    // defense structure drawing region
    private final int[] min = { 1100, 150 }, max = { 1700, 400 };
    private final int block_height = 60, max_structures = 5;
    private int num_structures;
    private Random random = new Random();
    private String pigs_base = "Pigimages/pig", block_base_directory = "Structures/";
    private String[] pig_textures = { ".png", "2.png", "3.png", "4.png" }, block_types = { "wood", "stone", "glass" };
    private ArrayList<Pig> pig_list = new ArrayList<Pig>();
    private ArrayList<Vector2> pig_positions = new ArrayList<Vector2>();
    private ArrayList<ArrayList<block_struct>> block_list = new ArrayList<ArrayList<block_struct>>();
    private ArrayList<ArrayList<Vector2>> block_position_list = new ArrayList<ArrayList<Vector2>>();

    public game_screen(MyGame game) {
        this.ori_game_variable = game;
        this.current_user = game.current_user;

        this.viewport = new FitViewport(1920, 1080);
        this.stage = new Stage(viewport);
        this.world = new World(new Vector2(0, gravity), true);
        this.shape_renderer = new ShapeRenderer();
        this.body_def = new BodyDef();
        this.fixture_def = new FixtureDef();
        this.background = new Texture("game_screen.png");
        this.birds = new ArrayList<Bird>();
        this.deburenderer = new Box2DDebugRenderer();
        this.level_generator = new LevelGenerator(ori_game_variable, world, stage);

        create_ui();
        create_contact_listener();
    }

    private void create_ui() {
        make_ground();
        initialize_birds();
        initialize_defense_structure();
        // level_generator.generateLevel(body_def, fixture_def);
        this.catapult = new ImageButton(new TextureRegionDrawable(new Texture("catapult.png")));
        this.back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        this.pause = new ImageButton(new TextureRegionDrawable(new Texture("pause.png")));

        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table();
        table.debug(Table.Debug.all);
        table.setFillParent(true);
        stage.addActor(table);

        table.add(back).size(150f, 150f).expand().top().left().pad(20f);
        add_win_lose_screens(table);
        table.add(pause).size(100f, 100f).top().right().pad(20f);
        table.row();
        table.add(catapult).size(200f, 200f).expand().bottom().left().pad(100f);
    }

    private void make_ground() {
        System.out.println("Making ground");
        this.body_def.type = BodyDef.BodyType.StaticBody;
        this.body_def.position.set(0, 0);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[] { new Vector2(-50000000, 0), new Vector2(50000000, 0) });

        fixture_def.friction = 0.5f;
        fixture_def.restitution = 0;
        fixture_def.shape = groundShape;
        world.createBody(this.body_def).createFixture(fixture_def);

        groundShape.dispose();
    }

    private void initialize_birds() {
        initial_bird_position = new Vector2(13000, 22000);

        birds.add(new Bird(world, "Birdimages/bigbird.png", initial_bird_position));
        birds.add(new Bird(world, "Birdimages/redbird.png", initial_bird_position));
        birds.add(new Bird(world, "Birdimages/yellowbird.png", initial_bird_position));
        birds.add(new Bird(world, "Birdimages/blackbird.png", initial_bird_position));

        current_bird = birds.get(current_bird_index);
        current_bird.setPosition(initial_bird_position);
        current_bird.getBody().setAwake(false);
    }

    private void initialize_defense_structure() {
        num_structures = random.nextInt(max_structures) + 1;
        int[] structure_positions = new int[num_structures];
        for (int structure_count = 0; structure_count < num_structures; structure_count++) {
            // while (true) // check if the structure is not overlapping with any other
            // structure
            // {
            // int baseX = min[0] + random.nextInt(max[0] - min[0]);
            // }
            int baseX = min[0] + random.nextInt(max[0] - min[0]);
            structure_positions[structure_count] = baseX;
            int structure_height = 3 + random.nextInt(3);
            ArrayList<block_struct> structure = new ArrayList<block_struct>();
            ArrayList<Vector2> block_positions = new ArrayList<Vector2>();
            for (int i = 0; i < structure_height; i++) {
                Vector2 block_position = new Vector2(baseX, min[1] + i * block_height);
                structure.add(
                        new block_struct(world, block_base_directory + block_types[random.nextInt(3)] + "/square.png",
                                block_position, block_height));
                block_positions.add(block_position);
            }
            block_list.add(structure);
            block_position_list.add(block_positions);

            int pigY = min[1] + (structure_height * block_height);
            Vector2 pig_position = new Vector2(baseX, pigY);
            pig_list.add(new Pig(world, pigs_base + pig_textures[structure_count % pig_textures.length], pig_position));
            pig_positions.add(pig_position);

        }
    }

    private void create_contact_listener() {
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
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        win_button = new TextButton("You Win!", skin);
        lose_button = new TextButton("You Lose!", skin);

        win_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ori_game_variable.clicksound.play();
                ori_game_variable.win_screen.dispose();
                ori_game_variable.win_screen = new win_screen(ori_game_variable);
                ori_game_variable.setScreen(ori_game_variable.win_screen);
                current_user.incrementLevel();
                System.out.println("Level incremented to: " + current_user.getLevel());
            }
        });
        lose_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ori_game_variable.clicksound.play();
                ori_game_variable.lose_screen.dispose();
                ori_game_variable.lose_screen = new lose_screen(ori_game_variable);
                ori_game_variable.setScreen(ori_game_variable.lose_screen);
            }
        });

        table.add(win_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
        table.add(lose_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
    }

    private void change_catapult_bird() {
        current_bird_index = (current_bird_index + 1) % (birds.size());
        current_bird = birds.get(current_bird_index);
        current_bird.getBody().setAwake(false);
        while (current_bird.getBody().isAwake()) {
            // Wait for the body to be set to sleep
        }
        current_bird.setPosition(initial_bird_position); // Reset to initial position
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
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.UP) {
                    change_catapult_bird();
                } else if (keycode == Input.Keys.DOWN) {
                    current_bird_index = (current_bird_index - 1 + birds.size()) % birds.size();
                    current_bird = birds.get(current_bird_index);
                    current_bird.getBody().setAwake(false);
                    current_bird.setPosition(initial_bird_position); // Reset to initial position
                } else if (keycode == Input.Keys.ESCAPE) {
                    ori_game_variable.pause_screen.dispose();
                    ori_game_variable.pause_screen = new pause_screen(ori_game_variable);
                    ori_game_variable.setScreen(ori_game_variable.pause_screen);
                } else if (keycode == Input.Keys.BACKSPACE) {
                    ori_game_variable.level_screen.dispose();
                    ori_game_variable.level_screen = new level_screen(ori_game_variable);
                    ori_game_variable.setScreen(ori_game_variable.level_screen);
                } else if (keycode == Input.Keys.ENTER) {
                    float velocityX = (float) (Math.random() * 200000 + 10);
                    float velocityY = (float) (Math.random() * 150000 + 5);
                    current_bird.getBody().setAwake(true); // Wake up the body
                    current_bird.getBody().setLinearVelocity(velocityX, velocityY);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                is_dragging = true;
                drastart = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (is_dragging) {
                    draend = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (is_dragging) {
                    is_dragging = false;
                    draend = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    Vector2 impulse = calculateImpulse(drastart, draend);
                    current_bird.getBody().setAwake(true); // Wake up the body
                    current_bird.getBody().applyLinearImpulse(impulse,
                            current_bird.getBody().getWorldCenter(), true);
                }
                return true;
            }

        });

        // for (Pig pig : pilist) {
        // stage.addActor(pig);
        // }

        Gdx.input.setInputProcessor(inputMultiplexer);
        // deburenderer.render(world, viewport.getCamera().combined);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);

        // Begin drawing
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the current bird scaled down on the catapult
        if (current_bird != null) {
            Vector2 birdPosition = current_bird.getBody().getPosition();
            float birdWidth = current_bird.getTexture().getWidth() * 0.25f;
            float birdHeight = current_bird.getTexture().getHeight() * 0.25f;
            stage.getBatch().draw(current_bird.getTexture(), birdPosition.x, birdPosition.y, birdWidth,
                    birdHeight);
        }

        // draw the defense structure here
        for (int structure_count = 0; structure_count < num_structures; structure_count++) {
            int i = 0;
            for (block_struct block : block_list.get(structure_count)) {
                Vector2 block_position = block_position_list.get(structure_count).get(i);
                // block.setPosition(block_position.x, block_position.y);
                // stage.getBatch().draw(block.getTexture(), block_position.x, block_position.y, block.getWidth()*0.25f, block.getHeight()*0.25f);
                stage.getBatch().draw(block.getTexture(), block.getBody().getPosition().x, block.getBody().getPosition().y, block.getTexture().getWidth()*0.5f, block.getTexture().getHeight()*0.5f);
                i++;
            }
            Vector2 pig_position = pig_positions.get(structure_count);
            Pig pig = pig_list.get(structure_count);
            // pig_list.get(structure_count).setPosition(pig_positions.get(structure_count).x,
                    // pig_positions.get(structure_count).y);
            // stage.getBatch().draw(pig.getTexture(), pig_position.x, pig_position.y, pig.getWidth()*0.25f, pig.getHeight()*0.25f);
            stage.getBatch().draw(pig.getTexture(), pig.getBody().getPosition().x, pig.getBody().getPosition().y, pig.getTexture().getWidth()*0.15f, pig.getTexture().getHeight()*0.15f);
        }

        stage.getBatch().end();

        // Draw the drag path
        if (is_dragging && drastart != null && draend != null) {
            // stage.getBatch().begin();
            // stage.getBatch().setColor(Color.RED);
            // stage.getBatch().drawLine(drastart.x, drastart.y, draend.x, draend.y);
            // stage.getBatch().setColor(Color.WHITE);
            // stage.getBatch().end();

            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(Color.RED);
            shape_renderer.line(drastart.x, drastart.y, draend.x, draend.y);
            shape_renderer.end();
        }

        // Stage needs to act and draw UI elements
        stage.act(delta);
        stage.draw();

        // Step the physics simulation
        world.step(1 / 60f, 10, 4);
        // g_debug_renderer.render(world, viewport.getCamera().combined);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        background.dispose();
        stage.dispose();
        world.dispose();
    }

}
