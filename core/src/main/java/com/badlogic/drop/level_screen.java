package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class level_screen implements Screen{
    private MyGame original_game_variable;
    
    private Stage l_stage;
    private AssetManager l_asset_manager;
    private SpriteBatch l_batch;
    
    private FitViewport l_viewport;
    // private OrthographicCamera l_camera;

    private Texture l_background;
    private Texture l_back;
    // private ImageButton l_back_button;
    private Sprite l_back_button;
    private Texture l_1;
    // private ImageButton l_1_button;
    private Sprite l_1_button;
    private Texture l_2;
    // private ImageButton l_2_button;
    private Sprite l_2_button;
    private Texture l_3;
    // private ImageButton l_3_button;
    private Sprite l_3_button;
    private Texture l_4;
    // private ImageButton l_4_button;
    private Sprite l_4_button;

    private void asset_load_check(Object asset, String asset_name){
        if (asset != null){
            Gdx.app.log(asset_name,"loaded");
        } else {
            Gdx.app.log(asset_name,"not loaded");
        }
    }

    public level_screen(MyGame game, AssetManager asset_manager){
        this.original_game_variable = game;
        this.l_asset_manager = asset_manager;
        
        l_batch = new SpriteBatch();
        l_viewport = new FitViewport(800, 480);
        // l_camera = new OrthographicCamera();
        // l_viewport = new FitViewport(800, 480, l_camera);
        l_background = new Texture("level_screen.png");

        l_back = new Texture("back.png");
        // l_back_button = new ImageButton(new TextureRegionDrawable(l_back));
        l_back_button = new Sprite(l_back);
        l_1 = new Texture("level_number/1.png");
        // l_1_button = new ImageButton(new TextureRegionDrawable(l_1));
        l_1_button = new Sprite(l_1);
        l_2 = new Texture("level_number/2.png");
        // l_2_button = new ImageButton(new TextureRegionDrawable(l_2));
        l_2_button = new Sprite(l_2);
        l_3 = new Texture("level_number/3.png");
        // l_3_button = new ImageButton(new TextureRegionDrawable(l_3));
        l_3_button = new Sprite(l_3);
        l_4 = new Texture("level_number/4.png");
        // l_4_button = new ImageButton(new TextureRegionDrawable(l_4));
        l_4_button = new Sprite(l_4);

        // l_stage = new Stage();
        // l_stage.setViewport(l_viewport);

        
        // l_stage.addActor(l_back_button);
        // l_stage.addActor(l_1_button);
        // l_stage.addActor(l_2_button);
        // l_stage.addActor(l_3_button);
        // l_stage.addActor(l_4_button);

        asset_load_check(asset_manager, "asset_manager");
        asset_load_check(l_background, "background_image");
        asset_load_check(l_back, "back_button_image");
        asset_load_check(l_1, "level_1_button_image");
        asset_load_check(l_2, "level_2_button_image");
        asset_load_check(l_3, "level_3_button_image");
        asset_load_check(l_4, "level_4_button_image");



    }

    @Override
    public void resize(int width, int height) {
        l_viewport.update(width, height, true);
        // l_stage.setViewport(l_viewport);
        // l_stage.getViewport().update(width, height,true);
    }

    @Override
    public void show() {
        // Gdx.input.setInputProcessor(l_stage);
    }

    @Override
    public void render(float delta) {
        input_handler();
        logic_handler();
        draw_handler(delta);
    }

    private void input_handler(){}
    private void logic_handler(){
        float world_width = l_viewport.getWorldWidth();
        float world_height = l_viewport.getWorldHeight();
        
        float back_button_width = l_back_button.getWidth();
        float back_button_height = l_back_button.getHeight();

        float level_1_button_width = l_1_button.getWidth();
        float level_1_button_height = l_1_button.getHeight();

        float level_2_button_width = l_2_button.getWidth();
        float level_2_button_height = l_2_button.getHeight();

        float level_3_button_width = l_3_button.getWidth();
        float level_3_button_height = l_3_button.getHeight();

        float level_4_button_width = l_4_button.getWidth();
        float level_4_button_height = l_4_button.getHeight();



        
        
        // l_back_button.setPosition(0, world_height - l_back.getHeight());
        // l_1_button.setPosition(world_width/2 - l_1.getWidth()/2, world_height/2 - l_1.getHeight()/2);
        // l_2_button.setPosition(world_width/2 - l_2.getWidth()/2, world_height/2 - l_2.getHeight()/2);
        // l_3_button.setPosition(world_width/2 - l_3.getWidth()/2, world_height/2 - l_3.getHeight()/2);
        // l_4_button.setPosition(world_width/2 - l_4.getWidth()/2, world_height/2 - l_4.getHeight()/2);
    }
    private void draw_handler(float delta){
        ScreenUtils.clear(Color.CYAN);
        l_viewport.apply();

        l_batch.setProjectionMatrix(l_viewport.getCamera().combined);
        l_batch.begin();

        float world_width = l_viewport.getWorldWidth();
        float world_height = l_viewport.getWorldHeight();

        l_batch.draw(l_background, 0, 0, world_width, world_height);
        // l_batch.draw(l_back, 0, (world_height - l_back.getHeight()));
        // l_batch.draw(l_1, (world_width/2 - l_1.getWidth()/2), (world_height/2 - l_1.getHeight()/2));
        // l_batch.draw(l_2, (world_width/2 - l_2.getWidth()/2), (world_height/2 - l_2.getHeight()/2));
        // l_batch.draw(l_3, (world_width/2 - l_3.getWidth()/2), (world_height/2 - l_3.getHeight()/2));
        // l_batch.draw(l_4, (world_width/2 - l_4.getWidth()/2), (world_height/2 - l_4.getHeight()/2));

        l_back_button.draw(l_batch);
        l_1_button.draw(l_batch);
        l_2_button.draw(l_batch);
        l_3_button.draw(l_batch);
        l_4_button.draw(l_batch);

        l_batch.end();

        // l_stage.act(delta);
        // l_stage.draw();
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
        // l_batch.dispose();
        // l_background.dispose();
        // l_back.dispose();
        // l_1.dispose();
        // l_2.dispose();
        // l_3.dispose();
        // l_4.dispose();
        // l_stage.dispose();
    }

}
