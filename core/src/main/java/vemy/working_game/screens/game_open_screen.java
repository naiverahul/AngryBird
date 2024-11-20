package vemy.working_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

        // setting the viewport of the stage
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
            this.gol_login_button_texture = gol_asset_manager.get(gvcs.login_button_path, Texture.class);
            this.gol_signup_button_texture = gol_asset_manager.get(gvcs.signup_button_path, Texture.class);
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

        setup_progress_bar();



    }

    private void setup_progress_bar(){
        
    }
}
