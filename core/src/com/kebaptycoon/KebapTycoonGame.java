package com.kebaptycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kebaptycoon.util.FacebookLoginHelper;
import com.kebaptycoon.view.screens.LoginScreen;
import com.badlogic.gdx.Preferences;

public class KebapTycoonGame extends Game {

    public static KebapTycoonGame instance;
    Preferences prefs;
    FacebookLoginHelper facebookLoginHelper;

    public KebapTycoonGame(FacebookLoginHelper facebookLoginHelper){
        this.facebookLoginHelper = facebookLoginHelper;
    }

	@Override
	public void create () {
        instance = this;
        prefs = Gdx.app.getPreferences("Kebap Tycoon Preferences");

        if(prefs.getString("facebook_access_token") == null || prefs.getString("facebook_access_token").equals(""))
            facebookLoginHelper.connectFacebook();
        else
            System.out.println("token is already stored in prefs: " + prefs.getString("facebook_access_token"));

        this.setScreen(new LoginScreen());
	}

	@Override
	public void render () {
        super.render();
	}

    public static KebapTycoonGame getInstance(){
        return instance;
    }
}
