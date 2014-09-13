package com.mygdx.game.android;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.android.screens.StarAssault;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.libgdx_activity)
public class AndroidLauncher extends AndroidApplication implements View.OnTouchListener{

    @ViewById(R.id.gameLayout)
    RelativeLayout gameLayout;

    @ViewById(R.id.leftButton)
    Button leftButton;

    @ViewById(R.id.jumpButton)
    Button jumpButton;

    @ViewById(R.id.rightButton)
    Button rightButton;

    private StarAssault gameApp;

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

    @AfterViews
    public void onSetup(){

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
        config.useGLSurfaceView20API18 = true;
        gameApp = new StarAssault();
        View gameView = initializeForView(gameApp, config);
        gameLayout.addView(gameView);

        leftButton.setOnTouchListener(this);
        jumpButton.setOnTouchListener(this);
        rightButton.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        int action = event.getAction();
        int keyCode;
        if(id == leftButton.getId()) {
            keyCode = Input.Keys.LEFT;
        }else if(id == jumpButton.getId()) {
            keyCode = Input.Keys.Z;
        }else if(id == rightButton.getId()) {
            keyCode = Input.Keys.RIGHT;
        }else {
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                gameApp.onActionPressed(keyCode);
                return true;
            case MotionEvent.ACTION_UP:
                gameApp.onActionReleased(keyCode);
                return true;
            default:
                break;
        }
        return false;
    }
}
