package vemy.working_game.screens;

import com.badlogic.gdx.ScreenAdapter;

import vemy.working_game.my_game;

public class level_playing_screen extends ScreenAdapter{
    /*
     * This class will be the screen where the player will play the game, the selected level.
     * It will have a pause button and a button to go back to the level select screen.
     * It must generate the level with the help of level_factory and level_base classes, and must simulate the physics required. 
     * It update the level as the player plays. 
     * 
     * Game will have slingshot, birds, pigs, and various obstacles. Pigs will be the enemies and the player will have to destroy them using the birds. Pigs will be placed in a structure made of obstacles.
     * One bird can only be used once and the player will have to use the birds wisely to destroy all the pigs. Score will be calculated based on the number of birds used and the time taken to destroy all the pigs.
     */

    private my_game game;

    public level_playing_screen(my_game game) {
        this.game = game;
    }

    public level_playing_screen(my_game game2, int i) {
        
    }
}
