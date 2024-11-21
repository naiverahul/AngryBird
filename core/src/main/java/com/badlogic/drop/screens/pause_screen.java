package com.badlogic.drop.screens;

import com.badlogic.drop.MyGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class pause_screen implements Screen {
    private Stage stage;
    private FitViewport viewport;
    private MyGame game;
    private ImageButton resumeButton;
    private ImageButton mainMenuButton;
    private ImageButton restartButton;
    private Texture backgroundTexture;
    private ImageButton greeting_bird;

    public pause_screen(MyGame game) {
        this.game = game;
        this.viewport = new FitViewport(1920, 1080);
        this.stage = new Stage(viewport);

        backgroundTexture = new Texture(Gdx.files.internal("pause_background.png")); // Optional: semi-transparent background
        greeting_bird = createButton("chuck.png");
        greeting_bird.setPosition(
            Gdx.graphics.getWidth() - greeting_bird.getWidth() - 20, // 20px padding from the right
            20 // 20px padding from the bottom
        );
        stage.addActor(greeting_bird);
        setupUI();
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        resumeButton = createButton("resume_button.png");
        mainMenuButton = createButton("close.png");
        restartButton = createButton("restart_button.png");

        // Resume button functionality
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(game.game_screen); // Resume the game by returning to the game screen
            }
        });

        // Main Menu button functionality
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(new level_screen(game)); // Switch to the main menu
            }
        });

        // Restart button functionality
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.game_screen.dispose(); // Dispose of the game screen
                game.game_screen = new game_screen(game); // Create a new game screen
                game.setScreen(game.game_screen); // Restart the game
            }
        });

        table.add(resumeButton).size(200f, 100f).padBottom(20f);
        table.add(mainMenuButton).size(200f, 100f).padBottom(20f);
        table.add(restartButton).size(200f, 100f).padBottom(20f);
        table.debug();

        greeting_bird.setPosition(
            Gdx.graphics.getWidth() - greeting_bird.getWidth() - 20, // 20px padding from the right
            20 // 20px padding from the bottom
        );
    }

    private ImageButton createButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new ImageButton(new TextureRegionDrawable(texture));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        // Render the game screen underneath (without updating it)
        // game.game_screen.render(delta); // Ensures the game screen is visible underneath

        stage.getBatch().begin();
        stage.getBatch().setColor(Color.CYAN); // Optional: Set transparency
        stage.getBatch().draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.getBatch().end();

        // Draw the UI on top
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        backgroundTexture.dispose();
    }
}
