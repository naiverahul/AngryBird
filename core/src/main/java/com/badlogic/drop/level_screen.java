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

    public level_screen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;

        // Initialize stage and viewport
        stage = new Stage(new FitViewport(800, 600));

        // Load background from AssetManager (reused from FirstScreen)
        backgroundTexture = new Texture(Gdx.files.internal("First_Screen_bkg.png"));
        // Create UI
        createUI();
    }

    private void createUI() {
        // Create Table for layout management
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Define the size for buttons
        float buttonWidth = 100f;
        float buttonHeight = 100f;

        // Create buttons
        l_back_button = createButton("back.png");
        l_1_button = createButton("level_number/1.png");
        l_2_button = createButton("level_number/2.png");
        l_3_button = createButton("level_number/3.png");
        l_4_button = createButton("level_number/4.png");

        // Set button sizes
        l_back_button.setSize(buttonWidth, buttonHeight);
        l_1_button.setSize(buttonWidth, buttonHeight);
        l_2_button.setSize(buttonWidth, buttonHeight);
        l_3_button.setSize(buttonWidth, buttonHeight);
        l_4_button.setSize(buttonWidth, buttonHeight);

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
                game.setScreen(game.loadingScreen); // Load level 1
            }
        });
        l_2_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.loadingScreen); // Load level 2
            }
        });
        l_3_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.loadingScreen); // Load level 3
            }
        });
        l_4_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.loadingScreen); // Load level 4
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
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth() + backgroundTexture.getWidth() , Gdx.graphics.getHeight()+backgroundTexture.getHeight());
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
    }
}
