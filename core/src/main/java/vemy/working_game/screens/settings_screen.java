package vemy.working_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
// import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import vemy.working_game.my_game;
import vemy.working_game.utils.gvcs;

public class settings_screen extends ScreenAdapter {
    /*
     * This class will be the screen where the player can change the game settings.
     * It will have options to change sound settings, graphics settings, and other
     * game settings.
     * It will have a back button to go back to the level select screen, a save
     * button to save the changes, and a reset button to reset the settings to
     * default.
     * In case of reset, a confirmation dialog will be shown to the player. If the
     * player confirms, the settings will be loaded from the default player
     * settings. If the player cancels, the settings will remain as they are. If the
     * player saves the settings, the changes will be saved to the player profile.
     * If the player goes back without saving, the changes will be discarded.
     * If the player has not logged in, the changes will be saved to the default
     * player settings, but they will temperary and will be lost when the game is
     * closed.
     */

    private my_game game;
    private Stage settings_stage;
    private BitmapFont settings_font;
    // private Skin settings_skin;
    private Table settings_table;
    private SpriteBatch settings_batch;
    private FitViewport settings_viewport;
    private Texture settings_background;

    public settings_screen(my_game game) {
        this.game = game;
        settings_viewport = new FitViewport(gvcs.World_Width, gvcs.World_Height);
        settings_stage = new Stage(settings_viewport);
        settings_background = game.get_asset_manager().get(gvcs.setting_background_path, Texture.class);
        settings_batch = new SpriteBatch();
        settings_font = game.get_asset_manager().get(gvcs.default_font_path, BitmapFont.class);

        // Configure the font
        settings_font.setColor(Color.WHITE);
        settings_font.getData().setScale(gvcs.font_scale);

        // Create UI
        create_ui();
    }

    private void create_ui() {
        settings_table = new Table();
        settings_table.debug(Table.Debug.all);
        settings_table.setFillParent(true);
        settings_table.top().pad(300);

        create_settings_function_button();

        settings_stage.addActor(settings_table);
    }

    private void create_settings_function_button() {
        // Create the button style for settings function buttons
        Texture button_texture = game.get_asset_manager().get(gvcs.hollow_button_path, Texture.class);
        NinePatch button_patch = new NinePatch(button_texture, 5, 5, 5, 5);
        NinePatchDrawable button_drawable = new NinePatchDrawable(button_patch);

        TextButton.TextButtonStyle button_style = new TextButton.TextButtonStyle();
        button_style.up = button_drawable;
        button_style.down = button_drawable;

        BitmapFont button_font = game.get_asset_manager().get(gvcs.default_font_path, BitmapFont.class);
        button_font.getData().setScale(gvcs.font_scale / 2);
        button_style.font = button_font;

        TextButton music_button = new TextButton("Music: ON", button_style);
        TextButton graphics_button = new TextButton("Graphics: HIGH", button_style);
        TextButton controls_button = new TextButton("Controls: SIMPLE", button_style);
        TextButton back_button = new TextButton("Back", button_style);

        music_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the sound settings
                if (music_button.getText().toString().equals("Music: ON")) {
                    music_button.setText("Music: OFF");
                    gvcs.background_music.pause();
                    game.get_player().set_game_music_on(false);
                } else {
                    music_button.setText("Music: ON");
                    game.get_player().set_game_music_on(true);
                }
            }
        });

        graphics_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the graphics settings
                if (graphics_button.getText().toString().equals("Graphics: HIGH")) {
                    graphics_button.setText("Graphics: LOW");
                    game.get_player().set_graphics_high(false);
                } else {
                    graphics_button.setText("Graphics: HIGH");
                    game.get_player().set_graphics_high(true);
                }
            }
        });

        controls_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Change the controls settings
                if (controls_button.getText().toString().equals("Controls: SIMPLE")) {
                    controls_button.setText("Controls: ADVANCED");
                    game.get_player().set_controls_simple(false);
                } else {
                    controls_button.setText("Controls: SIMPLE");
                    game.get_player().set_controls_simple(true);
                }
            }
        });

        back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gvcs.click_sound.play();

                game.get_level_select_screen().dispose();
                level_select_screen new_level_select_screen = new level_select_screen(game);
                game.set_level_select_screen(new_level_select_screen);
                game.setScreen(game.get_level_select_screen());
            }
        });

        // adding buttons to the table
        settings_table.add(music_button).padTop(5).width(250).height(150);
        settings_table.row();
        settings_table.add(graphics_button).padTop(5).width(250).height(150);
        settings_table.row();
        settings_table.add(controls_button).padTop(5).width(250).height(150);
        settings_table.row();
        settings_table.add(back_button).padTop(5).width(200).height(100);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(settings_stage); // Set the input processor to the stage
    }

    @Override
    public void render(float delta) {
        // Clear the screen and draw the background
        settings_batch.begin();

        // Draw the background
        settings_batch.draw(settings_background, 0, 0, settings_viewport.getWorldWidth(), settings_viewport.getWorldHeight());

        // Set the font size by scaling
        settings_font.getData().setScale(4.0f);
        settings_font.setColor(Color.MAGENTA);
        
        settings_font.draw(settings_batch, "Settings", 850, 1000); // Adjust x, y position based on scaling

        settings_batch.end();

        // Update and draw the stage
        settings_stage.act(delta);
        settings_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gvcs.World_Height = height;
        gvcs.World_Width = width;
        settings_viewport.update(width, height); // Adjust the viewport on resize
    }

    @Override
    public void dispose() {
        settings_stage.dispose(); // Dispose of stage resources
        settings_font.dispose();
        settings_batch.dispose();

    }

}