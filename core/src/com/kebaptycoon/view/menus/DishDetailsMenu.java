package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.kebaptycoon.controller.menuControllers.DishDetailsMenuController;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishDetailsMenu extends Menu {

    public DishDetailsMenu(GameScreen gameScreen) {
        super.menuController = new DishDetailsMenuController(gameScreen);
        super.shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(new GestureDetector(menuController));

    }

    public void render(){

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        renderRect(50, 50, 200, 200);
        shapeRenderer.end();

    }
}
