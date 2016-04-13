package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MenuController;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public abstract class Menu {

    MenuController menuController;
    ShapeRenderer shapeRenderer;

    public abstract void render(SpriteBatch batch, Viewport viewPort);

    public void renderRect(int x, int y, int w, int h, Viewport viewPort)
    {
        shapeRenderer.rect(x, viewPort.getScreenHeight()-y-h,w,h);
    }
    public MenuController getMenuController() {
        return menuController;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }
}
