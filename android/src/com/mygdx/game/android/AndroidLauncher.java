package com.mygdx.game.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.android.screens.StarAssault;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.libgdx_activity)
public class AndroidLauncher extends AndroidApplication {

    @ViewById(R.id.gameLayout)
    RelativeLayout gameLayout;

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
        View gameView = initializeForView(new StarAssault(), config);
        gameLayout.addView(gameView);
    }
}
