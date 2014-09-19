package com.mygdx.game.android.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by salall on 9/15/14.
 */
public class Player extends Sprite{

    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2 , gravity = 60 * 1.8f;

    public Player(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void draw(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    private void update(float deltaTime) {
        velocity.y -= gravity * deltaTime;
        // clamp velocity
        if(velocity.y > speed)
            velocity.y = speed;
        else if(velocity.y < speed)
            velocity.y = -speed;

        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);
    }


}
