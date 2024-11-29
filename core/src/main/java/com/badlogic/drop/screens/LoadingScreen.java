package com.badlogic.drop.screens;

import com.badlogic.drop.MyGame;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import javax.swing.JOptionPane;

public class LoadingScreen implements Screen {

    private static final float VIRTUAL_HEIGHT = 600;

    private final MyGame game;
    private final AssetManager assetManager;
    private Stage stage;
    private SpriteBatch batch;
    private ProgressBar progressBar;
    private TextButton startButton, exitButton, loadButton;
    private Label nameLabel;
    private Texture loadingTexture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private boolean loadingCompleted = false;
    private boolean buttonsVisible = false;

    public LoadingScreen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        initialize();
    }

    private void initialize() {
        setupCameraAndViewport();
        setupStageAndBatch();
        setupSkinAndUI();
        setupProgressBar();
        setupInputListeners();
        Gdx.input.setInputProcessor(stage);
    }

    private void setupCameraAndViewport() {
        camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float virtualWidth = VIRTUAL_HEIGHT * aspectRatio;
        viewport = new FitViewport(virtualWidth, VIRTUAL_HEIGHT, camera);
        viewport.apply();
    }

    private void setupStageAndBatch() {
        stage = new Stage();
        batch = new SpriteBatch();
    }

    private void setupSkinAndUI() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        loadingTexture = new Texture(Gdx.files.internal("loading.png"));

        startButton = createTextButton("Start", skin, 835f, viewport.getWorldHeight() / 18f);
        exitButton = createTextButton("Exit", skin, 0, 0);
        loadButton = createTextButton("Load", skin, viewport.getWorldWidth() - 250f, viewport.getWorldHeight() - 140f);
        loadButton.setColor(Color.NAVY);

        nameLabel = new Label("Enter Your Name:", skin);
        nameLabel.setVisible(false);

        stage.addActor(startButton);
        stage.addActor(exitButton);
        stage.addActor(loadButton);
        stage.addActor(nameLabel);

        toggleButtons(false);
    }

    private TextButton createTextButton(String text, Skin skin, float x, float y) {
        TextButton button = new TextButton(text, skin);
        button.setSize(250f, 140f);
        button.setPosition(x, y);
        return button;
    }

    private void setupProgressBar() {
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle(
            skin.newDrawable("white", Color.DARK_GRAY),
            skin.newDrawable("white", Color.BLUE)
        );
        style.knobBefore = style.knob;

        progressBar = new ProgressBar(0, 1, 0.01f, false, style);
        progressBar.setSize(800f, 100f);
        progressBar.setPosition(
            (viewport.getWorldWidth() - progressBar.getWidth()) / 2f + 400f,
            150f
        );
        progressBar.setAnimateDuration(1.3f);
        stage.addActor(progressBar);
        pixmap.dispose();
    }

    private void setupInputListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.switch_screen(game.level_screen);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                Gdx.app.exit();
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    game_screen loadedGameScreen = game.game_screen.loadGameState(game);
                    game.setScreen(loadedGameScreen);
                    game.setGameScreen(loadedGameScreen);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Game not saved : "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                loadButton.setColor(Color.LIGHT_GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                loadButton.setColor(Color.NAVY);
            }
        });

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    game.switch_screen(game.level_screen);
                    return true;
                } else if (keycode == Input.Keys.ESCAPE) {
                    game.clicksound.play();
                    Gdx.app.exit();
                    return true;
                }
                return false;
            }
        });
    }

    private void toggleButtons(boolean visible) {
        startButton.setVisible(visible);
        exitButton.setVisible(visible);
        loadButton.setVisible(visible);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1f, 1.5f, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        float imageWidth = 1440f;
        float imageHeight = 765f;
        float x = (viewport.getWorldWidth() - imageWidth) / 2f;
        float y = (viewport.getWorldHeight() - imageHeight) / 2f;
        batch.draw(loadingTexture, x, y, imageWidth, imageHeight);
        batch.end();

        progressBar.setValue(assetManager.getProgress());

        if (!loadingCompleted && assetManager.getProgress() >= 1.0f) {
            loadingCompleted = true;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    progressBar.setVisible(false);
                    toggleButtons(true);
                }
            }, 2f);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

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
        stage.dispose();
    }
}
