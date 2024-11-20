package vemy.working_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;

import vemy.profiles.default_player;
import vemy.working_game.levels.level_factory;
import vemy.working_game.managers.asset_manager;
import vemy.working_game.managers.level_manager;
import vemy.working_game.managers.profile_manager;
import vemy.working_game.managers.score_manager;
import vemy.working_game.physics.collision_handler;
import vemy.working_game.physics.physics_engine;
import vemy.working_game.screens.game_open_screen;
import vemy.working_game.screens.level_playing_screen;
import vemy.working_game.screens.level_select_screen;
import vemy.working_game.screens.login_screen;
import vemy.working_game.screens.settings_screen;
import vemy.working_game.screens.signup_screen;
import vemy.working_game.utils.base_player;
import vemy.working_game.utils.gvcs;

public class my_game extends Game{
    /*
     * This class will be the main class of the game. It will be the entry point of the game. It must load the game_open_screen first.
     */

    
    // Physics
    private physics_engine main_physics_engine;
    private collision_handler main_collision_handler;

    // Managers
    private static asset_manager main_asset_manager = asset_manager.get_instance();
    private profile_manager main_profile_manager;
    private level_manager main_level_manager;
    private score_manager main_score_manager;
    
    // Screens
    private game_open_screen game_loading_screen;
    private settings_screen main_settings_screen;
    private level_playing_screen main_level_playing_screen;
    private level_select_screen main_level_select_screen;
    private login_screen main_login_screen;
    private signup_screen main_signup_screen;
    
    // Game Components
    private level_factory main_level_factory;
    private default_player main_player = new default_player();


    // Getters
    public level_factory get_level_factory() {
        return main_level_factory;
    }
    public asset_manager get_asset_manager() {
        return main_asset_manager;
    }
    public profile_manager get_profile_manager() {
        return main_profile_manager;
    }
    public level_manager get_level_manager() {
        return main_level_manager;
    }
    public score_manager get_score_manager() {
        return main_score_manager;
    }
    public physics_engine get_physics_engine() {
        return main_physics_engine;
    }
    public collision_handler get_collision_handler() {
        return main_collision_handler;
    }
    public game_open_screen get_game_loading_screen() {
        return game_loading_screen;
    }
    public settings_screen get_settings_screen() {
        return main_settings_screen;
    }
    public level_playing_screen get_level_playing_screen() {
        return main_level_playing_screen;
    }
    public level_select_screen get_level_select_screen() {
        return main_level_select_screen;
    }
    public login_screen get_login_screen() {
        return main_login_screen;
    }
    public signup_screen get_signup_screen() {
        return main_signup_screen;
    }
    public default_player get_player() {
        return main_player;
    }

    // Setters
    public void set_level_factory(level_factory main_level_factory) {
        this.main_level_factory = main_level_factory;
    }
    public void set_profile_manager(profile_manager main_profile_manager) {
        this.main_profile_manager = main_profile_manager;
    }
    public void set_level_manager(level_manager main_level_manager) {
        this.main_level_manager = main_level_manager;
    }
    public void set_score_manager(score_manager main_score_manager) {
        this.main_score_manager = main_score_manager;
    }
    public void set_physics_engine(physics_engine main_physics_engine) {
        this.main_physics_engine = main_physics_engine;
    }
    public void set_collision_handler(collision_handler main_collision_handler) {
        this.main_collision_handler = main_collision_handler;
    }
    public void set_game_loading_screen(game_open_screen game_loading_screen) {
        this.game_loading_screen = game_loading_screen;
    }
    public void set_settings_screen(settings_screen main_settings_screen) {
        this.main_settings_screen = main_settings_screen;
    }
    public void set_level_playing_screen(level_playing_screen main_level_playing_screen) {
        this.main_level_playing_screen = main_level_playing_screen;
    }
    public void set_level_select_screen(level_select_screen main_level_select_screen) {
        this.main_level_select_screen = main_level_select_screen;
    }
    public void set_login_screen(login_screen main_login_screen) {
        this.main_login_screen = main_login_screen;
    }
    public void set_signup_screen(signup_screen main_signup_screen) {
        this.main_signup_screen = main_signup_screen;
    }
    public void set_player(default_player main_player) {
        this.main_player = main_player;
    }
    

    @Override
    public void create() {
        
        main_asset_manager.load_assets();


        game_loading_screen = new game_open_screen(this);
        main_settings_screen = new settings_screen(this);
        main_level_playing_screen = new level_playing_screen(this);
        main_level_select_screen = new level_select_screen(this);
        main_login_screen = new login_screen(this);
        main_signup_screen = new signup_screen(this);

        // Starting the game music
        if (main_player.get_game_music_on()) {
            gvcs.background_music.play();
        }

        // Load the game_open_screen
        this.setScreen(game_loading_screen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (game_loading_screen != null) {
            game_loading_screen.dispose();
        }
        if (main_settings_screen != null) {
            main_settings_screen.dispose();
        }
        if (main_level_playing_screen != null) {
            main_level_playing_screen.dispose();
        }
        if (main_level_select_screen != null) {
            main_level_select_screen.dispose();
        }
        if (main_login_screen != null) {
            main_login_screen.dispose();
        }
        if (main_signup_screen != null) {
            main_signup_screen.dispose();
        }
        main_asset_manager.dispose();
        // super.dispose();
    }



}
