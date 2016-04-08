package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishMenu extends Menu{

    public DishMenu(GameScreen gameScreen) {
        super.menuController = new DishMenuController(gameScreen);
        super.shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(menuController));
    }

    public void render(){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        renderRect(100, 100, 500, 500);
        shapeRenderer.end();
    }

}
