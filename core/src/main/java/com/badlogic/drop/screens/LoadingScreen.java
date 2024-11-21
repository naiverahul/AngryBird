package com.badlogic.drop.screens;

import com.badlogic.drop.MyGame;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen implements Screen {

    private Stage stage;
    private SpriteBatch batch;
    private ProgressBar progressBar;
    private ImageButton startButton, exitButton;
    private TextField nameField;
    private Label nameLabel;
    private AssetManager assetManager;
    private MyGame game;
    private Texture loadingTexture;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private boolean nameDialogVisible = false;
    private boolean loadingCompleted = false;
    private boolean buttonsVisible = false;
    private Timer.Task showButtonsTask;
    private static final float VIRTUAL_HEIGHT = 600;

    public LoadingScreen(MyGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
        batch = new SpriteBatch();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        loadingTexture = new Texture(Gdx.files.internal("loading.png"));
        Texture startButtonTexture = new Texture(Gdx.files.internal("start.png"));
        Texture exitButtonTexture = new Texture(Gdx.files.internal("exit.png"));
        startButton = new ImageButton(new TextureRegionDrawable(startButtonTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitButtonTexture));
        startButton.setVisible(false);
        exitButton.setVisible(false);
        stage = new Stage();
        setupCameraAndViewport();
        nameLabel = new Label("Enter Your Name:", skin);
        nameField = new TextField("", skin);
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        setupProgressBar(skin);
        setupUIComponents();
        addInputListeners();
        Gdx.input.setInputProcessor(stage);
    }

    private void setupCameraAndViewport() {
        camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float virtualWidth = VIRTUAL_HEIGHT * aspectRatio;
        viewport = new FitViewport(virtualWidth, VIRTUAL_HEIGHT, camera);
        viewport.apply();
    }

    private void setupProgressBar(Skin skin) {
        skin = new Skin();
        Pixmap pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle(
            skin.newDrawable("white", Color.DARK_GRAY),
            skin.newDrawable("white", Color.BLUE)
        );
        progressBarStyle.knobBefore = progressBarStyle.knob;
        progressBar = new ProgressBar(0, 1, 0.01f, false, progressBarStyle);
        progressBar.setSize(800f, 100f);
        progressBar.setPosition(
            (viewport.getWorldWidth() - progressBar.getWidth()) / 2f + 400f,
            150f
        );
        progressBar.setValue(0f);
        progressBar.setAnimateDuration(1.3f);
        stage.addActor(progressBar);
        pixmap.dispose();
    }

    private void setupUIComponents() {
        startButton.setSize(250f, 140f);
        startButton.setPosition(835f, viewport.getWorldHeight() / 18f);
        exitButton.setSize(250f, 140f);
        exitButton.setPosition(0, 0);
        nameLabel.setPosition(760f, 600f);
        nameLabel.setFontScale(3.0f);
        nameField.setPosition(760f, 400f);
        nameField.setSize(400f, 80f);
        nameField.setCursorPosition((int) nameField.getWidth() / 2);
        nameField.setColor(Color.NAVY);
        stage.addActor(nameLabel);
        stage.addActor(nameField);
        stage.addActor(startButton);
        stage.addActor(exitButton);
    }

    private void addInputListeners() {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!nameDialogVisible) {
                    nameLabel.setVisible(true);
                    nameField.setVisible(true);
                    nameDialogVisible = true;
                } else {
                    game.switch_screen(game.level_screen);
                }
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                Gdx.app.exit();
            }
        });
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    if (!nameDialogVisible) {
                        nameLabel.setVisible(true);
                        nameField.setVisible(true);
                        nameDialogVisible = true;
                    } else {
                        game.switch_screen(game.level_screen
                        );
                    }
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

    @Override
    public void show() {
        Gdx.app.log("LoadingScreen", "Loading screen shown");


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
        float progress = assetManager.getProgress();
        progressBar.setValue(progress);

        if (progress >= 1.0f && !loadingCompleted) {
            loadingCompleted = true;
            showButtonsTask = new Timer.Task() {
                @Override
                public void run() {
                    progressBar.setVisible(false);
                    startButton.setVisible(true);
                    exitButton.setVisible(true);
                    buttonsVisible = true;
                }
            };
            Timer.schedule(showButtonsTask, 1.0f);
        }

        if (!buttonsVisible) {
            startButton.setVisible(false);
            exitButton.setVisible(false);
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
        ((TextureRegionDrawable) startButton.getStyle().imageUp).getRegion().getTexture().dispose();
        ((TextureRegionDrawable) exitButton.getStyle().imageUp).getRegion().getTexture().dispose();
        if (showButtonsTask != null) {
            showButtonsTask.cancel();
        }
    }
}
