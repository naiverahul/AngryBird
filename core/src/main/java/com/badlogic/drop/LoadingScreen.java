package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen implements Screen {
    private Stage stage;
    private AssetManager assetManager;
    private SpriteBatch batch;
    private Texture loadingTexture;
    private MyGame game;  // Reference to MyGame to switch screens

    private ImageButton startButton;
    private ImageButton exitButton;
    private FitViewport viewport;
    private OrthographicCamera camera;

    private TextField nameField;
    private Label nameLabel;

    private static final float VIRTUAL_HEIGHT = 600;
    private boolean nameDialogVisible = false;


    public LoadingScreen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        batch = new SpriteBatch();

        // Load the textures
        loadingTexture = new Texture(Gdx.files.internal("loading.png"));
        Texture startButtonTexture = new Texture(Gdx.files.internal("start.png"));
        Texture exitButtonTexture = new Texture(Gdx.files.internal("exit.png"));

        // Create buttons
        startButton = new ImageButton(new TextureRegionDrawable(startButtonTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitButtonTexture));

        // Create the stage and set up the UI skin
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create label and text field for name input
        nameLabel = new Label("Enter Your Name:", skin);
        nameField = new TextField("", skin);
        nameLabel.setVisible(false);
        nameField.setVisible(false);

        // Add UI elements to the stage
        stage.addActor(nameLabel);
        stage.addActor(nameField);
        stage.addActor(startButton);
        stage.addActor(exitButton);

        // Set up camera and viewport
        camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float virtualWidth = VIRTUAL_HEIGHT * aspectRatio;
        viewport = new FitViewport(virtualWidth, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // Set positions and sizes for UI elements
        float buttonWidth = 250f;
        float buttonHeight = 140f;

        startButton.setSize(buttonWidth, buttonHeight);
        startButton.setPosition(835f, viewport.getWorldHeight() / 18f);

        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(0, 0);

        nameLabel.setPosition(760f, 600f);  // Adjusted position for label
        nameField.setPosition(760f, 400f);// Adjusted position for text field
        nameLabel.setFontScale(3.0f);
        nameField.setSize(400, 80);
        nameField.setCursorPosition((int)nameField.getWidth()/2);
        nameField.setColor(Color.NAVY);

        // Add input listeners for buttons
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!nameDialogVisible) {
                    // First click: Show the name input dialog
                    nameLabel.setVisible(true);
                    nameField.setVisible(true);
                    loadingTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                    nameDialogVisible = true;
                } else {
                    // Second click: Switch to the next screen
                    game.switch_screen(game.level_screen);
                }
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                Gdx.app.exit();  // Exit the game when Exit is clicked
            }
        });

        // Set input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.app.log("LoadingScreen", "Loading screen shown");
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a custom color
        ScreenUtils.clear(1, 1f, 1.5f, 1);

        // Render the loading background image
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float imageWidth = 1440f;
        float imageHeight = 765f;
        float x = (viewport.getWorldWidth() - imageWidth) / 2f;
        float y = (viewport.getWorldHeight() - imageHeight) / 2f;
        batch.draw(loadingTexture, x, y, imageWidth, imageHeight);
        batch.end();

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        // Dispose of all resources
        batch.dispose();
        loadingTexture.dispose();
        stage.dispose();

        // Dispose of button textures
        ((TextureRegionDrawable) startButton.getStyle().imageUp).getRegion().getTexture().dispose();
        ((TextureRegionDrawable) exitButton.getStyle().imageUp).getRegion().getTexture().dispose();
    }

}
