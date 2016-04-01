package com.kebaptycoon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Dogancan on 18/12/15.
 */
public class AndroidFacebookActivity extends Activity{
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.kebaptycoon",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {}*/

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.layout_android_facebook_activity);
        final Preferences prefs = Gdx.app.getPreferences("Kebap Tycoon Preferences");

        loginButton = (LoginButton)findViewById(R.id.login_button);
        Gdx.app.log("prefs facebook access token", prefs.getString("facebook_access_token"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                prefs.putString("facebook_access_token", loginResult.getAccessToken().getToken());
                prefs.flush();
                System.out.println("token saved: " + prefs.getString("facebook_access_token"));
                Toast.makeText(getApplicationContext(), prefs.getString("facebook_access_token"), Toast.LENGTH_LONG).show();

                finish();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "Facebook error occured. Please try again later.",
                        Toast.LENGTH_LONG).show();
                Gdx.app.log("ERROR: ", e.getMessage());
                finish();
            }
        });

        loginButton.performClick();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}