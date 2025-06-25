package com.badlogic.drop.screens;

import java.io.*;
import java.util.*;

import com.badlogic.drop.Bird;
import com.badlogic.drop.MyGame;
import com.badlogic.drop.Pig;
import com.badlogic.drop.block_struct;
import com.badlogic.drop.physics.LevelGenerator;
import com.badlogic.drop.user.GameState;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class game_screen implements Screen, Serializable {
    private transient final MyGame ori_game_variable;
    private transient final float gravity = -9.8f;
    private transient User current_user;

    private transient World world;
    private transient Stage stage;
    private transient FitViewport viewport;
    private transient Box2DDebugRenderer deburenderer;
    private transient Skin skin;
    private transient BodyDef body_def;
    private transient FixtureDef fixture_def;

    private transient Texture background;
    private transient ImageButton pause, back, catapult;
    private transient TextButton win_button, lose_button;

    private transient LevelGenerator level_generator;
    private transient ShapeRenderer shape_renderer;
    private transient ArrayList<Bird> birds;
    private transient Bird current_bird;
    private int current_bird_index = 0;
    private transient Vector2 initial_bird_position, drastart, draend;
    private transient boolean is_dragging = false;
    private transient Queue<Body> bodiesToDestroy;
    private boolean isLaunched;

    // defenste structure drawing region
    private transient final int[] min = {900, 150}, max = {1400, 400};
    private transient final int block_height = 60, max_structures = 5;
    private transient int num_structures;
    private transient Random random = new Random();
    private transient String pigs_base = "Pigimages/pig", block_base_directory = "Structures/";
    private transient String[] pig_textures = {".png", "2.png", "3.png", "4.png"}, block_types = {"wood", "stone", "glass"};
    private transient ArrayList<Pig> pig_list = new ArrayList<Pig>();
    private transient ArrayList<ArrayList<block_struct>> block_list = new ArrayList<ArrayList<block_struct>>();

    private ArrayList<ArrayList<Vector2>> block_position_list = new ArrayList<ArrayList<Vector2>>();
    private ArrayList<Vector2> pig_positions = new ArrayList<Vector2>();
    private ArrayList<Integer> pig_textures_used = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> block_type_list = new ArrayList<ArrayList<Integer>>();

    public game_screen(MyGame game) {
        this.ori_game_variable = game;
        this.current_user = game.current_user;

        // load the 4 Array lists from the ser file and fill them up. other things
        // should work fine.

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
        this.bodiesToDestroy = new LinkedList<>();
        this.isLaunched = false;
        create_ui();
        create_contact_listener();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveGameState();
            System.out.println("Saved Game State 1");
        }));
    }

    public void saveGameState() {
        GameState gameState = new GameState();
        gameState.setBlock_position_list(this.block_position_list);
        gameState.setPig_positions(this.pig_positions);
        gameState.setPig_textures_used(this.pig_textures_used);
        gameState.setBlock_type_list(this.block_type_list);
        gameState.bird_position = current_bird.getBody().getPosition();
        gameState.bird_velocity = current_bird.getBody().getLinearVelocity();
        gameState.current_bird_index = current_bird_index;
        current_user.setGameState(gameState);
    }

    public game_screen loadGameState(MyGame game) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.ser"))) {
//            HashMap<> init_user = (HashMap<String,User>) ois.readObject();
            GameState gameState = current_user.getGameState();
            if(gameState == null) {
                System.out.println("Game state is null");
                return null;
            }
            game_screen screen = new game_screen(game);
