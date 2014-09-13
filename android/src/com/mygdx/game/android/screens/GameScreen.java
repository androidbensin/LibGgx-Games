package com.mygdx.game.android.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.mygdx.game.android.controller.BensinController;
import com.mygdx.game.android.controller.WorldController;
import com.mygdx.game.android.model.World;
import com.mygdx.game.android.view.WorldRenderer;

import javax.microedition.khronos.opengles.GL10;


/**
 * Created by salall on 9/6/14.
 */
public class GameScreen implements Screen , InputProcessor{

    private World world;
    private WorldRenderer worldRenderer;
    private BensinController controller;

    private int width , height;

    private long lastPressedTime;
    private boolean longPressed;
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f , 0.1f , 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        worldRenderer.setSize(width , height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void show() {
        world = new World();
        worldRenderer = new WorldRenderer(world , true);
        controller = new BensinController(world);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT)
            controller.leftPressed();
        if (keycode == Keys.RIGHT)
            controller.rightPressed();
        if (keycode == Keys.Z)
            controller.jumpPressed();
        if (keycode == Keys.X)
            controller.firePressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.LEFT)
            controller.leftReleased();
        if (keycode == Keys.RIGHT)
            controller.rightReleased();
        if (keycode == Keys.Z)
            controller.jumpReleased();
        if (keycode == Keys.X)
            controller.fireReleased();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android))
            return false;
        //if(System.currentTimeMillis() - lastPressedTime > 1000){

       // }

        if (x < width / 2 && y > height / 2) {
            controller.leftPressed();
        }
        if (x > width / 2 && y > height / 2) {
            controller.rightPressed();
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android))
            return false;
        if (x < width / 2 && y > height / 2) {
            controller.leftReleased();
        }
        if (x > width / 2 && y > height / 2) {
            controller.rightReleased();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        // TODO Auto-generated method stub
        if(longPressed) {
            controller.jumpReleased();
            longPressed = false;
        }
        else {
            longPressed = true;
            controller.jumpPressed();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}
