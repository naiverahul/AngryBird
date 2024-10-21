package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class level_screen implements Screen {
    private MyGame game;
    private Stage stage;
    private AssetManager assetManager;
    private Texture backgroundTexture;

    private ImageButton l_back_button;
    private ImageButton l_1_button;
    private ImageButton l_2_button;
    private ImageButton l_3_button;
    private ImageButton l_4_button;
    private ImageButton settings_button; // New settings button

    private game_screen l_game_screen;

    public level_screen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

        // Initialize stage and viewport
        stage = new Stage(new FitViewport(1920, 1080));

        // Load background from AssetManager (reused from FirstScreen)
        backgroundTexture = new Texture(Gdx.files.internal("First_Screen_bkg.png"));

        // Create UI
        l_game_screen = new game_screen(game);
        createUI();
    }

    private void createUI() {
        // Create Table for layout management
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Define the size for buttons
        float buttonWidth = 200f;
        float buttonHeight = 200f;

        // Create buttons
        l_back_button = createButton("back.png");
        l_1_button = createButton("level_number/1.png");
        l_2_button = createButton("level_number/2.png");
        l_3_button = createButton("level_number/3.png");
        l_4_button = createButton("level_number/4.png");
        settings_button = createButton("setting.png");  // Settings button

        // Set button sizes
        l_back_button.setSize(10f, 10f);
        l_1_button.setSize(buttonWidth, buttonHeight);
        l_2_button.setSize(buttonWidth, buttonHeight);
        l_3_button.setSize(buttonWidth, buttonHeight);
        l_4_button.setSize(buttonWidth, buttonHeight);
        settings_button.setSize(80f, 80f);  // Slightly smaller size for settings button

        // Add click listeners for buttons
        l_back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 game.setScreen(game.loadingScreen); // Transition to Main Menu or Loading Screen
            }
        });
        l_1_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(game.loadingScreen); // Load level 1
                // game.setScreen(l_game_screen);
                game.switch_screen(l_game_screen);
            }
        });
        l_2_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(game.loadingScreen); // Load level 2
                // game.setScreen(l_game_screen);
                game.switch_screen(l_game_screen);
            }
        });
        l_3_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(game.loadingScreen); // Load level 3
                // game.setScreen(l_game_screen);
                game.switch_screen(l_game_screen);
            }
        });
        l_4_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(game.loadingScreen); // Load level 4
                // game.setScreen(l_game_screen);
                game.switch_screen(l_game_screen);
            }
        });
        settings_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Implement settings screen transition
                game.setScreen(game.settingsScreen);
            }
        });

        // Set the table layout for 2x2 button grid and back button at the top-left
        table.top().left(); // Aligns table at top-left corner
        table.add(l_back_button).size(buttonWidth, buttonHeight).left().pad(10); // Back button at the top-left
        table.row(); // Move to the next row

        // Create a 2x2 matrix for the level buttons
        table.add(l_1_button).size(buttonWidth, buttonHeight).expandX().pad(20); // Level 1 button
        table.add(l_2_button).size(buttonWidth, buttonHeight).expandX().pad(20); // Level 2 button
        table.row(); // Move to the next row
        table.add(l_3_button).size(buttonWidth, buttonHeight).expandX().pad(20); // Level 3 button
        table.add(l_4_button).size(buttonWidth, buttonHeight).expandX().pad(20); // Level 4 button

        // Add settings button to bottom-left, outside of the main table layout
        table.row();
        table.add(settings_button).size(200f, 200f).expand().bottom().left().pad(10);  // Settings button
    }

    // Helper method to create buttons from textures
    private ImageButton createButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);  // Set input to stage
    }

    @Override
    public void render(float delta) {
        // Clear screen and draw background
        ScreenUtils.clear(Color.CYAN);

        // Begin rendering
        stage.getBatch().begin();
        // Draw background texture in full screen
        stage.getBatch().draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.getBatch().end();

        // Draw stage and handle input
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        assetManager.unload("back.png");
        assetManager.unload("level_number/1.png");
        assetManager.unload("level_number/2.png");
        assetManager.unload("level_number/3.png");
        assetManager.unload("level_number/4.png");
        assetManager.unload("settings.png");
    }
}
