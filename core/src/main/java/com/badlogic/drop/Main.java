package com.badlogic.drop;

import com.badlogic.drop.user.DataManager;
import com.badlogic.drop.user.Login_and_SignUp;
import com.badlogic.drop.user.User;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import javax.swing.*;

public class Main extends Game {
    private MyGame myGame;
    private User currentUser = null;

    @Override
    public void create() {
        User[] currentUserHolder = new User[1];
        Login_and_SignUp loginAndSignUp = new Login_and_SignUp(currentUserHolder);

        // Launch login/signup dialog on the Swing thread
        SwingUtilities.invokeLater(() -> {
            loginAndSignUp.showLoginSignupDialog();

            if (currentUserHolder[0] != null) {
                currentUser = currentUserHolder[0];
                Gdx.app.log("Main", "Game started with user: " + currentUser.getName());

                // Initialize the game on the LibGDX thread
                Gdx.app.postRunnable(() -> {
                    myGame = new MyGame(currentUser);
                    myGame.create();
                });
            } else {
                Gdx.app.log("Main", "User did not log in. Exiting the game.");
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render() {
        // Render only if the game has been initialized
        if (myGame != null) {
            myGame.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize only if the game has been initialized
        if (myGame != null) {
            myGame.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        // Dispose resources only if the game has been initialized
        if (myGame != null) {
            myGame.dispose();
        }
    }
}
