package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.view.menus.MarketMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class MarketMenuController extends MenuController {

    private MarketMenu marketMenu;
    public MarketMenuController(GameScreen gameScreen, MarketMenu marketMenu) {
        super(gameScreen);
        this.marketMenu = marketMenu;
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

        if(touchPositionX > 310 && touchPositionX < 400 && touchPositionY > 425 && touchPositionY < 700)
            marketMenu.changeCurrentPage(-1);
        else if(touchPositionX > 1500 && touchPositionX < 1580 && touchPositionY > 425 && touchPositionY < 700)
            marketMenu.changeCurrentPage(1);
        else if(touchPositionX >= 400 && touchPositionX <= 1500 && touchPositionY >= 300 && touchPositionY <= 840){
            int furnitureColumn = (touchPositionX - 500) / 240;
            int furnitureRow = (825 - touchPositionY ) / 240;
            furnitureColumn = Math.min(furnitureColumn, 3);
            furnitureRow = Math.min(furnitureRow, 1);
            int furniture = (furnitureRow * 4 + furnitureColumn) +  marketMenu.getCurrentPage() * 8;

            if(furniture >= gameScreen.getGameLogic().getMarketManager()
                    .getFurnitures().size()) return;

            FurniturePrototype proto = gameScreen.getGameLogic().getMarketManager()
                    .getFurnitures().get(furniture).getLeft();
            int price = gameScreen.getGameLogic().getMarketManager()
                    .getFurnitures().get(furniture).getRight();

            //if(!gameScreen.getCurrentVenue().pay(price)) return;

            Furniture newFurniture = new Furniture(proto);
            newFurniture.setPosition(new Vector3(17, 5, 0));

            gameScreen.getCurrentVenue().getFurnitures().add(newFurniture);

            gameScreen.getGameLogic().getAnimationManager().setUpAnimations(newFurniture);
        }
        else{
            dispose();
        }
    }
}
