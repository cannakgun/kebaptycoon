package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.model.entities.Advertisement;
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
            int col = (touchPositionX - 400) / 270;

            if(col > 3) return;
            if(col < 0) return;

            int row = (765 - touchPositionY) / 140;

            if(row > 2) return;
            if(row < 0) return;


            if(col < 3) {
                advertisementMenu.setOption(col, row);
            }
            else if(row == 2){
                Advertisement newAd = advertisementMenu.constructAdvertisement();

                if (newAd == null) return;

                //TODO: AdvertManagera ekle, menüden çık
            }
        }
        else{
            dispose();
        }
    }
}
