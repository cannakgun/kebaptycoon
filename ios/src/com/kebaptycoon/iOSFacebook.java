package com.kebaptycoon;

import com.badlogic.gdx.Gdx;
import com.kebaptycoon.util.FacebookLoginHelper;

import org.robovm.pods.facebook.core.FBSDKAccessToken;
import org.robovm.pods.facebook.core.FBSDKProfile;

import java.util.Arrays;

public class iOSFacebook extends FacebookLoginHelper{
    @Override
    public void connectFacebook() {
        Gdx.app.log("iosFacebook", "ios Facebook login here!");

        FacebookHandler.getInstance().logIn(Arrays.asList("email", "user_friends"),
            new FacebookHandler.LoginListener() {
                @Override
                public void onSuccess() {

                    Gdx.app.log("iOSFacebook Access Token: ", FBSDKAccessToken.getCurrentAccessToken().getTokenString());
                    Gdx.app.log("iOSFacebook FB ID: ", FBSDKAccessToken.getCurrentAccessToken().getUserID());
                    KebapTycoonGame.getInstance().getPrefs().putString("facebook_access_token"
                            , FBSDKAccessToken.getCurrentAccessToken().getTokenString());
                    KebapTycoonGame.getInstance().getPrefs().putString("facebook_user_id",
                            FBSDKAccessToken.getCurrentAccessToken().getUserID());
                }

                @Override
                public void onError(String message) {
                    FacebookHandler.getInstance().alertError("Error during login!", message);
                }

                @Override
                public void onCancel() {
                    // User cancelled, so do nothing.
                }
            });
    }
}