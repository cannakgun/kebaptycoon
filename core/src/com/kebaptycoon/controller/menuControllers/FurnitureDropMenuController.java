package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.view.menus.FurnitureDropMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class FurnitureDropMenuController extends MenuController {

    private FurnitureDropMenu dropMenu;
    public FurnitureDropMenuController(GameScreen gameScreen, FurnitureDropMenu dropMenu) {
        super(gameScreen);
        this.dropMenu = dropMenu;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        Vector2 vector2 = gameScreen.menuUnproject(x, y);
        super.touchPositionX = (int)(vector2.x);
        super.touchPositionY = (int)(vector2.y);

        Furniture newFurniture = new Furniture(dropMenu.getFurniture());
        newFurniture.setPosition(dropMenu.getFurnitureWorldPosition());

        gameScreen.getCurrentVenue().getFurnitures().add(newFurniture);

        gameScreen.getGameLogic().getAnimationManager().setUpAnimations(newFurniture);

        //TODO: Fail placement and return money on colliding furnitures

        dispose();
        return false;
    }

    @Override
    public boolean keyDown (int keycode){
        if(keycode == Input.Keys.BACK){
            dispose();
        }
        return false;
    }
}
