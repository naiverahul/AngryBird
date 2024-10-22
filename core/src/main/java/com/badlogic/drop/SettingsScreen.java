package com.badlogic.drop;

import Birds.Birdparentclass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class SettingsScreen implements Screen {
    private Stage stage;
    private BitmapFont font;
    private Skin skin;
    private Table table;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture s_background;

    private ArrayList<Birdparentclass> birdList;  // List of available birds
    private Birdparentclass selectedBird;         // Track the selected bird

    private Label selectedBirdLabel;

    public SettingsScreen() {
        viewport = new FitViewport(1920, 1080);  // Set up a 1920x1080 viewport
        stage = new Stage(viewport);
        s_background = new Texture(Gdx.files.internal("setting_background.png"));  // Load background texture

//        skin = new Skin(Gdx.files.internal("myskin.json"));
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        font.setColor(Color.WHITE);

        // Initialize bird list and default bird selection
        initializeBirdList();
        selectedBird = birdList.get(0);  // Default bird selection

        createUI();  // Build the UI
    }

    private void initializeBirdList() {
        birdList = new ArrayList<>();
        birdList.add(new Birdparentclass("Red Bird", "Birdimages/redbird.png", 1, 100));
        birdList.add(new Birdparentclass("Black Bird", "Birdimages/blackbird.png", 1, 150));
        birdList.add(new Birdparentclass("Yellow Bird", "Birdimages/yellowbird.png", 1, 120));
        birdList.add(new Birdparentclass("Big Bird", "Birdimages/bigbird.png", 1, 200));
    }

    private void createUI() {
        table = new Table();
        table.debug(Table.Debug.all);
        table.setFillParent(true);  // Make the table fill the screen
        table.top().padTop(300);    // Add padding to position it lower from the top

        createSettingsButtons();  // Add settings buttons

        stage.addActor(table);  // Add the table to the stage
    }

    private void createSettingsButtons() {
        // Load button texture with rounded corners
        Texture buttonTexture = new Texture(Gdx.files.internal("button.png"));
        NinePatch buttonPatch = new NinePatch(buttonTexture, 10, 10, 10, 10);
        NinePatchDrawable buttonDrawable = new NinePatchDrawable(buttonPatch);

        // Create a TextButton style programmatically
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonDrawable;
        buttonStyle.down = buttonDrawable;
        buttonStyle.font = new BitmapFont();

        // Create buttons with the new style
        TextButton soundButton = new TextButton("Sound: ON", buttonStyle);
        TextButton graphicsButton = new TextButton("Graphics: HIGH", buttonStyle);
        TextButton controlsButton = new TextButton("Controls: SIMPLE", buttonStyle);
        TextButton backButton = new TextButton("Back", buttonStyle);

        // Add buttons to the table
        table.add(soundButton).pad(15).width(250).height(200);
        table.row();
        table.add(graphicsButton).pad(15).width(300).height(200);
        table.row();
        table.add(controlsButton).pad(15).width(300).height(200);
        table.row();
        table.add(backButton).padTop(15).width(200).height(200);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);  // Set the input processor to the stage
    }

    @Override
    public void render(float delta) {
        // Clear the screen and draw the background
        batch.begin();
        batch.draw(s_background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        stage.act(delta);  // Update the stage
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);  // Adjust the viewport on resize
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();  // Dispose of stage resources
        font.dispose();
        batch.dispose();
    }
}
