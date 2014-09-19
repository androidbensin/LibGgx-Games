package com.mygdx.game.android.screens;

import com.badlogic.gdx.Game;

/**
 * Created by salall on 9/15/14.
 */
public class TestGame extends Game {

    private Play gameScreen;

    public TestGame(){
        gameScreen = new Play();
    }

    @Override
    public void create() {
        setScreen(gameScreen);
    }

    public void onActionPressed(int keyCode) {
        gameScreen.keyDown(keyCode);
    }

    public void onActionReleased(int keyCode) {
        gameScreen.keyUp(keyCode);
    }
}
