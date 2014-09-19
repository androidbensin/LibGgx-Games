package com.mygdx.game.android.controller;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.android.model.Bensin;
import com.mygdx.game.android.model.Bensin.State;
import com.mygdx.game.android.model.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by salall on 9/6/14.
 */
public class BensinController {
    enum KeyStates {
        LEFT , RIGHT , JUMP , FIRE
    }

    private static final long LONG_JUMP_PRESS 	= 150l;
    private static final float ACCELERATION 	= 20f;
    private static final float GRAVITY 			= -20f;
    private static final float MAX_JUMP_SPEED	= 7f;
    private static final float DAMP 			= 0.90f;
    private static final float MAX_VEL 			= 4f;
    private static final float WIDTH = 50f;
    private World world;
    private Bensin bensin;
    private long	jumpPressedTime;
    private boolean jumpingPressed;
    private boolean grounded = false;

    static Map<KeyStates , Boolean> keys = new HashMap<KeyStates, Boolean>();

    static {
        keys.put(KeyStates.LEFT , false);
        keys.put(KeyStates.RIGHT, false);
        keys.put(KeyStates.JUMP , false);
        keys.put(KeyStates.FIRE , false);
    }

    public BensinController(World world) {
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
        jumpingPressed = false;
    }

    public void fireReleased() {
        keys.get(keys.put(KeyStates.FIRE, false));
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();

        // If bensin is grounded then reset the state to IDLE
        /*if (grounded && bensin.getState().equals(State.JUMPING)) {
            bensin.setState(State.IDLE);
        }

        // Setting initial vertical acceleration
        bensin.getAcceleration().y = GRAVITY;

        // Convert acceleration to frame time
        bensin.getAcceleration().scl(delta);

        // apply acceleration to change velocity
        bensin.getVelocity().add(bensin.getAcceleration().x, bensin.getAcceleration().y);

        // checking collisions with the surrounding blocks depending on bensin's velocity
        //checkCollisionWithBlocks(delta);

        // apply damping to halt bensin nicely
        bensin.getVelocity().x *= DAMP;

        // ensure terminal velocity is not exceeded
        if (bensin.getVelocity().x > MAX_VEL) {
            bensin.getVelocity().x = MAX_VEL;
        }
        if (bensin.getVelocity().x < -MAX_VEL) {
            bensin.getVelocity().x = -MAX_VEL;
        }

        // simply updates the state time
        bensin.update(delta);*/

        bensin.getAcceleration().y = GRAVITY;
        bensin.getAcceleration().scl(delta);
        bensin.getVelocity().add(bensin.getAcceleration().x, bensin.getAcceleration().y);
        if (bensin.getAcceleration().x == 0) bensin.getVelocity().x *= DAMP;
        if (bensin.getVelocity().x > MAX_VEL) {
            bensin.getVelocity().x = MAX_VEL;
        }
        if (bensin.getVelocity().x < -MAX_VEL) {
            bensin.getVelocity().x = -MAX_VEL;
        }

        bensin.update(delta);
        if (bensin.getPosition().y < 0) {
            bensin.getPosition().y = 0f;
            bensin.setPosition(bensin.getPosition());
            if (bensin.getState().equals(Bensin.State.JUMPING)) {
                bensin.setState(State.IDLE);
            }
        }
        if (bensin.getPosition().x < 0) {
            bensin.getPosition().x = 0;
            bensin.setPosition(bensin.getPosition());
            if (!bensin.getState().equals(State.JUMPING)) {
                bensin.setState(State.IDLE);
            }
        }
        if (bensin.getPosition().x > WIDTH - bensin.getBounds().width ) {
            bensin.getPosition().x = WIDTH - bensin.getBounds().width;
            bensin.setPosition(bensin.getPosition());
            if (!bensin.getState().equals(State.JUMPING)) {
                bensin.setState(State.IDLE);
            }
        }

    }

    /** Change bensin's state and parameters based on input controls **/
    private void processInput() {
        if (keys.get(KeyStates.JUMP)) {
            if (!bensin.getState().equals(State.JUMPING)) {
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                bensin.setState(State.JUMPING);
                bensin.getVelocity().y = MAX_JUMP_SPEED;
                grounded = false;
            } else {
                if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
                    jumpingPressed = false;
                } else {
                    if (jumpingPressed) {
                        bensin.getVelocity().y = MAX_JUMP_SPEED;
                    }
                }
            }
        }
        if (keys.get(KeyStates.LEFT)) {
            // left is pressed
            bensin.setFacingLeft(true);
            if (!bensin.getState().equals(State.JUMPING)) {
                bensin.setState(State.WALKING);
            }
            bensin.getAcceleration().x = -ACCELERATION;
        } else if (keys.get(KeyStates.RIGHT)) {
            // left is pressed
            bensin.setFacingLeft(false);
            if (!bensin.getState().equals(State.JUMPING)) {
                bensin.setState(State.WALKING);
            }
            bensin.getAcceleration().x = ACCELERATION;
        } else {
            if (!bensin.getState().equals(State.JUMPING)) {
                bensin.setState(State.IDLE);
            }
            bensin.getAcceleration().x = 0;

        }
    }

}
