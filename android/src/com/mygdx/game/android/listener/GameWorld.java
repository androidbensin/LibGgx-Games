package com.mygdx.game.android.listener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by salall on 9/6/14.
 */
public class GameWorld implements ApplicationListener {

    private SpriteBatch spriteBatch;
    private BitmapFont bitmapFont;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.GREEN);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1,1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        bitmapFont.draw(spriteBatch, "NATALYA GAME", 200, 200);
        bitmapFont.setScale(5, 5);
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        bitmapFont.dispose();
    }
}
