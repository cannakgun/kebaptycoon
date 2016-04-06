package com.kebaptycoon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kebaptycoon.KebapTycoonGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Kebap Tycoon";
		config.width = 1920;
		config.height = 1080;
        KebapTycoonGame.setFacebookLoginHelper(new DesktopFacebook());
		new LwjglApplication(KebapTycoonGame.getInstance(), config);
	}
}
