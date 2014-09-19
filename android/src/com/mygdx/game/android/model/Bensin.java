package com.mygdx.game.android.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by salall on 9/6/14.
 */
public class Bensin {
    public enum State {IDLE , WALKING, JUMPING, DYING}

    public static final float SPEED = 4f;
    public static final float JUMP_VELOCITY = 4f;
    public static final float SIZE = 1f;

    /** Bensin's attributes */
    Vector2 position = new Vector2(); // Bensin's  position in the world. This is expressed in world coordinates
    Vector2 acceleration = new Vector2(); // acceleration when Bensin jumps
    Vector2 velocity = new Vector2(); // Will be calculated and used for moving Bensin around
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = false;
    float stateTime = 0;

    public Bensin(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }


    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public State getState() {
        return state;
    }

    public void setState(State newState) {
        this.state = newState;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setX(position.x);
        this.bounds.setY(position.y);
    }


    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }


    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }


    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        bounds.x = position.x;
        bounds.y = position.y;
        stateTime += delta;
    }
}
