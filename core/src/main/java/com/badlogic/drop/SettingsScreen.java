package com.badlogic.drop;

import Birds.Birdparentclass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private MyGame game;
    private ArrayList<Birdparentclass> birdList;  // List of available birds
    private Birdparentclass selectedBird;         // Track the selected bird

    private Label selectedBirdLabel;

    public SettingsScreen(MyGame game) {
        this.game = game;
        viewport = new FitViewport(1920, 1080);  // Set up a 1920x1080 viewport
        stage = new Stage(viewport);
        s_background = new Texture(Gdx.files.internal("setting_background.png"));  // Load background texture

//        skin = new Skin(Gdx.files.internal("myskin.json"));
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("default.fnt"));
        font.setColor(Color.WHITE);
        font.getData().setScale(3.0f);



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
        NinePatch buttonPatch = new NinePatch(buttonTexture, 5, 5, 5, 5);
        NinePatchDrawable buttonDrawable = new NinePatchDrawable(buttonPatch);

        // Create a TextButton style programmatically
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonDrawable;
        buttonStyle.down = buttonDrawable;

        // Increase the font size for the button text
        BitmapFont buttonFont = new BitmapFont(Gdx.files.internal("default.fnt"));
        buttonFont.getData().setScale(1.5f);  // Increase the font size by 3
        buttonStyle.font = buttonFont;

        // Create buttons with the new style
        // final TextButton soundButton = new TextButton("Sound: ON", buttonStyle);
        TextButton soundButton = new TextButton("Sound: ON", buttonStyle);
        // final TextButton graphicsButton = new TextButton("Graphics: HIGH", buttonStyle);
        TextButton graphicsButton = new TextButton("Graphics: HIGH", buttonStyle);
        // final TextButton controlsButton = new TextButton("Controls: SIMPLE", buttonStyle);
        TextButton controlsButton = new TextButton("Controls: SIMPLE", buttonStyle);
        // final TextButton backButton = new TextButton("Back", buttonStyle);
        TextButton backButton = new TextButton("Back", buttonStyle);

        // Add listeners to each button

        // Listener for soundButton (toggle between ON/OFF)
        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();


                if (soundButton.getText().toString().equals("Sound: ON")) {
                    soundButton.setText("Sound: OFF");
                    game.bkgmusic.pause();
                } else {
                    soundButton.setText("Sound: ON");
                    game.bkgmusic.play();
                }
            }
        });

        // Listener for graphicsButton (toggle between HIGH/MEDIUM/LOW)
        graphicsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();

                String currentText = graphicsButton.getText().toString();
                if (currentText.equals("Graphics: HIGH")) {
                    graphicsButton.setText("Graphics: MEDIUM");
                } else if (currentText.equals("Graphics: MEDIUM")) {
                    graphicsButton.setText("Graphics: LOW");
                } else {
                    graphicsButton.setText("Graphics: HIGH");
                }
            }
        });

        // Listener for controlsButton (toggle between SIMPLE/ADVANCED)
        controlsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();

                if (controlsButton.getText().toString().equals("Controls: SIMPLE")) {
                    controlsButton.setText("Controls: ADVANCED");
                } else {
                    controlsButton.setText("Controls: SIMPLE");
                }
            }
        });

        // Listener for backButton (go back to the previous screen or main menu)
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.clicksound.play();

                game.settingsScreen.dispose();
                level_screen new_level_screen = new level_screen(game);
                game.level_screen = new_level_screen;
                game.setScreen(game.level_screen);
            }
        });

        // Add buttons to the table with increased padding and size
        table.add(soundButton).padTop(5).width(250).height(150);  // Increase button size
        table.row();
        table.add(graphicsButton).padTop(5).width(250).height(150);
        table.row();
        table.add(controlsButton).padTop(5).width(250).height(150);
        table.row();
        table.add(backButton).padTop(5).width(200).height(100);
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);  // Set the input processor to the stage
    }

    @Override
    public void render(float delta) {
        // Clear the screen and draw the background
        batch.begin();

        // Draw the background
        batch.draw(s_background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Set the font size by scaling
        font.getData().setScale(4.0f);
        font.setColor(Color.MAGENTA); ;
        font.draw(batch, "Settings",850 , 1000);  // Adjust x, y position based on scaling

        batch.end();

        // Update and draw the stage
        stage.act(delta);
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
