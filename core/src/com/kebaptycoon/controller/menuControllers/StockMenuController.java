package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.managers.SoundManager;
import com.kebaptycoon.view.menus.StockMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class StockMenuController extends MenuController {

    private StockMenu stockMenu;

    public StockMenuController(GameScreen gameScreen, StockMenu stockMenu) {
        super(gameScreen);
        this.stockMenu = stockMenu;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 vector2 = gameScreen.menuUnproject(x, y);
        super.touchPositionX = (int)(vector2.x);
        super.touchPositionY = (int)(vector2.y);

        checkPressedPosition(touchPositionX, touchPositionY);
        return false;
    }

    @Override
    public boolean keyDown (int keycode){
        if(keycode == Input.Keys.BACK){
            checkPressedPosition(-1, -1);
        }
        return false;
    }

    public void checkPressedPosition(int touchPositionX, int touchPositionY){

        if(touchPositionX > 310 && touchPositionX < 450 && touchPositionY > 425 && touchPositionY < 700)
            stockMenu.changeCurrentPage(-1);
        else if(touchPositionX > 1450 && touchPositionX < 1580 && touchPositionY > 425 && touchPositionY < 700)
            stockMenu.changeCurrentPage(1);
        else if(touchPositionX >= 450 && touchPositionX <= 1450 && touchPositionY >= 300 && touchPositionY <= 840){

            int ingredientColumn = (touchPositionX - 500) / 240;
            int ingredientRow = (680 - touchPositionY ) / 200;
            int ingredient = (ingredientRow * 4 + ingredientColumn) +  stockMenu.getCurrentPage() * 8;

            int midPoint = ((510 + (ingredient % 4) * 240) + (645 + (ingredient % 4) * 240 + 20)) / 2;
            Ingredient ing = gameScreen.getGameLogic().getMarketManager().getIngredients().get(ingredient).getLeft();
            Integer price = gameScreen.getGameLogic().getMarketManager().getIngredients().get(ingredient).getRight();
            if(touchPositionX > 510){
                if(ingredient < gameScreen.getGameLogic().getMarketManager().getIngredients().size()){
                   if(touchPositionX > midPoint){

                       if(gameScreen.getCurrentVenue().getStock(ing) < 999 && gameScreen.getCurrentVenue().pay(price)){
                           gameScreen.getCurrentVenue().incrementIngredient(ing, 1);
                           SoundManager.play("pay");
                       }
                   }

                }
            }

        }
        else{
            dispose();
        }
    }
}
