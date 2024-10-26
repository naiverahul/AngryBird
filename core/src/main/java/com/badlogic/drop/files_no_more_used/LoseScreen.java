// package com.badlogic.drop;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.assets.AssetManager;
// import com.badlogic.gdx.audio.Sound;
// import com.badlogic.gdx.graphics.Texture;
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

// public class LoseScreen implements Screen {
//     private final MyGame game;
//     private Stage stage;
//     private Texture loseBackground;
//     private Sound loseSound;
//     private ImageButton backbutton;

//     public LoseScreen(MyGame game) {
//         this.game = game;
//         this.stage = new Stage(new FitViewport(1920, 1080));
//         this.loseBackground = new Texture(Gdx.files.internal("lose.png"));
//         this.loseSound = Gdx.audio.newSound(Gdx.files.internal("lose_sound.wav"));

//         setupBackground();
//     }

//     private void setupBackground() {
//         Table table = new Table();
//         this.backbutton = createButton("back.png");
//         table.add(backbutton).size(100f, 100f).expand().top().left().pad(20f);

//         Image loseImage = new Image(loseBackground);
//         loseImage.setSize(1920, 1080);
//         loseImage.setPosition(0, 0);

//         // Add a fade-in and slight pulse animation for the lose image
//         loseImage.getColor().a = 0f;
//         loseImage.addAction(Actions.sequence(
//             Actions.fadeIn(2f, Interpolation.fade), // Fade in over 2 seconds
//             Actions.forever(Actions.sequence(
//                 Actions.scaleTo(1.05f, 1.05f, 1.5f), // Pulse effect
//                 Actions.scaleTo(1f, 1f, 1.5f)
//             ))
//         ));

//         stage.addActor(loseImage);
//         backbutton.addListener(new ClickListener() {
//             @Override
//             public void clicked(InputEvent event, float x, float y) {
//                 AssetManager assetManager  = new AssetManager();
//                 game.clicksound.play();
//                 game.pause_screen.dispose();
//                 LoadingScreen new_Pause_screen = new LoadingScreen(game,assetManager);
//                 game.loadingScreen = new_Pause_screen;
//                 game.setScreen(new_Pause_screen);
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
//         loseSound.play(); // Play lose sound
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
//         loseBackground.dispose();
//         loseSound.dispose();
//     }
// }

