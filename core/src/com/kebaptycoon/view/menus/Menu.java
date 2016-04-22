package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

public abstract class Menu {

    InputProcessor menuController;
    GameLogic gameLogic;
    GameScreen gameScreen;
    ResourceManager resourceManager;
    FreeTypeFontGenerator heading1Generator, heading2Generator,heading3Generator ;
    FreeTypeFontGenerator.FreeTypeFontParameter heading1Parameter, heading2Parameter, heading3Parameter;
    BitmapFont heading1Font, heading2Font, heading3Font;
    int currentPage;

    public Menu(GameScreen gameScreen){

        this.gameScreen = gameScreen;
        resourceManager = gameScreen.getResourceManager();
        gameLogic = gameScreen.getGameLogic();

        heading1Generator = resourceManager.fonts.get("Boogaloo");
        heading1Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading1Parameter.size = 72;
        heading1Parameter.color = Color.WHITE;

        heading1Font = heading1Generator.generateFont(heading1Parameter);
        heading1Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        heading2Generator = resourceManager.fonts.get("ClearSans");
        heading2Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading2Parameter.size = 45;
        heading2Parameter.color = Color.WHITE;

        heading2Font = heading2Generator.generateFont(heading2Parameter);
        heading2Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        heading3Generator = resourceManager.fonts.get("ClearSans");
        heading3Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading3Parameter.size = 25;
        heading3Parameter.color = Color.WHITE;
        heading3Font = heading3Generator.generateFont(heading3Parameter);
        heading3Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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
