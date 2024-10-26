package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class lose_screen implements Screen{
    private final MyGame w_orignal_game_variable;
    private Stage w_stage;
    private FitViewport w_viewport;
    private ImageButton w_back_button, w_restart_button;
    private Texture w_background;
    private Music w_music;




    public lose_screen(MyGame w_orignal_game_variable) {
        this.w_orignal_game_variable = w_orignal_game_variable;
        this.w_viewport = new FitViewport(1920,1080);
        this.w_stage = new Stage(w_viewport);
        this.w_background = new Texture("lose_screen.png");
        this.w_music = Gdx.audio.newMusic(Gdx.files.internal("lose_sound.wav"));
        
        w_create_UI();
    }
    private void w_create_UI() {

        Table table = new Table();
        table.setFillParent(true);
        w_stage.addActor(table);

        w_back_button = w_create_button("back.png");
        w_restart_button = w_create_button("restart_button.png");
        add_listeners();

        add_buttons_to_table(table);
    }
    private ImageButton w_create_button(String image_path) {
        ImageButton button = new ImageButton(new TextureRegionDrawable(new Texture(image_path)));
        w_stage.addActor(button);
        return button;
    }
    private void add_listeners() {
        w_back_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                w_orignal_game_variable.clicksound.play();
                w_orignal_game_variable.level_screen.dispose();
                w_orignal_game_variable.level_screen = new level_screen(w_orignal_game_variable);
                w_orignal_game_variable.bkgmusic.play();
                w_orignal_game_variable.setScreen(w_orignal_game_variable.level_screen);
            }
        });
        w_restart_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                w_orignal_game_variable.clicksound.play();
                w_orignal_game_variable.game_screen.dispose();
                w_orignal_game_variable.game_screen = new game_screen(w_orignal_game_variable);
                w_orignal_game_variable.bkgmusic.play();
                w_orignal_game_variable.setScreen(w_orignal_game_variable.game_screen);
            }
        });
    }
    private void add_buttons_to_table(Table table) {
        table.add(w_back_button).size(300f, 150f).padBottom(20f).padTop(100f);
        table.add(w_restart_button).size(200f, 100f).padBottom(20f).padTop(100f);
        table.debug();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(w_stage);
        w_orignal_game_variable.bkgmusic.stop();
        w_music.play();
    }

    @Override
    public void render(float delta) {
        w_stage.getBatch().begin();
        w_stage.getBatch().draw(w_background, 0, 0, w_viewport.getWorldWidth(), w_viewport.getWorldHeight());
        w_stage.getBatch().end();

        w_stage.act(delta);
        w_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        w_viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        w_stage.dispose();
        w_background.dispose();
    }


}
