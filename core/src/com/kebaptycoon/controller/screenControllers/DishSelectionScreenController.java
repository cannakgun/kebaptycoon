package com.kebaptycoon.controller.screenControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class DishSelectionScreenController implements GestureDetector.GestureListener{

    private static DishSelectionScreenController instance = null;

    private int touchPositionX;
    private int touchPositionY;
    private String dishType;
    private boolean dishTypeSelected;

    public DishSelectionScreenController(){
        touchPositionX = -1;
        touchPositionY = -1;
        dishType = "";
        dishTypeSelected = false;
    }
    public static DishSelectionScreenController getInstance()
    {
        if (instance == null)
        {
            instance = new DishSelectionScreenController();
        }
        return instance;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touchPositionX = Gdx.input.getX();
        touchPositionY =  Gdx.input.getY();
        if(touchPositionX <= 200){
            dishType = "kofteci";
            dishTypeSelected = true;
        }

        //TODO: Set dishTypes (and dishTypeSeleceted boolean) here according to the touchPositions.

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    public int getTouchPositionX() {
        return touchPositionX;
    }

    public void setTouchPositionX(int touchPositionX) {
        this.touchPositionX = touchPositionX;
    }

    public int gettTouchPositionY() {
        return touchPositionY;
    }

    public void setTouchPositionY(int getTouchPositionY) {
        this.touchPositionY = getTouchPositionY;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public boolean isDishTypeSelected() {
        return dishTypeSelected;
    }

    public void setDishTypeSelected(boolean dishTypeSelected) {
        this.dishTypeSelected = dishTypeSelected;
    }
}
