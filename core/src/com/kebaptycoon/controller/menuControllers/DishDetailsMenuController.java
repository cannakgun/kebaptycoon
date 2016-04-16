package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishDetailsMenuController extends MenuController{

    private GameScreen gameScreen;

    public DishDetailsMenuController(GameScreen gameScreen) {
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
        //System.out.println("adana menu controller");
       // System.out.println("x: " + touchPositionX + "y: " + touchPositionY );
        if(touchPositionX >= 50 && touchPositionX <= 250 && touchPositionY >= 50 && touchPositionY <= 250){
            //System.out.println("adana menu controller kordinat tuttu");
            gameScreen.getGameScreenController().getMenuStack().pop();
            if(gameScreen.getGameScreenController().getMenuStack().isEmpty())
            {
                GestureDetector gestureDetector = new GestureDetector(gameScreen.getGameScreenController());
                gameScreen.setInputProcessor(gestureDetector);
            }
            else
            {
                InputProcessor ip = gameScreen.getGameScreenController().getMenuStack()
                                                        .peek().getMenuController();
                gameScreen.setInputProcessor(ip);
            }
        }
        else{

        }
    }

}
