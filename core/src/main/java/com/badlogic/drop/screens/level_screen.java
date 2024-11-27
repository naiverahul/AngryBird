package com.badlogic.drop.screens;

import com.badlogic.drop.MyGame;
import com.badlogic.drop.user.User;
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

import javax.swing.JOptionPane;

public class level_screen implements Screen {
    private MyGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private ImageButton l_back_button, l_1_button, l_2_button, l_3_button, l_4_button, l_settings_button;

    private User current_user;

    public level_screen(MyGame game) {
        this.game = game;
        this.current_user = game.current_user;
        this.stage = new Stage(new FitViewport(1920, 1080));

        // Load background
        backgroundTexture = new Texture(Gdx.files.internal("First_Screen_bkg.png"));

        // Create UI components
        createUI();
    }

    private void createUI() {
        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        float buttonWidth = 200f;
        float buttonHeight = 200f;

        // Initialize buttons
        l_back_button = createButton("back.png");
        l_1_button = createButton("level_number/1.png");
        l_2_button = createButton("level_number/2.png");
        l_3_button = createButton("level_number/3.png");
        l_4_button = createButton("level_number/4.png");
        l_settings_button = createButton("setting.png");

        // Add click listeners
        l_back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(game.loadingScreen);
                dispose();
            }
        });

        l_settings_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(game.settingsScreen);
            }
        });

        // Configure level buttons based on user level
        configureLevelButtons();

        // Add buttons to the table
        table.top().left();
        table.add(l_back_button).size(buttonWidth, buttonHeight).left().pad(10);
        table.row();
        table.add(l_1_button).size(buttonWidth, buttonHeight).expandX().pad(20);
        table.add(l_2_button).size(buttonWidth, buttonHeight).expandX().pad(20);
        table.row();
        table.add(l_3_button).size(buttonWidth, buttonHeight).expandX().pad(20);
        table.add(l_4_button).size(buttonWidth, buttonHeight).expandX().pad(20);
        table.row();
        table.add(l_settings_button).size(100f, 100f).expand().bottom().left().pad(10);
    }

    private void configureLevelButtons() {
        int level = current_user.getLevel(); // Get the user's unlocked level
        ImageButton[] levelButtons = {l_1_button, l_2_button, l_3_button, l_4_button};

        for (int i = 0; i < levelButtons.length; i++) {
            if (i < level) {
                enableLevelButton(levelButtons[i], i + 1);
            } else {
                disableLevelButton(levelButtons[i]);
            }
        }
    }

    private void enableLevelButton(ImageButton button, int level) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                game.setScreen(new game_screen(game));
            }
        });
    }

    private void disableLevelButton(ImageButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();
                JOptionPane.showMessageDialog(null, "You need to complete the previous levels to unlock this level!");
            }
        });
    }

    private ImageButton createButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);

        // Draw background
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
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
        backgroundTexture.dispose();
    }
}
