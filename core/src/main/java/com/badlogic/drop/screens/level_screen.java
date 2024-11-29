package com.badlogic.drop.screens;

import com.badlogic.drop.MyGame;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class level_screen implements Screen {
    private final MyGame game;
    private final Stage stage;
    private final User current_user;
    private Skin skin;
    private Texture background ;

    public level_screen(MyGame game) {
        this.game = game;
        this.current_user = game.current_user;
        this.stage = new Stage(new FitViewport(1920, 1080));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.background = new Texture(Gdx.files.internal("loading2.png"));
        // Create UI components
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        // Create scrollable level buttons
        Table levelTable = new Table();
        configureLevelButtons(levelTable);

        ScrollPane scrollPane = new ScrollPane(levelTable);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false); // Enable vertical scrolling only

        // Create back button
        ImageButton backButton = createImageButton("back.png");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(game.loadingScreen);
                dispose();
            }
        });

        // Create settings button
        ImageButton settingsButton = createImageButton("setting.png");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(game.settingsScreen);
            }
        });

        // Add buttons to the table
        table.top().left();
        table.add(backButton).size(100f, 100f).right().pad(20);
        table.row();
        table.add(scrollPane).expand().fill().pad(20);
        table.row();
        table.add(settingsButton).size(100f, 100f).expand().bottom().left().pad(10);
    }

    private ImageButton createImageButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(drawable);

        // Add hover effect
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.getImage().setColor(Color.LIGHT_GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.getImage().setColor(Color.WHITE);
            }
        });

        return button;
    }
    private void configureLevelButtons(Table levelTable) {
        // Retrieve screen dimensions to calculate button sizes dynamically
        float buttonWidth = Gdx.graphics.getWidth() / 5f;  // 1/5th of screen width
        float buttonHeight = Gdx.graphics.getHeight() / 5f; // 1/5th of screen height

        int userLevel = current_user.getLevel(); // Get the user's current level

        for (int i = 1; i <= userLevel; i++) { // Allow 100 levels beyond the current unlocked level
            TextButton levelButton = createLevelButton("Level " + i, buttonWidth, buttonHeight);

            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.clicksound.play();
                    game.setScreen(new game_screen(game));
                }
            });

            levelTable.add(levelButton).size(buttonWidth, buttonHeight).pad(20);

            // Move to the next row after every 4 buttons
            if (i % 4 == 0) {
                levelTable.row();
            }
        }
    }

    private TextButton createLevelButton(String labelText, float buttonWidth, float buttonHeight) {
        // Create button style
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY); // Default background color
        buttonStyle.over = skin.newDrawable("white", Color.DARK_GRAY); // Hover background color
        buttonStyle.down =skin.newDrawable("white", new Color(1, 1, 1, 0)); // Clicked background color
        buttonStyle.font = skin.getFont("default-font");
        buttonStyle.fontColor = Color.NAVY; // Default font color

        // Create button with the label
        TextButton levelButton = new TextButton(labelText, buttonStyle);
        levelButton.setSize(buttonWidth, buttonHeight);
        levelButton.getLabel().setAlignment(Align.center);
        levelButton.getLabel().setFontScale(2); // Scale text for visibility

        // Add hover effects for font color
        levelButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                levelButton.getLabel().setColor(Color.WHITE); // Change font color on hover
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                levelButton.getLabel().setColor(Color.NAVY); // Reset font color when not hovering
            }
        });

        return levelButton;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACKSPACE) {
                    game.setScreen(game.loadingScreen);
                    dispose();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 1920, 1080);
        stage.getBatch().end();
        // Draw UI
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
    }
}
