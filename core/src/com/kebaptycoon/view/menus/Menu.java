package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

public abstract class Menu {

    InputProcessor menuController;
    GameLogic gameLogic;
    GameScreen gameScreen;
    ResourceManager resourceManager;
    FreeTypeFontGenerator heading1Generator;
    FreeTypeFontGenerator.FreeTypeFontParameter heading1Parameter;
    FreeTypeFontGenerator heading2Generator;
    FreeTypeFontGenerator.FreeTypeFontParameter heading2Parameter;
    BitmapFont heading1Font, heading2Font;
    int currentPage;

    public Menu(){
        resourceManager = gameScreen.getResourceManager();

        heading1Generator = resourceManager.fonts.get("Boogaloo");
        heading1Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading1Parameter.size = 72;
        heading1Parameter.color = Color.BLACK;

        heading1Font = heading1Generator.generateFont(heading1Parameter);
        heading1Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        heading2Generator = resourceManager.fonts.get("ClearSans");
        heading2Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading2Parameter.size = 45;
        heading2Parameter.color = Color.BLACK;

        heading2Font = heading2Generator.generateFont(heading2Parameter);
        heading2Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Gdx.input.setCatchBackKey(true);
    }

    public abstract void render(SpriteBatch batch, Viewport viewPort);

    public InputProcessor getMenuController() {
        return menuController;
    }

    public void setMenuController(InputProcessor menuController) {
        this.menuController = menuController;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void changeCurrentPage(int delta) {}
}
