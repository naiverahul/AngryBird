package vemy.profiles;

import vemy.working_game.utils.base_player;

public class default_player extends base_player {
    /*
     * This class is the default player class. It contains all the basic information that a player class should have.
     * It will be used if the player wants to play the game without creating a new profile.
     * It will have all the information a player would have. Classes extending this class will have to override the values of the variables.
     * It should have a variable that is pointing to the loaded profile, and a variable that is pointing to the new profile. They should be null by default, and should be set to the loaded or new profile when the player loads or creates a new profile.
     */

    // Properties
    public default_player() {
        super();
        this.set_default_player(this);
        this.set_game_music_on(true);
        this.set_graphics_high(true);
    }
    
}