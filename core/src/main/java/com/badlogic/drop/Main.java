package com.badlogic.drop;

import com.badlogic.drop.user.DataManager;
import com.badlogic.drop.user.Login_and_SignUp;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import javax.swing.*;

public class Main extends Game {
    private MyGame myGame;
    private User currentUser=null;

    @Override
    public void create() {
        // User[] currentUserHolder = new User[1];
        // Login_and_SignUp loginAndSignUp = new Login_and_SignUp(currentUserHolder);

        // SwingUtilities.invokeLater(loginAndSignUp::showLoginSignupDialog);

        // // Wait for the user to complete login/signup before starting the game
        // while (currentUserHolder[0] == null) {
        //     try {
        //         Thread.sleep(100); // Sleep for a short period to avoid busy-waiting
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // }

        // this.currentUser = currentUserHolder[0];
        // Gdx.app.log("Main", "Game started with user: " + currentUser.getName());

        myGame = new MyGame(currentUser);
        myGame.create();
    }

    @Override
    public void render() {
        myGame.render();
    }

    @Override
    public void dispose() {
        myGame.dispose();
    }

    @Override
    public void resize(int width, int height) {
        myGame.resize(width, height);
    }
}
