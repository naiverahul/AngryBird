package vemy.working_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
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

import vemy.working_game.my_game;
import vemy.working_game.managers.asset_manager;
import vemy.working_game.utils.gvcs;

public class game_open_screen extends ScreenAdapter {
    /*
     * This class will be the first screen that the player sees when they open the
     * game.
     * It will have a button that will ask player for login or sign up.
     * It will also have a button that will allow the player to play the game
     * without logging in.
     * In case of playing without logging in, default player profile must be loaded.
     * And the player will be taken to the level select screen
     */

    private my_game game;
    private asset_manager gol_asset_manager;

    private SpriteBatch gol_batch;
    private Skin gol_skin;
    private Stage gol_stage;
    private FitViewport gol_viewport;
    private OrthographicCamera gol_camera;
    private float gol_aspect_ratio, gol_virtual_width, gol_virtual_height;

    private ImageButton gol_login_button, gol_signup_button, gol_play_button, gol_exit_button;
    private Texture gol_background, gol_login_button_texture, gol_signup_button_texture, gol_play_button_texture,
            gol_exit_button_texture;

    private ProgressBar gol_progress_bar;
    private TextField gol_username_field, gol_password_field;
    private Label gol_username_label, gol_password_label;

    private boolean gol_name_dialog_visibile = false, gol_loading_complete = false, gol_button_visible = false;
    private Timer.Task gol_show_button_task;

    public game_open_screen(my_game game) {
        this.game = game;
        this.gol_asset_manager = game.get_asset_manager();
        this.gol_batch = new SpriteBatch();
        this.gol_stage = new Stage();
        this.gol_camera = new OrthographicCamera();

        // calculating the aspect ratio and the virtual width and height of the game
        {
            this.gol_virtual_height = gvcs.World_Height;
            this.gol_aspect_ratio = (float) ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
            this.gol_virtual_width = gol_aspect_ratio * gol_virtual_height;
        }

        this.gol_viewport = new FitViewport(gol_virtual_width, gol_virtual_height, gol_camera);

        // setting the viewport of the gol_stage
        {
            this.gol_stage.setViewport(gol_viewport);
            // this.gol_viewport.apply();
        }

        this.gol_skin = gol_asset_manager.get(gvcs.default_skin_path, Skin.class);
        this.gol_background = gol_asset_manager.get(gvcs.loading_screen_path, Texture.class);

        // loading the button textures
        {
            this.gol_play_button_texture = gol_asset_manager.get(gvcs.start_button_path, Texture.class);
            this.gol_exit_button_texture = gol_asset_manager.get(gvcs.exit_button_path, Texture.class);
            // this.gol_login_button_texture = gol_asset_manager.get(gvcs.login_button_path,
            // Texture.class);
            // this.gol_signup_button_texture =
            // gol_asset_manager.get(gvcs.signup_button_path, Texture.class);

        }

        // creating the buttons
        {
            this.gol_play_button = new ImageButton(new TextureRegionDrawable(gol_play_button_texture));
            this.gol_exit_button = new ImageButton(new TextureRegionDrawable(gol_exit_button_texture));
            // this.gol_login_button = new ImageButton(gol_login_button_texture);
            // this.gol_signup_button = new ImageButton(gol_signup_button_texture);
        }

        create_ui();

        Gdx.input.setInputProcessor(gol_stage);

    }

