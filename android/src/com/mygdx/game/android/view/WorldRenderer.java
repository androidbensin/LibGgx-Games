package com.mygdx.game.android.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.android.model.Bensin;
import com.mygdx.game.android.model.Block;
import com.mygdx.game.android.model.World;

/**
 * Created by salall on 9/6/14.
 */
public class WorldRenderer {

    private static final float CAMERA_WIDTH = 40f;
    private static final float CAMERA_HEIGHT = 7f;
    private static final float RUNNING_FRAME_DURATION = 0.06f;


    private World world;
    private OrthographicCamera camera;

    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Textures **/
    private TextureRegion bensinIdleLeft;
    private TextureRegion bensinIdleRight;
    private TextureRegion blockTexture;
    private TextureRegion bensinFrame;

    /** Animations **/
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;

    private SpriteBatch spriteBatch;
    private boolean debug = true;
    private int width;
    private int height;
    private float ppuX;
    private float ppuY;




    public void setSize(int width , int height) {
        this.width = width;
        this.height = height;
        ppuX = (float)width / 10f;
        ppuY = (float)height / CAMERA_HEIGHT;
    }

    public WorldRenderer(World world , boolean debug) {
        this.world = world;
        this.camera = new OrthographicCamera(this.width, this.height);
        this.camera.position.set(world.getBensin().getPosition().x / 2f, CAMERA_HEIGHT/2 , 0);
        this.camera.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
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


    private void drawBlocks() {
        for(Block block : world.getBlocks()) {
            spriteBatch.draw(blockTexture , block.getPosition().x * ppuX , block.getPosition().y * ppuY , Block.SIZE * ppuX , Block.SIZE  * ppuY);
        }
    }

    private void drawBensin() {
        Bensin bensin = world.getBensin();
        bensinFrame = bensin.isFacingLeft() ? bensinIdleLeft : bensinIdleRight;
        if(bensin.getState().equals(Bensin.State.WALKING)) {
            bensinFrame= bensin.isFacingLeft() ? walkLeftAnimation.getKeyFrame(bensin.getStateTime() , true) :
                    walkRightAnimation.getKeyFrame(bensin.getStateTime() , true);
        }
        spriteBatch.draw(bensinFrame , bensin.getPosition().x * ppuX , bensin.getPosition().y * ppuY , Block.SIZE * ppuX , Block.SIZE  * ppuY);
    }

    public void render() {
        moveCamera(world.getBensin().getPosition().x);
        spriteBatch.begin();
        drawBlocks();
        drawBensin();
        spriteBatch.end();
    }

    public void moveCamera(float x){
        this.camera.position.set(x,this.camera.position.y, 0);
        this.camera.update();
    }

    public void renderDebug() {
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Block block : world.getBlocks()) {
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x + rect.x;
            float y1 = block.getPosition().y + rect.y;
            debugRenderer.setColor(new Color(1, 0 , 0 , 1));
            debugRenderer.rect(x1, y1 , rect.width , rect.height);
        }

        Bensin bensin = world.getBensin();
        Rectangle rect = bensin.getBounds();
        float x1 = bensin.getPosition().x + rect.x;
        float y1 = bensin.getPosition().y + rect.y;
        debugRenderer.setColor(Color.GREEN);
        debugRenderer.rect(x1, y1 , rect.width , rect.height);
        debugRenderer.end();
    }
}
