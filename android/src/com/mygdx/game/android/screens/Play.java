package com.mygdx.game.android.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.android.controller.BensinController;
import com.mygdx.game.android.model.Bensin;
import com.mygdx.game.android.model.Block;
import com.mygdx.game.android.model.World;

/**
 * Created by salall on 9/15/14.
 */
public class Play implements Screen , InputProcessor {

    private World world;
    private BensinController controller;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;

    private static final float RUNNING_FRAME_DURATION = 0.06f;


    /** Textures **/
    private TextureRegion bensinIdleLeft;
    private TextureRegion bensinIdleRight;
    private TextureRegion blockTexture;
    private TextureRegion bensinFrame;

    /** Animations **/
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;


    private float ppuX;
    private float ppuY;


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.update(delta);
        renderer.setView(camera);
        renderer.render();
        renderer.getSpriteBatch().begin();
        drawBensin();
        renderer.getSpriteBatch().end();
        updateCamera();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false , width, height);
        camera.update();
        ppuX = (float)width / 10f;
        ppuY = (float)height / 7f;
    }

    @Override
    public void show() {
        world = new World();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        map = new TmxMapLoader().load("block.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false , width, height);
        camera.update();
        controller = new BensinController(world);
        Gdx.input.setInputProcessor(this);
        loadTextures();
    }



    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/bensin.pack"));
        bensinIdleLeft = atlas.findRegion("bensin01");
        bensinIdleRight = new TextureRegion(bensinIdleLeft);
        bensinIdleRight.flip(true, false);
        blockTexture = atlas.findRegion("block");
        TextureRegion[] walkLeftFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            walkLeftFrames[i] = atlas.findRegion("bensin0" + (i + 2));
        }
        walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
    }


    private void drawBensin() {
        Bensin bensin = world.getBensin();
        bensinFrame = bensin.isFacingLeft() ? bensinIdleLeft : bensinIdleRight;
        if(bensin.getState().equals(Bensin.State.WALKING)) {
            bensinFrame= bensin.isFacingLeft() ? walkLeftAnimation.getKeyFrame(bensin.getStateTime() , true) :
                    walkRightAnimation.getKeyFrame(bensin.getStateTime() , true);
        }
        renderer.getSpriteBatch().draw(bensinFrame , bensin.getPosition().x * ppuX , bensin.getPosition().y * ppuY , Block.SIZE * ppuX , Block.SIZE  * ppuY);
    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.LEFT) {
            controller.leftPressed();
        }
        if (keycode == Input.Keys.RIGHT) {
            controller.rightPressed();
        }
        if (keycode == Input.Keys.Z)
            controller.jumpPressed();
        if (keycode == Input.Keys.X)
            controller.firePressed();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            controller.leftReleased();
        if (keycode == Input.Keys.RIGHT)
            controller.rightReleased();
        if (keycode == Input.Keys.Z)
            controller.jumpReleased();
        if (keycode == Input.Keys.X)
            controller.fireReleased();
        return true;
    }


    private void updateCamera(){
        camera.position.x = world.getBensin().getPosition().x * ppuX;
        camera.update();
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