//            screen.block_position_list = gameState.getBlock_position_list();
//            screen.pig_positions = gameState.getPig_positions();
//            screen.pig_textures_used = gameState.getPig_textures_used();
//            screen.block_type_list = gameState.getBlock_type_list();
            screen.current_bird_index = gameState.current_bird_index;
            screen.current_bird = screen.birds.get(screen.current_bird_index);
            screen.initial_bird_position = gameState.bird_position;
            screen.current_bird.getBody().setTransform(gameState.bird_position, 0);
            screen.current_bird.getBody().setLinearVelocity(gameState.bird_velocity);
            System.out.println(gameState.bird_position);
            System.out.println(gameState.bird_velocity);
            System.out.println(gameState.current_bird_index);
            System.out.println(screen.block_position_list.size());
            System.out.println(screen.pig_positions.size());
            System.out.println(screen.pig_textures_used.size());
            System.out.println(screen.block_type_list.size());
            return screen;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading game state");
            Gdx.app.exit();
            return null;
        }
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
        table.setFillParent(true);
        stage.addActor(table);

        // Add level number label
        LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.NAVY);
        Label levelLabel = new Label("Level: " + current_user.getLevel(), labelStyle);
        table.add(levelLabel).top().left().pad(10f);
        table.row();

        table.add(back).size(150f, 150f).expand().top().left().pad(20f);
        add_win_lose_screens(table);
        table.add(pause).size(100f, 100f).top().right().pad(20f);
        table.row();
        table.add(catapult).size(200f, 200f).expand().bottom().left().pad(100f);
    }

    private void make_ground() {
        System.out.println("Making ground");
        this.body_def.type = BodyDef.BodyType.StaticBody;
        this.body_def.position.set(0, 120);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-50000000, 0), new Vector2(50000000, 0)});

        fixture_def.friction = 0.5f;
        fixture_def.restitution = 0;
        fixture_def.shape = groundShape;
        world.createBody(this.body_def).createFixture(fixture_def);

        groundShape.dispose();
    }

    private boolean pigs_destroyed() {
        boolean status = true;
        for (Pig pig : pig_list) {
            status = status && pig.destroy();
            if (!status) {
                break;
            }
        }
        return status;
    }

    private void initialize_birds() {
        initial_bird_position = new Vector2(13000, 22000);

        Bird bird1 = new Bird(world, "Birdimages/bigbird.png", initial_bird_position, 120);
        Bird bird2 = new Bird(world, "Birdimages/redbird.png", initial_bird_position, 100);
        Bird bird3 = new Bird(world, "Birdimages/yellowbird.png", initial_bird_position, 90);
        Bird birds4 = new Bird(world, "Birdimages/blackbird.png", initial_bird_position, 60);
        birds.add(bird1);
        birds.add(bird2);
        birds.add(bird3);
        birds.add(birds4);
        current_bird = birds.get(current_bird_index);
        current_bird.setPosition(initial_bird_position);
        current_bird.getBody().setAwake(false);
    }

    private void initialize_defense_structure() {
        if (pig_textures_used.size() != 0 && block_type_list.size() != 0) {
            num_structures = pig_textures_used.size();
            for (int structure_count = 0; structure_count < num_structures; structure_count++) {
                int num_blocks = block_type_list.get(structure_count).size();
                ArrayList<block_struct> structure = new ArrayList<block_struct>();
                for (int i = 0; i < num_blocks; i++) {
                    int type = block_type_list.get(structure_count).get(i);
                    structure.add(new block_struct(world, block_base_directory + block_types[type] + "/sqaure.png",
                        block_position_list.get(structure_count).get(i), block_height));
                }

                pig_list.add(new Pig(world, pigs_base + pig_textures[pig_textures_used.get(structure_count)],
                    pig_positions.get(structure_count)));
            }
        } else {
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
                ArrayList<Integer> block_types_used = new ArrayList<Integer>();
                for (int i = 0; i < structure_height; i++) {
                    Vector2 block_position = new Vector2(baseX, min[1] + i * block_height);
                    int type = random.nextInt(block_types.length);
                    structure.add(
                        new block_struct(world, block_base_directory + block_types[type] + "/square.png",
                            block_position, block_height));
                    block_positions.add(block_position);
                    block_types_used.add(type);

                }
                block_list.add(structure);
                block_position_list.add(block_positions);
                block_type_list.add(block_types_used);

                int pigY = min[1] + (structure_height * block_height);
                Vector2 pig_position = new Vector2(baseX, pigY);
                pig_list.add(
                    new Pig(world, pigs_base + pig_textures[structure_count % pig_textures.length], pig_position));
                pig_textures_used.add(structure_count % pig_textures.length);
                pig_positions.add(pig_position);

            }
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
                    // pig.takeDamage(10); // Example damage value

                    Bird bird = (Bird) ((a.getBody().getUserData() instanceof Bird) ? a : b).getBody().getUserData();
                    if (bird.birdhealth > 0) {
                        bird.birdhealth -= 50;
                    } else {
                        bird.setdestroy(bodiesToDestroy, birds, current_bird_index);
                    }
                    if (pig.pighealth > 0) {
                        pig.pighealth -= 50;
                    } else {
                        pig.setdestroy(bodiesToDestroy);
                    }
                    // Apply an impulse to the pig to make it move away
                    Vector2 collisionPoint = contact.getWorldManifold().getPoints()[0];
                    Vector2 pigPosition = pig.getBody().getPosition();
                    Vector2 impulseDirection = pigPosition.cpy().sub(collisionPoint).nor();
                    float impulseStrength = 10f; // Adjust this value as needed
                    pig.getBody().applyLinearImpulse(impulseDirection.scl(impulseStrength), pigPosition, true);
                } else if ((a.getBody().getUserData() instanceof Bird
                    && b.getBody().getUserData() instanceof block_struct)
                    || (b.getBody().getUserData() instanceof Bird
                    && a.getBody().getUserData() instanceof block_struct)) {
                    block_struct block = (block_struct) ((a.getBody().getUserData() instanceof block_struct) ? a : b)
                        .getBody().getUserData();
                    // block.takeDamage(10);

                    Bird bird = (Bird) ((a.getBody().getUserData() instanceof Bird) ? a : b).getBody().getUserData();
                    if (bird.birdhealth > 0) {
                        bird.birdhealth -= 20;
                    } else {
                        bird.setdestroy(bodiesToDestroy, birds, current_bird_index);
                    }

                    Vector2 collisionPoint = contact.getWorldManifold().getPoints()[0];
                    Vector2 blockPosition = block.getBody().getPosition();
                    Vector2 impulseDirection = blockPosition.cpy().sub(collisionPoint).nor();
                    float impulseForce = 10000f;
                    block.getBody().applyLinearImpulse(impulseDirection.scl(impulseForce), blockPosition, true);

                } else if (((a.getBody().getUserData() instanceof Pig) && (b.getBody().getUserData() instanceof Pig)) ||
                    ((a.getBody().getUserData() instanceof Bird) && (b.getBody().getUserData() instanceof Bird))
                    || ((a.getBody().getUserData() instanceof block_struct)
                    && (b.getBody().getUserData() instanceof block_struct))
                    || (a.getBody().getUserData() instanceof Pig
                    && b.getBody().getUserData() instanceof block_struct)
                    || (b.getBody().getUserData() instanceof Pig
                    && a.getBody().getUserData() instanceof block_struct)) {
                    if (((a.getBody().getUserData() instanceof Pig) && (b.getBody().getUserData() instanceof Pig))) {
                        Pig o = (Pig) ((a.getBody().getUserData() instanceof Pig) ? a : b).getBody().getUserData();
                        o.pighealth -= 30;
                    } else if (((a.getBody().getUserData() instanceof Bird) && (b.getBody().getUserData() instanceof Bird))) {
                        Bird o = (Bird) ((a.getBody().getUserData() instanceof Bird) ? a : b).getBody().getUserData();
                        o.birdhealth -= 30;
                    }
                    System.out.println("same body collision.");
                } else {
                    if ((a.getBody().getUserData() instanceof Pig) || (b.getBody().getUserData() instanceof Pig)) {
                        Pig o = (Pig) ((a.getBody().getUserData() instanceof Pig) ? a : b).getBody().getUserData();
                        o.setdestroy(bodiesToDestroy);
                        // o.setPosition(100000000000f, 100000000000f);
                        // world.destroyBody(o.getBody());
                    } else if ((a.getBody().getUserData() instanceof Bird)
                        || (b.getBody().getUserData() instanceof Bird)) {
                        Bird o = (Bird) ((a.getBody().getUserData() instanceof Bird) ? a : b).getBody().getUserData();
                        o.setdestroy(bodiesToDestroy, birds, current_bird_index);
                        // o.getTexture().dispose();
                        // o.setPosition(100000000000f, 100000000000f);
                        // world.destroyBody(o.getBody());

                    } else {
                        block_struct o = (block_struct) ((a.getBody().getUserData() instanceof block_struct) ? a : b)
                            .getBody().getUserData();
                        o.setdestroy();
                    }
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

                // save the 4 array list here as well.

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

                // save the 4 array list here as well.

                ori_game_variable.setScreen(ori_game_variable.lose_screen);
            }
        });

        table.add(win_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
        table.add(lose_button).size(150f, 50f).top().right().padTop(10f).padRight(10f);
    }

    private void change_catapult_bird() {

        if (birds.size() == 0) {
            ori_game_variable.lose_screen.dispose();
            ori_game_variable.lose_screen = new lose_screen(ori_game_variable);

            // save the 4 array list here as well.

            ori_game_variable.setScreen(ori_game_variable.lose_screen);
        } else {
//             current_bird.setdestroy(this.bodiesToDestroy, birds, current_bird_index);
            current_bird_index = (current_bird_index + 1) % (birds.size());
            current_bird = birds.get(current_bird_index);
            current_bird.getBody().setAwake(false);
            isLaunched = false;
            while (current_bird.getBody().isAwake()) {
                // Wait for the body to be set to sleep
            }
            current_bird.getBody().setAwake(false);
            current_bird.setPosition(new Vector2(13000, 22000));
        } // Reset to initial position
    }

    private Vector2 calculateImpulse(Vector2 start, Vector2 end) {
        Vector2 direction = start.cpy().sub(end); // Vector from release point to start
        float distance = direction.len(); // Calculate the magnitude
        direction.nor(); // Normalize to get the direction
        float forceMultiplier = 1000f; // Adjust this factor to control force
        return direction.scl(distance * forceMultiplier); // Scale by distance
    }

    private void draw_projectile(Vector2 velocity) {
        float delta = 0.1f;
        velocity = velocity.scl(0.0001f);
        for (int i = 0; i < 100; i++) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Filled);

            float x = velocity.x * i * delta;
            float y = (velocity.y * i *delta+ (0.5f) * gravity * i * i*delta*delta*0.1f);
            shape_renderer.setColor(Color.RED);

            shape_renderer.circle(x+200, y+300, 5f);
            shape_renderer.end();

        }
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
                    current_bird.getBody().setAwake(true);
                    current_bird.getBody().setLinearVelocity(velocityX, velocityY);
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (!isLaunched) {
                    is_dragging = true;
                    drastart = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (is_dragging && !isLaunched) {
                    draend = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    Vector2 velocity = calculateImpulse(drastart, draend);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (is_dragging && !isLaunched) {
                    is_dragging = false;
                    draend = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    Vector2 impulse = calculateImpulse(drastart, draend);
                    current_bird.getBody().setAwake(true);
                    current_bird.getBody().applyLinearImpulse(impulse, current_bird.getBody().getWorldCenter(), true);
                    isLaunched = true;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        if (pigs_destroyed()) {
            ori_game_variable.win_screen.dispose();
            ori_game_variable.win_screen = new win_screen(ori_game_variable);
            ori_game_variable.setScreen(ori_game_variable.win_screen);
            current_user.incrementLevel();
        }

        if (birds.size() == 0 && !pigs_destroyed()) {
            ori_game_variable.lose_screen.dispose();
            ori_game_variable.lose_screen = new lose_screen(ori_game_variable);
            ori_game_variable.setScreen(ori_game_variable.lose_screen);
        }

        if (!current_bird.destroy()) {
            if (current_bird != null) {
                Vector2 birdPosition = current_bird.getBody().getPosition();
                float birdWidth = current_bird.getTexture().getWidth() * 0.25f;
                float birdHeight = current_bird.getTexture().getHeight() * 0.25f;
                stage.getBatch().draw(current_bird.getTexture(), birdPosition.x, birdPosition.y, birdWidth, birdHeight);
            }
        }

        for (int structure_count = 0; structure_count < num_structures; structure_count++) {
            int i = 0;
            for (block_struct block : block_list.get(structure_count)) {
                Vector2 block_position = block_position_list.get(structure_count).get(i);
                stage.getBatch().draw(block.getTexture(), block.getBody().getPosition().x, block.getBody().getPosition().y,
                    block.getTexture().getWidth() * 0.5f, block.getTexture().getHeight() * 0.5f);
                i++;
            }
            Vector2 pig_position = pig_positions.get(structure_count);
            Pig pig = pig_list.get(structure_count);
            if (!pig.destroy()) {
                stage.getBatch().draw(pig.getTexture(), pig.getBody().getPosition().x, pig.getBody().getPosition().y,
                    pig.getTexture().getWidth() * 0.15f, pig.getTexture().getHeight() * 0.15f);
            }
        }

        stage.getBatch().end();

        if (is_dragging && drastart != null && draend != null) {
            shape_renderer.begin(ShapeRenderer.ShapeType.Line);
            shape_renderer.setColor(Color.RED);
            shape_renderer.line(drastart.x, drastart.y, draend.x, draend.y);
            shape_renderer.end();
            draw_projectile(calculateImpulse(drastart, draend));
        }

        while (!bodiesToDestroy.isEmpty()) {
            Body body = bodiesToDestroy.poll();
            if (body != null) {
                world.destroyBody(body);
            }
        }

        stage.act(delta);
        stage.draw();
        world.step(1 / 120f, 10, 4);
        deburenderer.render(world, viewport.getCamera().combined);
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
        saveGameState();
    }
}


