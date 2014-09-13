package com.mygdx.game.android.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;

/**
 * Created by salall on 9/6/14.
 */
public class World {

    private Array<Block> blocks = new Array<Block>();
    Bensin bensin;

    public Array<Block> getBlocks() {
        return blocks;
    }

    public Bensin getBensin() {
        return bensin;
    }

    public World() {
        createBensinWorld();
    }

    private void createBensinWorld() {
        bensin = new Bensin(new Vector2(1, 1));
        for (int i = 0; i < 20 ; i++) {
            blocks.add(new Block(new Vector2(i, 0)));
            blocks.add(new Block(new Vector2(i, 6)));
            /*if(i > 2) {
                blocks.add(new Block(new Vector2(i, 1)));
            }*/
        }

        blocks.add(new Block(new Vector2(0, 1)));
        blocks.add(new Block(new Vector2(0, 2)));
        blocks.add(new Block(new Vector2(0, 3)));
        blocks.add(new Block(new Vector2(0, 4)));
        blocks.add(new Block(new Vector2(0, 5)));

        /*blocks.add(new Block(new Vector2(6, 3)));
        blocks.add(new Block(new Vector2(6, 4)));
        blocks.add(new Block(new Vector2(6, 5)));*/
    }
}
