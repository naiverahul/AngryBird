package vemy.working_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import vemy.working_game.my_game;
import vemy.working_game.utils.gvcs;

public class level_select_screen extends ScreenAdapter{
    /*
     * This class will be the screen where the player will select the level they want to play.
     * It will have buttons for each level and a button to go back to the game open screen.
     * It will also show the player's progress in the game.
     * A player can only play the next level if they have completed the previous level.
     * It will load the levels with the help of level_factory and level_base classes.
     * After selecting the level, the player will be taken to the level playing screen.
     */

    private my_game game;
    private Stage ls_stage;
    private FitViewport ls_viewport;
    private Texture ls_background;
    private Table ls_table;
    private ImageButton ls_back_button, ls_settings_button;
    private ImageButton[] level_buttons;
    private int button_number;
    
    public level_select_screen(my_game game) {
        this.game = game;
        ls_viewport = new FitViewport(gvcs.World_Width, gvcs.World_Height);
        ls_stage = new Stage(ls_viewport);
        ls_background = game.get_asset_manager().get(gvcs.level_select_screen_path, Texture.class);

        create_ui();
    }

    private void create_ui(){

        ls_table = new Table();
        ls_table.setFillParent(true);
        ls_stage.addActor(ls_table);

        ls_back_button = createButton(gvcs.back_button_path);
        ls_back_button.setSize(gvcs.ls_back_button_size[0], gvcs.ls_back_button_size[1]);
        ls_back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gvcs.click_sound.play();

                game.get_game_loading_screen().dispose();
                game_open_screen new_game_loading_screen = new game_open_screen(game);
                game.set_game_loading_screen(new_game_loading_screen);
                game.setScreen(new_game_loading_screen);
            }
        });
        ls_table.top().left();
        ls_table.add(ls_back_button).size(gvcs.ls_level_button_size[0],gvcs.ls_level_button_size[1]).left().pad(10);
        ls_table.row();

        
        for (button_number = 0; button_number<gvcs.max_levels; button_number++){
            level_buttons[button_number] = createButton(gvcs.level_buttons + (button_number+1) + ".png");

            level_buttons[button_number].setSize(gvcs.ls_level_button_size[0], gvcs.ls_level_button_size[1]);   

            level_buttons[button_number].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gvcs.click_sound.play();

                    level_playing_screen new_level_playing_screen = new level_playing_screen(game, button_number+1);
                    game.set_level_playing_screen(new_level_playing_screen);
                    game.setScreen(new_level_playing_screen);
                }
            });
            ls_table.add(level_buttons[button_number]).size(gvcs.ls_level_button_size[0],gvcs.ls_level_button_size[1]).expandX().pad(20);
            if ((button_number+1)%(gvcs.max_level_buttons_in_row) == 0){
                ls_table.row();
            }
        }
        // ls_table.row();
        if ((gvcs.max_levels)%(gvcs.max_level_buttons_in_row) != 0){
            ls_table.row();
        }

        ls_settings_button = createButton(gvcs.settings_button_path);
        ls_settings_button.setSize(gvcs.ls_settings_button_size[0], gvcs.ls_settings_button_size[1]);
        ls_settings_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gvcs.click_sound.play();

                game.get_settings_screen().dispose();
                settings_screen new_settings_screen = new settings_screen(game);
                game.set_settings_screen(new_settings_screen);
                game.setScreen(new_settings_screen);
            }
        });
        ls_table.add(ls_settings_button).size(200f, 200f).expand().bottom().left().pad(10);
    }

    private ImageButton createButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }
   

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ls_stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);

        ls_stage.getBatch().begin();
        ls_stage.getBatch().draw(ls_background, 0, 0, ls_stage.getViewport().getWorldWidth(), ls_stage.getViewport().getWorldHeight());
        ls_stage.getBatch().end();

        ls_stage.act(delta);
        ls_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gvcs.World_Width = width;
        gvcs.World_Height = height;
        ls_stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        ls_stage.dispose();
        ls_background.dispose();
    }
}
