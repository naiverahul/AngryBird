package vemy.working_game.utils;

import vemy.profiles.default_player;
import vemy.profiles.loaded_profile;
import vemy.profiles.new_profile;

public class base_player {
    /* 
     * This class is the base class for all player classes. It contains all the basic information that a player class should have.
     * This class is not meant to be instantiated, but rather to be extended by other classes.
     * 
     */
    
    // Profile Pointers
    private default_player default_player;
    private loaded_profile loaded_profile;
    private new_profile new_profile;

    
    // Properties
    private boolean game_music_on;
    private boolean graphics_high;
    private boolean controls_simple;


    // Getters
    public default_player get_default_player() {
        return default_player;
    }
    public loaded_profile get_loaded_profile() {
        return loaded_profile;
    }
    public new_profile get_new_profile() {
        return new_profile;
    }
    public boolean get_game_music_on() {
        return game_music_on;
    }
    public boolean get_graphics_high() {
        return graphics_high;
    }
    public boolean get_controls_simple() {
        return controls_simple;
    }


    // Setters
    public void set_default_player(default_player default_player) {
        this.default_player = default_player;
    }
    public void set_loaded_profile(loaded_profile loaded_profile) {
        this.loaded_profile = loaded_profile;
    }
    public void set_new_profile(new_profile new_profile) {
        this.new_profile = new_profile;
    }
    public void set_game_music_on(boolean game_music_on) {
        this.game_music_on = game_music_on;
    }
    public void set_graphics_high(boolean graphics_high) {
        this.graphics_high = graphics_high;
    }
    public void set_controls_simple(boolean controls_simple) {
        this.controls_simple = controls_simple;
    }
}
