//  package com.badlogic.drop;
//
//  import Birds.Birdparentclass;
//  import com.badlogic.gdx.Gdx;
//  import com.badlogic.gdx.Screen;
//  import com.badlogic.gdx.graphics.Color;
//  import com.badlogic.gdx.graphics.Texture;
//  import com.badlogic.gdx.graphics.g2d.TextureRegion;
//  import com.badlogic.gdx.scenes.scene2d.InputEvent;
//  import com.badlogic.gdx.scenes.scene2d.Stage;
//  import com.badlogic.gdx.scenes.scene2d.ui.Image;
//  import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//  import com.badlogic.gdx.scenes.scene2d.ui.Table;
//  import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//  import com.badlogic.gdx.utils.ScreenUtils;
//  import com.badlogic.gdx.utils.viewport.FitViewport;
//
//  import java.util.LinkedList;
//  import java.util.Queue;
//
//
//  public class game_screen implements Screen {
//      private MyGame orginal_game_variable;
//      private Stage g_stage;
//      private FitViewport g_viewport;
//      private Texture g_background;
//      private ImageButton g_back_button, g_pause_button, g_catapult;
//      private Image current_bird_on_catapult;
//
//      // Queue to manage the birds
//      private Queue<Birdparentclass> birdQueue;
//      private Birdparentclass currentBird;  // Track the bird on the catapult
//
//      public game_screen(MyGame game) {
//          this.orginal_game_variable = game;
//          g_viewport = new FitViewport(1920, 1080);
//          g_stage = new Stage(g_viewport);
//
//          g_background = new Texture(Gdx.files.internal("game_screen.png"));
//          birdQueue = new LinkedList<>();
//
//          loadBirds();  // Load birds into the queue
//          g_create_ui();
//      }
//
//      // Load birds into the queue
//      private void loadBirds() {
//          birdQueue.add(new Birdparentclass("Big Bird", "Birdimages/bigbird.png", 1, 100));
//          birdQueue.add(new Birdparentclass("Black Bird", "Birdimages/blackbird.png", 1, 120));
//          birdQueue.add(new Birdparentclass("Red Bird", "Birdimages/redbird.png", 1, 80));
//          birdQueue.add(new Birdparentclass("Yellow Bird", "Birdimages/yellowbird.png", 1, 90));
//      }
//
//      private void g_create_ui() {
//          Table g_table = g_initialize_table(true);
//
//          // Create the catapult button and set its listener
//          g_catapult = g_create_button("Catapult.png");
//          g_catapult.addListener(new ClickListener() {
//              @Override
//              public void clicked(InputEvent event, float x, float y) {
//                  switchToNextBird();  // Switch to the next bird on click
//              }
//          });
//
//          g_back_button = g_create_button("back.png");
//          g_pause_button = g_create_button("pause.png");
//
//          g_set_button_listeners();
//          g_set_button_positions(g_table);
//          place_bird_on_catapult();  // Place the first bird on the catapult
//      }
//
//      private Table g_initialize_table(boolean fill_parent) {
//          Table table = new Table();
//          table.setFillParent(fill_parent);
//          g_stage.addActor(table);
//          table.top();
//          return table;
//      }
//
//      private ImageButton g_create_button(String button_path) {
//          Texture button_texture = new Texture(Gdx.files.internal(button_path));
//
//          TextureRegion button_texture_region = new TextureRegion(button_texture);
//          ImageButton button = new ImageButton(new Image(button_texture_region).getDrawable());
//
//          return button;
//      }
//
//
//      private void g_set_button_listeners() {
//          g_back_button.addListener(new ClickListener() {
//              @Override
//              public void clicked(InputEvent event, float x, float y) {
//                  orginal_game_variable.switch_screen(orginal_game_variable.level_screen);
//              }
//          });
//
//          g_pause_button.addListener(new ClickListener() {
//              @Override
//              public void clicked(InputEvent event, float x, float y) {
//                  orginal_game_variable.switch_screen(orginal_game_variable.pause_screen);
//              }
//          });
//      }
//
//      private void g_set_button_positions(Table g_table) {
//          g_table.add(g_back_button).size(200f,200f).expandX().top().left();
//          g_table.add(g_pause_button).size(200f,200f).expandX().top().right();
//          g_table.row();
//          g_table.row();
//          g_table.row();
//          g_table.add(g_catapult).size(400f,400f).expandX().bottom().left();
//      }
//
//      // Place the first bird from the queue on the catapult
//      private void place_bird_on_catapult() {
//          if (current_bird_on_catapult != null) {
//              g_stage.getActors().removeValue(current_bird_on_catapult, true);
//          }
//
//          currentBird = birdQueue.poll();  // Get the next bird from the queue
//          if (currentBird == null) {
//              Gdx.app.log("BirdQueue", "No more birds in the queue");
//              return;
//          }
//
//          current_bird_on_catapult = new Image(new TextureRegion(currentBird.getTexture()));
//
//          float catapultX = g_catapult.getX();
//          float catapultY = g_catapult.getY() + g_catapult.getHeight();
//          current_bird_on_catapult.setPosition(catapultX, catapultY);
//
//          g_stage.addActor(current_bird_on_catapult);
//          Gdx.app.log("Bird Placement", "Placed " + currentBird.getName() + " on the catapult");
//      }
//
//      // Switch to the next bird when the catapult is clicked
//      private void switchToNextBird() {
//          // Place the current bird back into the queue
//          if (currentBird != null) {
//              birdQueue.add(currentBird);
//          }
//
//          // Place the next bird on the catapult
//          place_bird_on_catapult();
//      }
//
//      @Override
//      public void show() {
//          Gdx.input.setInputProcessor(g_stage);
//      }
//
//      @Override
//      public void render(float delta) {
//          ScreenUtils.clear(Color.CYAN);
//
//          g_stage.getBatch().begin();
//          g_stage.getBatch().draw(g_background, 0, 0, g_viewport.getWorldWidth(), g_viewport.getWorldHeight());
//          g_stage.getBatch().end();
//
//          g_stage.act(delta);
//          g_stage.draw();
//      }
//
//      @Override
//      public void resize(int width, int height) {
//          g_viewport.update(width, height, true);
//      }
//
//      @Override
//      public void dispose() {
//          g_stage.dispose();
//          g_background.dispose();
//          disposeBirds();  // Dispose bird textures
//      }
//
//      // Dispose all bird textures in the queue
//      private void disposeBirds() {
//          for (Birdparentclass bird : birdQueue) {
//              bird.dispose();
//          }
//      }
//
//      @Override
//      public void pause() {}
//
//      @Override
//      public void hide() {
//          g_stage.clear();
//      }
//
//      @Override
//      public void resume() {}
//  }
