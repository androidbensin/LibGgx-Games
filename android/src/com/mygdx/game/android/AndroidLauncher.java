package com.mygdx.game.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.android.screens.StarAssault;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
        config.useGLSurfaceView20API18 = true;
		View gameView = initializeForView(new StarAssault(), config);
        View rootLayout = LayoutInflater.from(this).inflate(R.layout.libgdx_activity, null);
        ((RelativeLayout)rootLayout.findViewById(R.id.gameLayout)).addView(gameView);
        setContentView(rootLayout);
	}
}
