package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
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
    @SuppressWarnings("unused")
    private MyGame game;  // Reference to MyGame to switch screens

    private Texture startButtonTexture;
    private ImageButton startButton;

    private Texture exitButtonTexture;
    private ImageButton exitButton;

    private FitViewport viewport;
    private OrthographicCamera camera;
    private static final float VIRTUAL_HEIGHT = 600;

    private TextField nameField;
    private Label namelabel;


    public LoadingScreen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        batch = new SpriteBatch();

        loadingTexture = new Texture(Gdx.files.internal("loading.png"));

        startButtonTexture = new Texture(Gdx.files.internal("start.png"));
        startButton = new ImageButton(new TextureRegionDrawable(startButtonTexture));

        exitButtonTexture = new Texture(Gdx.files.internal("exit.png"));
        exitButton = new ImageButton(new TextureRegionDrawable(exitButtonTexture));

        // Initialize the stage
        stage = new Stage();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Name label and TextField for name input
        namelabel = new Label("Enter Your Name:", skin);
        nameField = new TextField("", skin);  // Empty text field for input
        // Add buttons to the stage
        stage.addActor(startButton);
        stage.addActor(exitButton);
// Initialize the camera and viewport
        camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float virtualWidth = VIRTUAL_HEIGHT * aspectRatio;
        viewport = new FitViewport(virtualWidth, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // Increase button size
        float buttonWidth = 250f;
        float buttonHeight = 140f;

        // Position the "Play" button at the center of the screen
        startButton.setSize(buttonWidth, buttonHeight);
        startButton.setPosition(850f, viewport.getWorldHeight() / 18f);

        // Position the "Exit" button below the "Play" button
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(0, 0);

        // Add input listeners to buttons
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to FirstScreen when the start button is clicked
                game.switch_screen(game.level_screen);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        ScreenUtils.clear(1, 1f, 1.5f, 1);  // Clear screen with a custom color

        if (assetManager.update()) {
            startButton.setVisible(true);
            exitButton.setVisible(true);
        } else {
            startButton.setVisible(false);
            exitButton.setVisible(false);
        }

        // Draw the loading texture
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float imageWidth = 1440f;
        float imageHeight = 765f;
        float x = (viewport.getWorldWidth() - imageWidth) / 2f;
        float y = (viewport.getWorldHeight() - imageHeight) / 2f;
        batch.draw(loadingTexture, x, y, imageWidth, imageHeight);
        batch.end();

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
        batch.dispose();
        loadingTexture.dispose();
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        stage.dispose();
    }
}
