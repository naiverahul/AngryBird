package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class level_screen_ORIGINAL implements Screen{
    private MyGame original_game_variable;
    
    private Stage l_stage;
    private AssetManager l_asset_manager;
    private SpriteBatch l_batch;
    
    private FitViewport l_viewport;

    private Texture l_background;
    private Texture l_back;
    private Texture l_1;
    private Texture l_2;
    private Texture l_3;
    private Texture l_4;

    private ImageButton l_back_button;
    private ImageButton l_1_button;
    private ImageButton l_2_button;
    private ImageButton l_3_button;
    private ImageButton l_4_button;

    private void asset_load_check(Object asset, String asset_name){
        if (asset != null){
            Gdx.app.log(asset_name,"loaded");
        } else {
            Gdx.app.log(asset_name,"not loaded");
        }
    }
    private void asset_load(){
        l_asset_manager.load("level_screen.png", Texture.class);
        l_asset_manager.load("back.png", Texture.class);
        l_asset_manager.load("level_number/1.png", Texture.class);
        l_asset_manager.load("level_number/2.png", Texture.class);
        l_asset_manager.load("level_number/3.png", Texture.class);
        l_asset_manager.load("level_number/4.png", Texture.class);
        l_asset_manager.finishLoading();
    }

    public level_screen_ORIGINAL(MyGame game, AssetManager asset_manager){
        this.original_game_variable = game;
        this.l_asset_manager = asset_manager;
        asset_load();
        
        l_batch = new SpriteBatch();
        l_viewport = new FitViewport(800, 480);

        l_background = l_asset_manager.get("level_screen.png",Texture.class);
        l_back = l_asset_manager.get("back.png",Texture.class);
        l_1 = l_asset_manager.get("level_number/1.png",Texture.class);
        l_2 = l_asset_manager.get("level_number/2.png",Texture.class);
        l_3 = l_asset_manager.get("level_number/3.png",Texture.class);
        l_4 = l_asset_manager.get("level_number/4.png",Texture.class);
               
        asset_load_check(asset_manager, "asset_manager");
        asset_load_check(l_background, "background_image");
        asset_load_check(l_back, "back_button_image");
        asset_load_check(l_1, "level_1_button_image");
        asset_load_check(l_2, "level_2_button_image");
        asset_load_check(l_3, "level_3_button_image");
        asset_load_check(l_4, "level_4_button_image");

        l_back_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(l_back)));
        l_1_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(l_1)));
        l_2_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(l_2)));
        l_3_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(l_3)));
        l_4_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(l_4)));

        l_back_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // original_game_variable.setScreen(new MainMenuScreen(original_game_variable));
                original_game_variable.setScreen(original_game_variable.loadingScreen);
                // Change main menu screen
            }
        });
        l_1_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // original_game_variable.setScreen(original_game_variable.firstScreen);
                // Change GameScreen to level 1 screen
                original_game_variable.setScreen(original_game_variable.loadingScreen);

            }
        });
        l_2_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // original_game_variable.setScreen(original_game_variable.firstScreen);
                // Change GameScreen to level 1 screen
                original_game_variable.setScreen(original_game_variable.loadingScreen);

            }
        });
        l_3_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // original_game_variable.setScreen(original_game_variable.firstScreen);
                // Change GameScreen to level 1 screen
                original_game_variable.setScreen(original_game_variable.loadingScreen);

            }
        });
        l_4_button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // original_game_variable.setScreen(original_game_variable.firstScreen);
                // Change GameScreen to level 1 screen
                original_game_variable.setScreen(original_game_variable.loadingScreen);

            }
        });
        
        
        l_stage = new Stage();
        l_stage.setViewport(l_viewport);

        l_stage.addActor(l_back_button);
        l_stage.addActor(l_1_button);
        l_stage.addActor(l_2_button);
        l_stage.addActor(l_3_button);
        l_stage.addActor(l_4_button);

        
    }

    @Override
    public void resize(int width, int height) {
        l_viewport.update(width, height, true);
        // l_stage.setViewport(l_viewport);
        // l_stage.getViewport().update(width, height,true);

        logic_handler();
    }
    private void logic_handler(){
        float world_width = l_viewport.getWorldWidth();
        float world_height = l_viewport.getWorldHeight();
        
        l_back_button.setPosition(0, world_height - l_back.getHeight());
        l_1_button.setPosition(world_width / 2 - l_1.getWidth() / 2, world_height / 2 - l_1.getHeight() / 2);
        l_2_button.setPosition(world_width / 2 - l_2.getWidth() / 2, world_height / 2 - l_2.getHeight() / 2 - 50);
        l_3_button.setPosition(world_width / 2 - l_3.getWidth() / 2, world_height / 2 - l_3.getHeight() / 2 - 100);
        l_4_button.setPosition(world_width / 2 - l_4.getWidth() / 2, world_height / 2 - l_4.getHeight() / 2 - 150);
    }
    

    @Override
    public void show() {
        Gdx.input.setInputProcessor(l_stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);
        l_viewport.apply();
        l_batch.setProjectionMatrix(l_viewport.getCamera().combined);
        l_batch.begin();

        l_batch.draw(l_background, 0, 0, l_viewport.getWorldWidth(), l_viewport.getWorldHeight());

        l_batch.end();

        l_stage.act(delta);
        l_stage.draw();
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
        l_stage.dispose();
        l_batch.dispose();
        l_background.dispose();
        l_back.dispose();
        l_1.dispose();
        l_2.dispose();
        l_3.dispose();
        l_4.dispose();
    }

}
