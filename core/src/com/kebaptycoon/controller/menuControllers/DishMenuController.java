package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.model.entities.Dish;
import com.kebaptycoon.view.menus.DishDetailsMenu;
import com.kebaptycoon.view.menus.DishMenu;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishMenuController extends MenuController {

    private GameScreen gameScreen;
    private DishMenu dishMenu;

    public DishMenuController(GameScreen gameScreen, DishMenu dishMenu) {
        super(gameScreen);
        this.gameScreen = gameScreen;
        this.dishMenu = dishMenu;
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

        if(touchPositionX >= 300 && touchPositionX <= 1588 && touchPositionY >= 300 && touchPositionY <= 965){
            /*            gameScreen.getGameScreenController().getMenuStack().push(new DishDetailsMenu(gameScreen));
            InputProcessor mul = gameScreen.getGameScreenController().getMenuStack()
                                                                    .peek().getMenuController();
            gameScreen.setInputProcessor(mul);
            */
            dishMenu.changeCurrentPage(1);
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
