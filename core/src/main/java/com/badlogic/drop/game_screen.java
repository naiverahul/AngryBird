
package com.badlogic.drop;

import Birds.Birdparentclass;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import java.util.LinkedList;
import java.util.Queue;

public class game_screen implements Screen {
    private final MyGame orginal_game_variable;
    private Stage stage;
    private FitViewport viewport;
    private Texture game_background;
    private Queue<Birdparentclass> birdQueue;
    private Birdparentclass currentBird;
    private ImageButton catapultButton;
    private ImageButton pausebutton;
    private ImageButton backbutton;

    public game_screen(MyGame game) {
        this.orginal_game_variable = game;
        this.viewport = new FitViewport(1920, 1080);
        this.stage = new Stage(viewport);
        this.game_background = new Texture(Gdx.files.internal("game_screen.png"));
        this.birdQueue = new LinkedList<>();

        initBirds();
        setupUI();

        // Start with the first bird in the queue
        this.currentBird = birdQueue.peek();
    }



    private void initBirds() {
        birdQueue.add(new Birdparentclass("bigbird","Birdimages/bigbird.png", 10, 5));
        birdQueue.add(new Birdparentclass("redbird","Birdimages/redbird.png", 8, 6));
        birdQueue.add(new Birdparentclass("yellowbird","Birdimages/yellowbird.png", 12, 7));
        birdQueue.add(new Birdparentclass("blackbird","Birdimages/blackbird.png", 15, 8));
    }

    // private void setupUI() {
    //     Table table = new Table();
    //     table.debug(Table.Debug.all);
    //     table.setFillParent(true);
    //     stage.addActor(table);

    //     this.catapultButton = createButton("catapult.png");
    //     this.pausebutton = createButton("pause.png");
    //     this.backbutton = createButton("back.png");
    //     catapultButton.addListener(new ClickListener() {
    //         @Override
    //         public void clicked(InputEvent event, float x, float y) {
    //             launchBird();
    //         }
    //     });
    //     backbutton.addListener(new ClickListener() {
    //         @Override
    //         public void clicked(InputEvent event, float x, float y) {
    //             orginal_game_variable.level_screen.dispose();
    //             level_screen new_Level_screen = new level_screen(orginal_game_variable);
    //             orginal_game_variable.level_screen = new_Level_screen;
    //             orginal_game_variable.setScreen(new_Level_screen);
    //         }
    //     });


    //     table.add(catapultButton).size(200f, 200f).expand().bottom().pad(100f).left().pad(100f);
    //     table.padBottom(50f);
    //     table.add(backbutton).size(200f, 200f).expand().top().pad(25f).left().pad(25f);
    //     table.row();
    //     table.add(pausebutton).size(200f, 200f).expand().top().pad(25f).right().pad(25f);
    // }


    private void setupUI() {
        Table table = new Table();
        table.debug(Table.Debug.all);
        table.setFillParent(true);
        stage.addActor(table);

        this.catapultButton = createButton("catapult.png");
        this.pausebutton = createButton("pause.png");
        this.backbutton = createButton("back.png");

        pausebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                orginal_game_variable.pause_screen.dispose();
                pause_screen new_Pause_screen = new pause_screen(orginal_game_variable);
                orginal_game_variable.pause_screen = new_Pause_screen;
                orginal_game_variable.setScreen(new_Pause_screen);
            }
        });

        catapultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                launchBird();
            }
        });

        backbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                orginal_game_variable.level_screen.dispose();
                level_screen new_Level_screen = new level_screen(orginal_game_variable);
                orginal_game_variable.level_screen = new_Level_screen;
                orginal_game_variable.setScreen(new_Level_screen);
            }
        });

        // Positioning buttons
        // Back button at the top-left corner
        table.add(backbutton).size(100f, 100f).expand().top().left().pad(20f);

        // Pause button at the top-right corner
        table.add(pausebutton).size(100f, 100f).top().right().pad(20f);

        table.row();

        // Catapult button at the bottom-left corner
        table.add(catapultButton).size(200f, 200f).bottom().left().pad(100f);
    }




    private ImageButton createButton(String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    private void launchBird() {
        if (!birdQueue.isEmpty()) {
            Birdparentclass bird = birdQueue.poll();
            Gdx.app.log("Launching Bird", "Launched " + bird.getName() + " with strength " + bird.getAttackingPower());
            birdQueue.add(bird); // Adds the bird back to the queue after launch
            currentBird = birdQueue.peek(); // Set the next bird to be launched
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.SKY);

        // Begin drawing
        stage.getBatch().begin();
        stage.getBatch().draw(game_background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw the current bird scaled down on the catapult
        if (currentBird != null) {
            float birdWidth = currentBird.getTexture().getWidth() * 0.5f;  // Reduce size to 50%
            float birdHeight = currentBird.getTexture().getHeight() * 0.5f; // Reduce size to 50%
            stage.getBatch().draw(currentBird.getTexture(), catapultButton.getX(), catapultButton.getY(), birdWidth, birdHeight);
        }

        stage.getBatch().end();

        // Stage needs to act and draw UI elements
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        game_background.dispose();
        for (Birdparentclass bird : birdQueue) {
            bird.dispose();
        }
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'hide'");

    }
}
