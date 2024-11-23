package com.badlogic.drop;

import com.badlogic.drop.screens.LoadingScreen;
import com.badlogic.drop.screens.Play;
import com.badlogic.drop.screens.SettingsScreen;
import com.badlogic.drop.screens.game_screen;
import com.badlogic.drop.screens.level_screen;
import com.badlogic.drop.screens.lose_screen;
import com.badlogic.drop.screens.pause_screen;
import com.badlogic.drop.screens.win_screen;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MyGame extends Game {
    public LoadingScreen loadingScreen;
    public level_screen level_screen;
    public pause_screen pause_screen;
    public SettingsScreen settingsScreen;
    public game_screen game_screen;
    public win_screen win_screen;
    public lose_screen lose_screen;
    public AssetManager assetManager;
    public Music bkgmusic;
    public Sound clicksound;
    public Play play;
    public User current_user;
    // protected FirstScreen firstScreen;
    // protected level_screen pause_screen;
    // protected WinScreen win_screen;
    // protected LoseScreen lose_screen;

    public MyGame(User current_user) {
        this.current_user = current_user;
    }
    @Override
    public void create() {
        assetManager = new AssetManager();

        // Create instances of your screens
        loadingScreen = new LoadingScreen(this, assetManager);
        // firstScreen = new FirstScreen(this);
        level_screen = new level_screen(this);
        settingsScreen = new SettingsScreen(this);
        game_screen = new game_screen(this);
        // pause_screen = level_screen;
        pause_screen = new pause_screen(this);
        play = new Play(this);
        bkgmusic = Gdx.audio.newMusic(Gdx.files.internal("angry_birds.mp3"));
        bkgmusic.setLooping(true);
        bkgmusic.setVolume(0.2f);
        bkgmusic.play();
        // setScreen(loadingScreen);
        // setScreen(level_screen);
        clicksound = Gdx.audio.newSound(Gdx.files.internal("clicksound.mp3"));
        // win_screen = new WinScreen(this);
        win_screen = new win_screen(this);
        // lose_screen = new LoseScreen(this);
        lose_screen = new lose_screen(this);
        switch_screen(game_screen);
    }

    public void switch_screen(Screen target_screen) {
        // setScreen(firstScreen);
        // setScreen(level_screen);
        setScreen(target_screen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (loadingScreen != null) loadingScreen.dispose();
        // if (firstScreen != null) firstScreen.dispose();
        if (level_screen != null) level_screen.dispose();
        if(settingsScreen != null) settingsScreen.dispose();
        assetManager.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }
}
