package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishDetailsMenuController;
import com.kebaptycoon.controller.menuControllers.DishMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.screens.GameScreen;

public class DishDetailsMenu extends Menu {
    private GameScreen gameScreen;

    private SpriteBatch batch;
    private Texture texture;

    private int dishIndex;

    private FreeTypeFontGenerator menuTitleGenerator, priceFontGenerator, ingredientAmountFontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter menuTitleParameter, priceFontParameter,
            ingredientAmountFontParameter;
    private BitmapFont titleFont, priceFont, ingredientAmountFont;

    private Recipe rec;

    public DishDetailsMenu(GameScreen gameScreen, int dishIndex) {

        this.gameScreen = gameScreen;
        this.dishIndex = dishIndex;


        menuTitleGenerator = resourceManager.fonts.get("Boogaloo");
        menuTitleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuTitleParameter.size = 60;
        menuTitleParameter.color = Color.BLACK;
        titleFont = menuTitleGenerator.generateFont(menuTitleParameter);
        titleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        priceFontGenerator = resourceManager.fonts.get("ClearSans");
        priceFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        priceFontParameter.size = 30;
        priceFontParameter.color = Color.BLACK;
        priceFont = priceFontGenerator.generateFont(priceFontParameter);
        priceFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ingredientAmountFontGenerator = resourceManager.fonts.get("ClearSans");
        ingredientAmountFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        ingredientAmountFontParameter.size = 20;
        ingredientAmountFontParameter.color = Color.BLACK;
        ingredientAmountFont = ingredientAmountFontGenerator.generateFont(ingredientAmountFontParameter);
        ingredientAmountFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        MenuController mc = new DishDetailsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        rec = gameScreen.getGameLogic().getRecipeManager().getRecipes().get(dishIndex);
    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        titleFont.draw(batch, rec.getName()
                , 845, 920);

        batch.draw(gameScreen.getResourceManager().textures.get(rec.getTexture()), 500, 650);
        batch.draw(resourceManager.textures.get("menu_check"), 1390 , 350, 70, 70);

        priceFont.draw(batch, "" + rec.getPrice() + " TL", 570, 610);
        batch.draw(resourceManager.textures.get("menu_minus"), 500, 590);
        batch.draw(resourceManager.textures.get("menu_plus"),  680 , 590);

        int y = 900;

        for (int i = 0; i < rec.getIngredients().size(); i++){
            if(i%3 == 0)
                y -= 200;

            batch.draw(resourceManager.textures.get("ingredients_"+rec.getIngredients().get(i).getLeft()),
                    850 + (i%3) * 240, y, 100, 100);
            batch.draw(resourceManager.textures.get("menu_minus"), 830 + (i%3) * 240, y - 45);
            batch.draw(resourceManager.textures.get("menu_plus"), 950 + (i%3) * 240, y - 45);
            ingredientAmountFont.draw(batch, ""+rec.getIngredients().get(i).getRight(), 900 + (i%3) * 240, y - 30);
        }
        batch.end();
    }

    public int getDishIndex() {
        return dishIndex;
    }
}
