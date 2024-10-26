package com.badlogic.drop;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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

import Birds.Birdparentclass;

public class game_screen implements Screen {
    private final MyGame g_original_game_variable;
    private Stage g_stage;
    private FitViewport g_viewport;
    private Texture g_background;
    private ArrayList<Birdparentclass> g_bird_list;
    private Birdparentclass g_bird_on_catapult;
    private ImageButton g_catapult, g_pause_button, g_back_button;
    private int current_bird_index = 0;

    private TextButton g_win_button;
    private TextButton g_lose_button;
    private Skin g_skin;

    private LevelGenerator g_level_generator;

    public game_screen(MyGame game) {
        this.g_original_game_variable = game;
        this.g_viewport = new FitViewport(1920, 1080);
        this.g_stage = new Stage(g_viewport);
        this.g_background = new Texture("game_screen.png");
        this.g_bird_list = new ArrayList<>();

        this.g_level_generator = new LevelGenerator(g_stage);
        this.g_level_generator.generateLevel();

        g_initialize_birds();
        g_create_UI();

        // Start with the first bird in the queue
        this.g_bird_on_catapult = g_bird_list.get(current_bird_index);
    }

    private void g_initialize_birds() {
        g_bird_list.add(new Birdparentclass("bigbird", "Birdimages/bigbird.png", 10, 5));
        g_bird_list.add(new Birdparentclass("redbird", "Birdimages/redbird.png", 8, 6));
        g_bird_list.add(new Birdparentclass("yellowbird", "Birdimages/yellowbird.png", 12, 7));
        g_bird_list.add(new Birdparentclass("blackbird", "Birdimages/blackbird.png", 15, 8));
    }

    private void g_create_UI() {
        Table table = new Table();
        table.debug(Table.Debug.all);
        table.setFillParent(true);
        g_stage.addActor(table);

        this.g_catapult = g_create_button("catapult.png");
        this.g_pause_button = g_create_button("pause.png");
        this.g_back_button = g_create_button("back.png");

        g_catapult.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_change_catapult_bird();
            }
        });
        g_pause_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_original_game_variable.pause_screen.dispose();
                g_original_game_variable.pause_screen = new pause_screen(g_original_game_variable);
                g_original_game_variable.setScreen(g_original_game_variable.pause_screen);
            }
        });
        g_back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_original_game_variable.level_screen.dispose();
                g_original_game_variable.level_screen = new level_screen(g_original_game_variable);
                g_original_game_variable.setScreen(g_original_game_variable.level_screen);
            }
        });

        table.add(g_back_button).size(150f, 150f).expand().top().left().pad(20f);
        add_win_lose_screens(table);
        table.add(g_pause_button).size(100f, 100f).top().right().pad(20f);
        table.row();
        table.add(g_catapult).size(200f, 200f).expand().bottom().left().pad(100f);

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
                // g_original_game_variable.win_screen = new WinScreen(g_original_game_variable);
                g_original_game_variable.win_screen = new win_screen(g_original_game_variable);
                g_original_game_variable.setScreen(g_original_game_variable.win_screen);
            }
        });
        g_lose_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                g_original_game_variable.clicksound.play();
                g_original_game_variable.lose_screen.dispose();
                g_original_game_variable.lose_screen = new lose_screen(g_original_game_variable);
                // g_original_game_variable.lose_screen = new LoseScreen(g_original_game_variable);
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
    }

    private void g_add_birds_to_table() {
        for (Birdparentclass bird : g_bird_list) {
            if (!(bird.equals(g_bird_on_catapult))) {
                // ImageButton g_bird_button = g_create_button();
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(g_stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);

        // Begin drawing
        g_stage.getBatch().begin();
        g_stage.getBatch().draw(g_background, 0, 0, g_viewport.getWorldWidth(), g_viewport.getWorldHeight());

        // Draw the current bird scaled down on the catapult
        if (g_bird_on_catapult != null) {
            float birdWidth = g_bird_on_catapult.getTexture().getWidth() * 0.25f;
            float birdHeight = g_bird_on_catapult.getTexture().getHeight() * 0.25f;
            g_stage.getBatch().draw(g_bird_on_catapult.getTexture(), g_catapult.getX() + 50f, g_catapult.getY() + 150f,
                    birdWidth, birdHeight);

        }

        g_stage.getBatch().end();

        // Stage needs to act and draw UI elements
        g_stage.act(delta);
        g_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        g_viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        g_stage.dispose();
        g_background.dispose();
        for (Birdparentclass bird : g_bird_list) {
            bird.dispose();
        }

    }
}