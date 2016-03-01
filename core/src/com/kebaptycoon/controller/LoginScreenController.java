package com.kebaptycoon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class LoginScreenController implements GestureDetector.GestureListener{

    private int touchPositionX, touchPositionY;

    public LoginScreenController(){
    }

    public int getTouchPositionY() {
        return touchPositionY;
    }

    public int getTouchPositionX() {
        return touchPositionX;
    }

    public void setTouchPositionX(int touchPositionX) {
        this.touchPositionX = touchPositionX;
    }

    public void setTouchPositionY(int touchPositionY) {
        this.touchPositionY = touchPositionY;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        touchPositionX = Gdx.input.getX();
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
}
