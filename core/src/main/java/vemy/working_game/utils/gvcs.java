package vemy.working_game.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import vemy.profiles.*;
import vemy.working_game.managers.asset_manager;

public class gvcs { // game_value_constants
    /*
     * purpose of this file:
     * 1. to store all the constants that are used in the game
     * 2. to change the values of the constants of the game as the player progresses
     * in the game.
     * 3. the constants must change depending on which of the current player's
     * profile is being used.
     */

    private static asset_manager constant_asset_manager = asset_manager.get_instance();
    {
        constant_asset_manager.load_assets();
    }

    // file paths.
    public static final String game_music_path = "sounds/system/angry_birds_music.mp3";
    // public static final String game_music_path = "angry_birds_music.mp3";
    public static final String click_sound_path = "sounds/system/click_sound.mp3";
    public static final String lose_sound_path = "sounds/lose_sound.mp3";
    public static final String win_sound_path = "sounds/win_sound.mp3";

    public static final String game_screen_path = "images/backgrounds/game_screen.png";
    public static final String level_select_screen_path = "images/backgrounds/level_select_screen.png";
    public static final String loading_screen_path = "images/backgrounds/loading_screen.png";
    public static final String pause_background_path = "images/backgrounds/pause_background.png";
    public static final String setting_background_path = "images/backgrounds/setting_background.png";

    public static final String sling_shot_path = "images/sling_shot/sling_shot.png";

    public static final String big_bird_path = "images/birds/big_birds.png";
    public static final String black_bird_path = "images/birds/black_birds.png";
    public static final String red_bird_path = "images/birds/red_birds.png";
    public static final String yellow_bird_path = "images/birds/yellow_birds.png";

    public static final String cook_pig_path = "images/pigs/cook_pig.png";
    public static final String normal_pig_path = "images/pigs/normal_pig.png";
    public static final String stupid_pig_path = "images/pigs/stupid_pig.png";
    public static final String teen_pig_path = "images/pigs/teen_pig.png";

    public static final String level_1_button_path = "images/level_number/1.png";
    public static final String level_2_button_path = "images/level_number/2.png";
    public static final String level_3_button_path = "images/level_number/3.png";
    public static final String level_4_button_path = "images/level_number/4.png";

    public static final String back_button_path = "images/buttons/back_button.png";
    public static final String close_button_path = "images/buttons/close_button.png";
    public static final String exit_button_path = "images/buttons/exit_button.png";
    public static final String hollow_button_path = "images/buttons/hollow_button.png";
    public static final String pause_button_path = "images/buttons/pause_button.png";
    public static final String restart_button_path = "images/buttons/restart_button.png";
    public static final String resume_button_path = "images/buttons/resume_button.png";
    public static final String settings_button_path = "images/buttons/settings_button.png";
    public static final String start_button_path = "images/buttons/start_button.png";

    public static final String level_buttons = "images/level_number/";

    public static final String glass_rectangle_path = "images/obstacles/Glase/glass_rectangle.png";
    public static final String glass_traingle_path = "images/obstacles/Glase/glass_triangle.png";

    public static final String stone_circle_path = "images/obstacles/Stone/stone_circle.png";
    public static final String stone_triangle_path = "images/obstacles/Stone/stone_triangle.png";

    public static final String wood_rectangle_path = "images/obstacles/Wood/wood_rectangle.png";
    public static final String wood_traingle_path = "images/obstacles/Wood/wood_triangle.png";
    public static final String wood_square_path = "images/obstacles/Wood/wood_square.png";

    public static final String default_font_path = "default.fnt";

    // internal working properties
    public static Music background_music = constant_asset_manager.get(game_music_path, Music.class);
    {
        background_music.setLooping(true);
    }

    public static Sound click_sound = constant_asset_manager.get(click_sound_path, Sound.class);
    public static Sound lose_sound = constant_asset_manager.get(lose_sound_path, Sound.class);
    public static Sound win_sound = constant_asset_manager.get(win_sound_path, Sound.class);

    // world properties
    public static final int starting_world_width = 800;
    public static final int starting_world_height = 480;
    public static int World_Width = starting_world_width;
    public static int World_Height = starting_world_height;

    // physics properties
    public static final float gravity = 9.8f;

    // game properties
    public static final int max_levels = 4;
    public static final int max_level_buttons_in_row = 2;
    public static final float font_scale = 3.0f;
    public static final float ls_back_button_size[] = { 10f, 10f };
    public static final float ls_settings_button_size[] = { 80f, 80f };
    public static final float ls_level_button_size[] = { 200f, 200f };

}
