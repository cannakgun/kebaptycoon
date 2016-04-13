package com.kebaptycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.kebaptycoon.util.FacebookLoginHelper;
import com.kebaptycoon.utils.TextureManager;
import com.kebaptycoon.view.screens.GameScreen;
import com.kebaptycoon.view.screens.SplashScreen;

public class KebapTycoonGame extends Game {

    private static KebapTycoonGame instance;
    private Preferences prefs;
    private static FacebookLoginHelper facebookLoginHelper;

    @Override
	public void create () {

        prefs = Gdx.app.getPreferences("Kebap Tycoon Preferences");

        if(prefs.getString("facebook_access_token") == null || prefs.getString("facebook_access_token").equals(""))
            facebookLoginHelper.connectFacebook();
        else
            System.out.println("token is already stored in prefs: " + prefs.getString("facebook_access_token"));

        this.setScreen(new SplashScreen());
	}


	@Override
	public void render () {
        super.render();
	}


    public static KebapTycoonGame getInstance(){
        if(instance == null)
            instance = new KebapTycoonGame();
        return instance;
    }

    public static void setFacebookLoginHelper(FacebookLoginHelper flh){
        facebookLoginHelper = flh;
    }

    public Preferences getPrefs(){
        return prefs;
    }
}
