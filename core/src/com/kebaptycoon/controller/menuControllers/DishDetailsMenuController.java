package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.menus.DishDetailsMenu;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class DishDetailsMenuController extends MenuController{

    private DishDetailsMenu dishDetailsMenu;
    public DishDetailsMenuController(GameScreen gameScreen, DishDetailsMenu dishDetailsMenu) {
        super(gameScreen);
        this.dishDetailsMenu = dishDetailsMenu;

    }
    @Override
    public boolean keyDown (int keycode){
        if(keycode == Input.Keys.BACK){
            checkPressedPosition(-1, -1);
        }
        return false;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        Vector2 vector2 = gameScreen.menuUnproject(x, y);
        super.touchPositionX = (int)(vector2.x);
        super.touchPositionY = (int)(vector2.y);

        checkPressedPosition(touchPositionX, touchPositionY);
        return false;
    }
    public void checkPressedPosition(int touchPositionX, int touchPositionY){

        if(touchPositionX > 300 && touchPositionX < 400 && touchPositionY > 425 && touchPositionY < 700){

        }
        else if(touchPositionX > 1500 && touchPositionX < 1580 && touchPositionY > 425 && touchPositionY < 700){

        }
        else if(touchPositionX >= 400 && touchPositionX <= 1500 && touchPositionY >= 300 && touchPositionY <= 840){
            int ingredientColumn = (touchPositionX - 780) / 240;
            int ingredientRow = (800 - touchPositionY ) / 200;
            int ingredient = ingredientRow * 3 + ingredientColumn;
            int midPoint = ((840 + (ingredient % 3) * 240) + (940 + (ingredient % 3) * 240 + 20)) / 2;

            if(touchPositionX > 830 && touchPositionY >= 410){
                if(ingredient < dishDetailsMenu.getCloneRecipe().getIngredients().size()){
                    if (touchPositionX < midPoint) {
                        if (dishDetailsMenu.getCloneRecipe().getIngredients().get(ingredient).right > 0) {
                            dishDetailsMenu.getCloneRecipe().getIngredients().get(ingredient).right--;
                        }
                    }
                    else if(touchPositionX > midPoint) {
                        if(dishDetailsMenu.getCloneRecipe().getIngredients().get(ingredient).right < 99) {
                            dishDetailsMenu.getCloneRecipe().getIngredients().get(ingredient).right++;
                        }
                    }
                }
            }
            if(touchPositionY > 320 && touchPositionY < 410){
                if(touchPositionX > 450 && touchPositionX < 544){
                    backPrevious();
                }
                else if(touchPositionX > 760 && touchPositionX < 1030){
                    dishDetailsMenu.setOriginalRecipe();
                    backPrevious();
                }

            }
            if(touchPositionX > 450 && touchPositionX < 500 && touchPositionY > 460 && touchPositionY < 490) {
                if(dishDetailsMenu.getCloneRecipe().getPrice() > 0)
                    dishDetailsMenu.getCloneRecipe().setPrice(dishDetailsMenu.getCloneRecipe().getPrice()-1);
            }

            else if(touchPositionX > 640 && touchPositionX < 690 && touchPositionY > 460 && touchPositionY < 490){
                if(dishDetailsMenu.getCloneRecipe().getPrice() < 99)
                    dishDetailsMenu.getCloneRecipe().setPrice(dishDetailsMenu.getCloneRecipe().getPrice()+1);
            }

        }
        else{
            dispose();
        }
    }

}
