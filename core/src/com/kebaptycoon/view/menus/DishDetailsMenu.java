package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishDetailsMenuController;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishDetailsMenu extends Menu {
    private SpriteBatch batch;
    private Texture texture;

    public DishDetailsMenu(GameScreen gameScreen) {
        super.menuController = new DishDetailsMenuController(gameScreen);
    }

    public void render(SpriteBatch batch, Viewport viewPort){

    }

}
