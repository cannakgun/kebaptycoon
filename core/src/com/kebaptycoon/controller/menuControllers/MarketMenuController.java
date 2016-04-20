package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.view.menus.MarketMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class MarketMenuController extends MenuController {

    private MarketMenu marketMenu;
    public MarketMenuController(GameScreen gameScreen, MarketMenu marketMenu) {
        super(gameScreen);
        this.marketMenu = marketMenu;
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

        if(touchPositionX > 297 && touchPositionX < 388 && touchPositionY > 440 && touchPositionY < 770)
            marketMenu.changeCurrentPage(-1);
        else if(touchPositionX > 1500 && touchPositionX < 1590 && touchPositionY > 440 && touchPositionY < 770)
            marketMenu.changeCurrentPage(1);
        else if(touchPositionX >= 300 && touchPositionX <= 1588 && touchPositionY >= 300 && touchPositionY <= 965){

        }
        else{
            gameScreen.getGameScreenController().getMenuStack().pop();
            if(gameScreen.getGameScreenController().getMenuStack().isEmpty()) {
                gameScreen.resetController();
                gameScreen.getGameScreenController()
                        .processTouch(new Vector2(touchPositionX, touchPositionY));
            }
            else {
                gameScreen.setInputProcessor(gameScreen.getGameScreenController().getMenuStack()
                        .peek().getMenuController());
            }
        }
    }
}
