package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.kebaptycoon.controller.LoginScreenController;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class LoginScreen implements Screen {

    LoginScreenController loginScreenController;

    public LoginScreen(){

    }

    @Override
    public void show() {

        loginScreenController = new LoginScreenController();
        Gdx.input.setInputProcessor(new GestureDetector(loginScreenController));
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
