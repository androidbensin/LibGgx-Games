package com.mygdx.game.android.screens;

import com.badlogic.gdx.Game;

/**
 * Created by salall on 9/6/14.
 */
public class StarAssault extends Game{

    GameScreen gameScreen = null;
    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

    public void onActionPressed(int keyCode) {
        gameScreen.keyDown(keyCode);
    }

    public void onActionReleased(int keyCode) {
        gameScreen.keyUp(keyCode);
    }
}
