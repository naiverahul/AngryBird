package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class pause_screen implements Screen {
    private AssetManager p_asset_manager;
    private MyGame orignal_game_variable;
    private Stage p_stage;
    private ImageButton p_resume_button, p_menu_button, p_exit_button;


    public pause_screen(MyGame game, AssetManager asset_manager) {
        p_asset_manager = asset_manager;
        orignal_game_variable = game;
        p_stage = new Stage(new FitViewport(1920,1080));
        Gdx.input.setInputProcessor(p_stage);
        Table table = new Table();
        table.setFillParent(true);
        p_stage.addActor(table);
        // p_resume_button = new ImageButton(new NinePatchDrawable(new NinePatch(new Texture("resume_button.png"), 10, 10, 10, 10)));
        // p_menu_button = new ImageButton(new NinePatchDrawable(new NinePatch(new Texture("menu_button.png"), 10, 10, 10, 10)));
        // p_exit_button = new ImageButton(new NinePatchDrawable(new NinePatch(new Texture("exit_button.png"), 10, 10, 10, 10)));
        table.add(p_resume_button).pad(10);
        table.row();
        table.add(p_menu_button).pad(10);
        table.row();
        table.add(p_exit_button).pad(10);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(p_stage);
    }

    @Override
    public void render(float delta) {
        p_stage.act();
        p_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'resize'");
    }

    @Override
    public void pause() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

}
