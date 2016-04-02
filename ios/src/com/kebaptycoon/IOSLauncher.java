package com.kebaptycoon;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSPropertyList;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.pods.facebook.core.FBSDKAppEvents;
import org.robovm.pods.facebook.core.FBSDKApplicationDelegate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.kebaptycoon.KebapTycoonGame;
import com.kebaptycoon.util.FacebookLoginHelper;

public class IOSLauncher extends IOSApplication.Delegate {


    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        return new IOSApplication(KebapTycoonGame.getInstance(new iOSFacebook()), config);
    }

    public static void main(String[] args) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(args, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        boolean rtn = super.didFinishLaunching(application, launchOptions);
        FBSDKApplicationDelegate.getSharedInstance().didFinishLaunching(application, launchOptions);
        return rtn;
    }

    @Override
    public boolean openURL(UIApplication application, NSURL url, String sourceApplication, NSPropertyList annotation) {
        Gdx.app.log("iosLauncher", "openURL");
        return FBSDKApplicationDelegate.getSharedInstance().openURL(application, url, sourceApplication, annotation);
    }

    @Override
    public void didBecomeActive(UIApplication application) {
        super.didBecomeActive(application);
        Gdx.app.log("iosLauncher", "didBecomeActive");
        FBSDKAppEvents.activateApp();
    }
}