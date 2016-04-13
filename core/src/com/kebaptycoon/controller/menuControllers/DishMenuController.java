package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.kebaptycoon.view.menus.DishDetailsMenu;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishMenuController extends MenuController {

    private GameScreen gameScreen;

    public DishMenuController(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        super.touchPositionX = Gdx.input.getX();
        super.touchPositionY = Gdx.input.getY();

        checkPressedPosition(touchPositionX, touchPositionY);
        return false;
    }
    public void checkPressedPosition(int touchPositionX, int touchPositionY){

        if(touchPositionX >= 100 && touchPositionX <= 600 && touchPositionY >= 100 && touchPositionY <= 600){
            gameScreen.getGameScreenController().getMenuStack().pop();
            if(gameScreen.getGameScreenController().getMenuStack().isEmpty()) {
                GestureDetector gestureDetector = new GestureDetector(gameScreen.getGameScreenController());
                gameScreen.setInputProcessor(gestureDetector);
            }
        }
        else{
            gameScreen.getGameScreenController().getMenuStack().push(new DishDetailsMenu(gameScreen));
            GestureDetector gestureDetector = new GestureDetector(gameScreen.getGameScreenController().getMenuStack()
                                                                                        .getTop().getMenuController());
            gameScreen.setInputProcessor(gestureDetector);
        }
    }

}
