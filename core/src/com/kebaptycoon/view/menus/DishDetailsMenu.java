package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.DishDetailsMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.view.screens.GameScreen;

public class DishDetailsMenu extends Menu {

    private int dishIndex;
    private Recipe rec;

    public DishDetailsMenu(GameScreen gameScreen, int dishIndex) {

        super(gameScreen);
        this.dishIndex = dishIndex;

        MenuController mc = new DishDetailsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        rec = gameLogic.getRecipeManager().getRecipes().get(dishIndex);
    }

    public void render(SpriteBatch batch, Viewport viewPort){

        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_menuBackground"), 300, 300);

        heading1Font.draw(batch, rec.getName()
                , 805, 920);

        batch.draw(resourceManager.textures.get(rec.getTexture()), 460, 520);

        batch.draw(resourceManager.textures.get("menu_approve"), 760 , 320);
        batch.draw(resourceManager.textures.get("menu_back"), 450 , 320);

        heading2Font.draw(batch, "" + rec.getPrice() + " TL", 510, 490);
        batch.draw(resourceManager.textures.get("menu_minus"), 460  , 460);
        batch.draw(resourceManager.textures.get("menu_plus"),  650 , 460);

        int y = 900;

        for (int i = 0; i < rec.getIngredients().size(); i++){
            if(i%3 == 0)
                y -= 200;

            batch.draw(resourceManager.textures.get("ingredients_" + rec.getIngredients().get(i).getLeft()),
                    850 + (i%3) * 240, y, 100, 100);
            batch.draw(resourceManager.textures.get("menu_minus"), 840 + (i%3) * 240, y - 45);
            batch.draw(resourceManager.textures.get("menu_plus"), 940 + (i%3) * 240, y - 45);
            heading3Font.draw(batch, ""+rec.getIngredients().get(i).getRight(), 900 + (i%3) * 240, y - 25);
        }
        batch.end();
    }

    public int getDishIndex() {
        return dishIndex;
    }
}