    private void create_ui() {
        // name dialog
        {
            gol_username_label = new Label("Username: ", gol_skin);
            gol_username_field = new TextField("Enter your username: ", gol_skin);
            gol_username_label.setVisible(gol_name_dialog_visibile);
            gol_username_field.setVisible(gol_name_dialog_visibile);
        }

        // loading progress bar setup
        {
            gol_skin = new Skin();
            Pixmap gol_pixmap = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
            gol_pixmap.setColor(Color.WHITE);
            gol_pixmap.fill();
            gol_skin.add("white", new Texture(gol_pixmap));
            ProgressBar.ProgressBarStyle gol_progress_bar_style = new ProgressBar.ProgressBarStyle(
                    gol_skin.newDrawable("white", Color.DARK_GRAY),
                    gol_skin.newDrawable("white", Color.BLUE));
            gol_progress_bar_style.knobBefore = gol_progress_bar_style.knob;
            gol_progress_bar = new ProgressBar(0, 1, 0.01f, false, gol_progress_bar_style);
            gol_progress_bar.setSize(800f, 100f);
            gol_progress_bar.setPosition(
                    (gol_viewport.getWorldWidth() - gol_progress_bar.getWidth()) / 2f + 400f,
                    150f);
            gol_progress_bar.setValue(0f);
            gol_progress_bar.setAnimateDuration(1.3f);
            gol_stage.addActor(gol_progress_bar);
            gol_pixmap.dispose();
        }

        // setting the position and size of the buttons
        {
            gol_play_button.setSize(250f, 140f);
            gol_play_button.setPosition(835f, gol_viewport.getWorldHeight() / 18f);
            gol_exit_button.setSize(250f, 140f);
            gol_exit_button.setPosition(0, 0);
            gol_username_label.setPosition(760f, 600f);
            gol_username_label.setFontScale(3.0f);
            gol_username_field.setPosition(760f, 400f);
            gol_username_field.setSize(400f, 80f);
            gol_username_field.setCursorPosition((int) gol_username_field.getWidth() / 2);
            gol_username_field.setColor(Color.NAVY);
            gol_stage.addActor(gol_username_label);
            gol_stage.addActor(gol_username_field);
            gol_stage.addActor(gol_play_button);
            gol_stage.addActor(gol_exit_button);
        }

        // adding listeners to the buttons
        {
            gol_play_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!gol_name_dialog_visibile) {
                        gol_username_label.setVisible(true);
                        gol_username_field.setVisible(true);
                        gol_name_dialog_visibile = true;
                    } else {
                        game.setScreen(game.get_level_select_screen());
                    }
                }
            });

            gol_exit_button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gvcs.click_sound.play();
                    Gdx.app.exit();
                }
            });
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == Input.Keys.ENTER) {
                        if (!gol_name_dialog_visibile) {
                            gol_username_label.setVisible(true);
                            gol_username_field.setVisible(true);
                            gol_name_dialog_visibile = true;
                        } else {
                            game.setScreen(game.get_level_select_screen());
                        }
                        return true;
                    } else if (keycode == Input.Keys.ESCAPE) {
                        gvcs.click_sound.play();
                        Gdx.app.exit();
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1f, 1.5f, 1);
        gol_batch.setProjectionMatrix(gol_camera.combined);
        gol_batch.begin();
        float imageWidth = 1440f;
        float imageHeight = 765f;
        float x = (gol_viewport.getWorldWidth() - imageWidth) / 2f;
        float y = (gol_viewport.getWorldHeight() - imageHeight) / 2f;
        gol_batch.draw(gol_background, x, y, imageWidth, imageHeight);
        gol_batch.end();
        float progress = gol_asset_manager.get_progress();
        gol_progress_bar.setValue(progress);

        if (progress >= 1.0f && !gol_loading_complete) {
            gol_loading_complete = true;
            gol_show_button_task = new Timer.Task() {
                @Override
                public void run() {
                    gol_progress_bar.setVisible(false);
                    gol_play_button.setVisible(true);
                    gol_exit_button.setVisible(true);
                    gol_button_visible = true;
                }
            };
            Timer.schedule(gol_show_button_task, 1.0f);
        }

        if (!gol_button_visible) {
            gol_play_button.setVisible(false);
            gol_exit_button.setVisible(false);
        }

        gol_stage.act(delta);
        gol_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gol_viewport.update(width, height, true);
        gol_stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        gol_batch.dispose();
        gol_background.dispose();
        gol_stage.dispose();
        ((TextureRegionDrawable) gol_play_button.getStyle().imageUp).getRegion().getTexture().dispose();
        ((TextureRegionDrawable) gol_exit_button.getStyle().imageUp).getRegion().getTexture().dispose();
        if (gol_show_button_task != null) {
            gol_show_button_task.cancel();
        }
    }
}