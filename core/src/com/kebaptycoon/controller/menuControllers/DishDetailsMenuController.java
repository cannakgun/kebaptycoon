package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.view.menus.DishDetailsMenu;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by Can Akgün on 7.4.2016.
 */
public class DishDetailsMenuController extends MenuController{

    private DishDetailsMenu dishDetailsMenu;
    Recipe rec;
    public DishDetailsMenuController(GameScreen gameScreen, DishDetailsMenu dishDetailsMenu) {
        super(gameScreen);
        this.dishDetailsMenu = dishDetailsMenu;
        rec = gameScreen.getGameLogic().getRecipeManager().getRecipes().get(dishDetailsMenu.getDishIndex());
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

        if(touchPositionX >= 300 && touchPositionX <= 1588 && touchPositionY >= 300 && touchPositionY <= 965){
            int ingredientColumn = (touchPositionX - 770) / 240;
            int ingredientRow = (800 - touchPositionY ) / 200;
            int ingredient = ingredientRow * 3 + ingredientColumn;
            int midPoint = ((830 + (ingredient % 3) * 240) + (950 + (ingredient % 3) * 240 + 20)) / 2;

            if(touchPositionX > 830){
                if(ingredient < rec.getIngredients().size()){
                    if (touchPositionX < midPoint)
                        rec.getIngredients().get(ingredient).right--;
                    else
                        rec.getIngredients().get(ingredient).right++;
                }
            }

            if(touchPositionX > 500 && touchPositionX < 550 && touchPositionY > 590 && touchPositionY < 620)
                rec.setPrice(rec.getPrice()-1);
            else if(touchPositionX > 680 && touchPositionX < 730 && touchPositionY > 590 && touchPositionY < 620)
                rec.setPrice(rec.getPrice()+1);
        }
        else{

            gameScreen.getGameScreenController().getMenuStack().pop();
            if(gameScreen.getGameScreenController().getMenuStack().isEmpty()) {
                gameScreen.resetController();
            }
            else {
                gameScreen.setInputProcessor(gameScreen.getGameScreenController().getMenuStack()
                        .peek().getMenuController());
            }
        }
    }

}
