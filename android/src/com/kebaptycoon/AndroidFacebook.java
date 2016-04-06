package com.kebaptycoon;

import android.content.Context;
import android.content.Intent;

import com.kebaptycoon.AndroidFacebookActivity;
import com.kebaptycoon.util.FacebookLoginHelper;

/**
 * Created by Dogancan on 18/12/15.
 */
public class AndroidFacebook extends FacebookLoginHelper {
    Context context;
    public AndroidFacebook(Context context){
        this.context = context;
    }

    @Override
    public void connectFacebook() {
        Intent i = new Intent(context, AndroidFacebookActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
