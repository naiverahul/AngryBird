package vemy.working_game.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import vemy.working_game.utils.gvcs;

public class asset_manager {
    /*
     * This class is used to load all the assets in the game. It is a singleton class that will be used by all the classes in the game to access the assets.
     */

    private static asset_manager instance;
    private static AssetManager assetManager;

    private asset_manager() {
        assetManager = new AssetManager();
    }
    public static asset_manager get_instance() {
        if (instance == null) {
            instance = new asset_manager();
        }
        // return instance.assetManager;
        return instance;
    }

    public void load_assets() {
        // Load sound and music
        assetManager.load(gvcs.game_music_path, Music.class);
        assetManager.load(gvcs.click_sound_path, Sound.class);
        assetManager.load(gvcs.lose_sound_path, Sound.class);
        assetManager.load(gvcs.win_sound_path, Sound.class);

        // Load background images
        assetManager.load(gvcs.game_screen_path, Texture.class);
        assetManager.load(gvcs.level_select_screen_path, Texture.class);
        assetManager.load(gvcs.loading_screen_path, Texture.class);
        assetManager.load(gvcs.pause_background_path, Texture.class);
        assetManager.load(gvcs.setting_background_path, Texture.class);

        // Load sling_shot
        assetManager.load(gvcs.sling_shot_path, Texture.class);

        // Load birds
        assetManager.load(gvcs.big_bird_path, Texture.class);
        assetManager.load(gvcs.black_bird_path, Texture.class);
        assetManager.load(gvcs.red_bird_path, Texture.class);
        assetManager.load(gvcs.yellow_bird_path, Texture.class);

        // Load pigs
        assetManager.load(gvcs.cook_pig_path, Texture.class);
        assetManager.load(gvcs.normal_pig_path, Texture.class);
        assetManager.load(gvcs.stupid_pig_path, Texture.class);
        assetManager.load(gvcs.teen_pig_path, Texture.class);


        // Load level_number
        assetManager.load(gvcs.level_1_button_path, Texture.class);
        assetManager.load(gvcs.level_2_button_path, Texture.class);
        assetManager.load(gvcs.level_3_button_path, Texture.class);
        assetManager.load(gvcs.level_4_button_path, Texture.class);

        // Load buttons
        assetManager.load(gvcs.back_button_path, Texture.class);
        assetManager.load(gvcs.close_button_path, Texture.class);
        assetManager.load(gvcs.exit_button_path, Texture.class);
        assetManager.load(gvcs.hollow_button_path, Texture.class);
        assetManager.load(gvcs.pause_button_path, Texture.class);
        assetManager.load(gvcs.restart_button_path, Texture.class);
        assetManager.load(gvcs.resume_button_path, Texture.class);
        assetManager.load(gvcs.settings_button_path, Texture.class);
        assetManager.load(gvcs.start_button_path, Texture.class);

        // Load blocks
        assetManager.load(gvcs.glass_rectangle_path, Texture.class);
        assetManager.load(gvcs.glass_traingle_path, Texture.class);
        assetManager.load(gvcs.stone_circle_path, Texture.class);
        assetManager.load(gvcs.stone_triangle_path, Texture.class);
        assetManager.load(gvcs.wood_rectangle_path, Texture.class);
        assetManager.load(gvcs.wood_traingle_path, Texture.class);
        assetManager.load(gvcs.wood_square_path, Texture.class);

        // Load font
        assetManager.load(gvcs.default_font_path, BitmapFont.class);
    }

    public void finish_loading() {
        assetManager.finishLoading();
    }

    public <T> T get(String assetName, Class<T> type) {
        return assetManager.get(assetName, type);
    }
 
    public void dispose() {
        assetManager.dispose();
        asset_manager.instance = null;
    }
}
