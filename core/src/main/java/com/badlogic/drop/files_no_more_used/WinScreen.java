// package com.badlogic.drop;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.assets.AssetManager;
// import com.badlogic.gdx.audio.Sound;
// import com.badlogic.gdx.graphics.Texture;
// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.badlogic.gdx.graphics.g2d.TextureRegion;
// import com.badlogic.gdx.math.Interpolation;
// import com.badlogic.gdx.scenes.scene2d.InputEvent;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.scenes.scene2d.actions.Actions;
// import com.badlogic.gdx.scenes.scene2d.ui.Image;
// import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
// import com.badlogic.gdx.scenes.scene2d.ui.Table;
// import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
// import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
// import com.badlogic.gdx.utils.viewport.FitViewport;

// public class WinScreen implements Screen {
//     private final MyGame game;
//     private Stage stage;
//     private Texture winBackground;
//     private Sound winSound;
//     private SpriteBatch batch;
//     private ImageButton backbutton;

//     public WinScreen(MyGame game) {
//         this.game = game;

//         this.stage = new Stage(new FitViewport(1920, 1080));
//         // this.winBackground = new Texture(Gdx.files.internal("win.png"));
//         this.winBackground = new Texture(Gdx.files.internal("win_screen.png"));
//         this.winSound = Gdx.audio.newSound(Gdx.files.internal("win_sound.wav"));
//         this.batch = new SpriteBatch();
//         this.backbutton = createButton("back.png");
//         // Set up the back button and position it in a table layout
//         Table table = new Table();
//         table.setFillParent(true); // Makes the table cover the entire screen

//         table.add(backbutton).size(100f, 100f).expand().top().left().pad(20f);

//         // Add table to stage to make the button visible
//         stage.addActor(table);

//         setupBackground();
//     }

//     private void setupBackground() {


//         // Set up the background win image with fade-in and scaling animations
//         Image winImage = new Image(winBackground);
//         winImage.setSize(1920, 1080);
//         winImage.setPosition(0, 0);
//         winImage.getColor().a = 0f; // Start fully transparent
//         winImage.addAction(Actions.sequence(
//             Actions.fadeIn(2f, Interpolation.fade), // Fade in over 2 seconds
//             Actions.forever(Actions.sequence(
//                 Actions.scaleTo(1.05f, 1.05f, 2f), // Scale up a bit
//                 Actions.scaleTo(1f, 1f, 2f) // Scale back down
//             ))
//         ));
//         stage.addActor(winImage);

//         // Set up the back button click listener to switch screens
//         backbutton.addListener(new ClickListener() {
//             @Override
//             public void clicked(InputEvent event, float x, float y) {
//                 AssetManager assetManager = new AssetManager();
//                 game.clicksound.play();
//                 game.pause_screen.dispose();
//                 LoadingScreen newPauseScreen = new LoadingScreen(game, assetManager);
//                 game.loadingScreen = newPauseScreen;
//                 game.setScreen(newPauseScreen);
//             }
//         });
//     }

//     private ImageButton createButton(String texturePath) {
//         Texture texture = new Texture(Gdx.files.internal(texturePath));
//         return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
//     }

//     @Override
//     public void show() {
//         Gdx.input.setInputProcessor(stage);
//         game.bkgmusic.pause();
//         winSound.play(); // Play win sound
//     }

//     @Override
//     public void render(float delta) {
//         stage.act(delta);
//         stage.draw();
//     }

//     @Override
//     public void resize(int width, int height) {
//         stage.getViewport().update(width, height, true);
//     }

//     @Override
//     public void pause() { }

//     @Override
//     public void resume() { }

//     @Override
//     public void hide() { }

//     @Override
//     public void dispose() {
//         stage.dispose();
//         winBackground.dispose();
//         winSound.dispose();
//     }
// }
