package com.kebaptycoon;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kebaptycoon.KebapTycoonGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        KebapTycoonGame.setFacebookLoginHelper(new AndroidFacebook(getApplicationContext()));
		initialize(KebapTycoonGame.getInstance(), config);	
	}
}
