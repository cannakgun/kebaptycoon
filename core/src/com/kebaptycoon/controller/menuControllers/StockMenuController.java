package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import com.kebaptycoon.view.menus.StockMenu;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 20.4.2016.
 */
public class StockMenuController extends MenuController {

    private GameScreen gameScreen;
    private StockMenu stockMenu;

    public StockMenuController(GameScreen gameScreen, StockMenu stockMenu) {
        super(gameScreen);
        this.gameScreen = gameScreen;
        this.stockMenu = stockMenu;
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
            stockMenu.changeCurrentPage(-1);
        else if(touchPositionX > 1500 && touchPositionX < 1590 && touchPositionY > 440 && touchPositionY < 770)
            stockMenu.changeCurrentPage(1);
        else if(touchPositionX >= 300 && touchPositionX <= 1588 && touchPositionY >= 300 && touchPositionY <= 965){
            /*int dishIndex = (touchPositionX - 500) / 350 + dishMenu.getCurrentPage() * 3;
            gameScreen.getGameScreenController().getMenuStack().push(new DishDetailsMenu(gameScreen, dishIndex));

            InputProcessor mul = gameScreen.getGameScreenController().getMenuStack()
                    .peek().getMenuController();
            gameScreen.setInputProcessor(mul);*/

        }
        else{

            gameScreen.getGameScreenController().getMenuStack().pop();
            if(gameScreen.getGameScreenController().getMenuStack().isEmpty()) {
                gameScreen.resetController();
            }
            else {
                gameScreen.setInputProcessor(gameScreen.getGameScreenController().getMenuStack()
                        .peek().getMenuController());
            }
        }
    }
}
