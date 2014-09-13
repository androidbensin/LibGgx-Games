package com.mygdx.game.android.controller;

import com.mygdx.game.android.model.Bensin;
import com.mygdx.game.android.model.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by salall on 9/6/14.
 */
public class WorldController {
    enum KeyStates {
        LEFT , RIGHT , JUMP , FIRE
    }

    private World world;
    private Bensin bensin;

    static Map<KeyStates , Boolean> keys = new HashMap<KeyStates, Boolean>();

    static {
        keys.put(KeyStates.LEFT , false);
        keys.put(KeyStates.RIGHT, false);
        keys.put(KeyStates.JUMP , false);
        keys.put(KeyStates.FIRE , false);
    }

    public WorldController(World world) {
        this.world = world;
        this.bensin = world.getBensin();
    }

    public void leftPressed() {
        keys.get(keys.put(KeyStates.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(KeyStates.RIGHT, true));
    }

    public void jumpPressed() {
        keys.get(keys.put(KeyStates.JUMP, true));
    }

    public void firePressed() {
        keys.get(keys.put(KeyStates.FIRE, false));
    }

    public void leftReleased() {
        keys.get(keys.put(KeyStates.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(KeyStates.RIGHT, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(KeyStates.JUMP, false));
    }

    public void fireReleased() {
        keys.get(keys.put(KeyStates.FIRE, false));
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();
        bensin.update(delta);
    }

    /** Change Bob's state and parameters based on input controls **/
    private void processInput() {
        if (keys.get(KeyStates.LEFT)) {
            // left is pressed
            bensin.setFacingLeft(true);
            bensin.setState(Bensin.State.WALKING);
            bensin.getVelocity().x = -Bensin.SPEED;
        }
        if (keys.get(KeyStates.RIGHT)) {
            // left is pressed
            bensin.setFacingLeft(false);
            bensin.setState(Bensin.State.WALKING);
            bensin.getVelocity().x = Bensin.SPEED;
        }
        // need to check if both or none direction are pressed, then Bob is idle
        if ((keys.get(KeyStates.LEFT) && keys.get(KeyStates.RIGHT)) ||
                (!keys.get(KeyStates.LEFT) && !(keys.get(KeyStates.RIGHT)))) {
            bensin.setState(Bensin.State.IDLE);
            // acceleration is 0 on the x
            bensin.getAcceleration().x = 0;
            // horizontal speed is 0
            bensin.getVelocity().x = 0;
        }
    }

}
