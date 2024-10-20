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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class game_screen implements Screen{
    private MyGame orginal_game_variable;
    private Stage g_stage;
    private AssetManager g_asset_manager;
    // private SpriteBatch g_batch; // idk if this is still needed.

    private FitViewport g_viewport;
    private Texture g_background;
    private ImageButton g_back_button, g_pause_button, g_catapult, g_bird_big, g_bird_black, g_bird_red, g_bird_yellow;

    private Screen g_pause_screen, g_level_screen;
    
    public game_screen(MyGame game, AssetManager asset_manager){
        this.orginal_game_variable = game;
        this.g_asset_manager = asset_manager;
        g_assest_load();
        
        g_viewport = new FitViewport(800, 600);
        g_stage = new Stage(g_viewport);

        g_pause_screen = orginal_game_variable.pause_screen;
        g_level_screen = orginal_game_variable.level_screen;

        // g_background = new Texture(Gdx.files.internal("game_screen.png"));
        g_background = g_asset_manager.get("game_screen.png", Texture.class);
        // g_catapult = g_asset_manager.get("Catapult.png", Texture.class);
        g_create_ui();
    }
    private void g_assest_load(){
        g_asset_manager.load("game_screen.png", Texture.class);
        g_asset_manager.load("back.png", Texture.class);
        g_asset_manager.load("pause.png", Texture.class);
        g_asset_manager.load("Birdimages/bigbird.png", Texture.class);
        g_asset_manager.load("Birdimages/blackbird.png", Texture.class);
        g_asset_manager.load("Birdimages/redbird.png", Texture.class);
        g_asset_manager.load("Birdimages/yellowbird.png", Texture.class);
        g_asset_manager.load("Catapult.png", Texture.class);    
        g_asset_manager.finishLoading();
    }
    private void g_create_ui(){
        // g_batch = new SpriteBatch(); // idk if this is still needed.
        Table g_table = g_initialize_table(true);
        g_catapult = g_create_button("Catapult.png");
        g_initialize_buttons();
        g_set_button_sizes(100f,100f);
        g_set_button_listeners();
        g_set_button_positions(g_table);
    }
    private Table g_initialize_table(boolean fill_parent){
        Table table = new Table();
        table.setFillParent(fill_parent);
        g_stage.addActor(table);
        return table;
    }
    private void g_initialize_buttons(){
        g_back_button = g_create_button("back.png");
        g_pause_button = g_create_button("pause.png");
        g_bird_big = g_create_button("Birdimages/bigbird.png");
        g_bird_black = g_create_button("Birdimages/blackbird.png");
        g_bird_red = g_create_button("Birdimages/redbird.png");
        g_bird_yellow = g_create_button("Birdimages/yellowbird.png");
    }
    private ImageButton g_create_button(String button_path){
        Texture button_texture = g_asset_manager.get(button_path, Texture.class);
        TextureRegion button_texture_region = new TextureRegion(button_texture);
        TextureRegionDrawable button_texture_region_drawable = new TextureRegionDrawable(button_texture_region);
        ImageButton button = new ImageButton(button_texture_region_drawable);
        g_stage.addActor(button);
        return button;
    }
    private void g_set_button_sizes(float button_width, float button_height){
        g_back_button.setSize(button_width, button_height);
        g_pause_button.setSize(button_width, button_height);
        g_bird_big.setSize(button_width, button_height);
        g_bird_black.setSize(button_width, button_height);
        g_bird_red.setSize(button_width, button_height);
        g_bird_yellow.setSize(button_width, button_height);
    }
    private void g_set_button_listeners(){
        g_setup_utility_listener(g_back_button, g_level_screen);
        g_setup_utility_listener(g_pause_button, g_pause_screen);
        g_setup_bird_listener(g_bird_big, "big bird");
        g_setup_bird_listener(g_bird_black, "black bird");  
        g_setup_bird_listener(g_bird_red, "red bird");
        g_setup_bird_listener(g_bird_yellow, "yellow bird");

    }
    private void g_setup_utility_listener(ImageButton button, Screen target_screen){
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                orginal_game_variable.switch_screen(target_screen);
            }
        });
    }
    private void g_setup_bird_listener(ImageButton bird, String bird_name){
        bird.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.log("Bird", bird_name);
                // change the action.
            }
        });
    }
    private void g_set_button_positions(Table g_table){
        g_table.add(g_back_button).expandX().top().left();
        g_table.add(g_pause_button).expandX().top().right();
        g_table.row();
        g_table.add(g_catapult).expandX().bottom().left().pad(10);
        g_table.row();
        g_table.add(g_bird_big).expandX().bottom().left();
        g_table.add(g_bird_black).expandX().bottom().left().pad(10);
        g_table.add(g_bird_red).expandX().bottom().left().pad(20);
        g_table.add(g_bird_yellow).expandX().bottom().left().pad(30);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(g_stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.CYAN);

        g_stage.getBatch().begin();

        g_stage.getBatch().draw(g_background, 0, 0, 800, 600);

        g_stage.getBatch().end();
        
        // g_stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        g_stage.act(delta);
        g_stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        g_viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void hide() {
        g_stage.clear();
    }

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        g_stage.dispose();
        // g_batch.dispose(); // idk if this is still needed.
        g_background.dispose();
        g_unload_asset_manager();
    }
    private void g_unload_asset_manager(){
        g_asset_manager.unload("game_screen.png");
        g_asset_manager.unload("back.png");
        g_asset_manager.unload("pause.png");
        g_asset_manager.unload("Birdimages/bigbird.png");
        g_asset_manager.unload("Birdimages/blackbird.png");
        g_asset_manager.unload("Birdimages/redbird.png");
        g_asset_manager.unload("Birdimages/yellowbird.png");
        g_asset_manager.unload("Catapult.png");
    }
}
