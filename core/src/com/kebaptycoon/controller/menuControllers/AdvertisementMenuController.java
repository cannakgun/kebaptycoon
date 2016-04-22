package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.view.menus.AdvertisementMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class AdvertisementMenuController extends MenuController {

    AdvertisementMenu advertisementMenu;
    public AdvertisementMenuController(GameScreen gameScreen, AdvertisementMenu advertisementMenu) {
        super(gameScreen);
        this.advertisementMenu = advertisementMenu;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 vector2 = gameScreen.menuUnproject(x, y);
        super.touchPositionX = (int)(vector2.x);
        super.touchPositionY = (int)(vector2.y);

        checkPressedPosition(touchPositionX, touchPositionY);
        return false;
    }

    @Override
    public boolean keyDown (int keycode){
        if(keycode == Input.Keys.BACK){
            checkPressedPosition(-1, -1);
        }
        return false;
    }

    public void checkPressedPosition(int touchPositionX, int touchPositionY){

        if(touchPositionX > 310 && touchPositionX < 400 && touchPositionY > 425 && touchPositionY < 700)
            advertisementMenu.changeCurrentPage(-1);
        else if(touchPositionX > 1500 && touchPositionX < 1580 && touchPositionY > 425 && touchPositionY < 700)
            advertisementMenu.changeCurrentPage(1);
        else if(touchPositionX >= 400 && touchPositionX <= 1500 && touchPositionY >= 300 && touchPositionY <= 840){

        }
        else{
            dispose();
        }
    }
}
