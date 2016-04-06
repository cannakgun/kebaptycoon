package com.kebaptycoon.util;

import com.badlogic.gdx.Preferences;
import com.kebaptycoon.KebapTycoonGame;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public abstract class FacebookLoginHelper {

    public abstract void connectFacebook();

    public void disconnectFacebook(){
        Preferences prefs = KebapTycoonGame.getInstance().getPrefs();
        prefs.putString("facebook_user_id", "");
        prefs.putString("facebook_access_token", "");
        prefs.flush();
    }
    public boolean isConnectedFacebook(){
        Preferences prefs = KebapTycoonGame.getInstance().getPrefs();
        if(prefs.getString("facebook_access_token") == null || prefs.getString("facebook_access_token").equals(""))
            return false;
        return true;
    }

}
