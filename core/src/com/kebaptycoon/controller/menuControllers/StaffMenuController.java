package com.kebaptycoon.controller.menuControllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.Cook;
import com.kebaptycoon.model.entities.Employee;
import com.kebaptycoon.model.entities.Orientation;
import com.kebaptycoon.model.entities.Waiter;
import com.kebaptycoon.model.entities.prototypes.EmployeePrototype;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.view.menus.FurnitureDropMenu;
import com.kebaptycoon.view.menus.StaffMenu;
import com.kebaptycoon.view.screens.GameScreen;

public class StaffMenuController extends MenuController {

    private StaffMenu staffMenu;

    public StaffMenuController(GameScreen gameScreen, StaffMenu staffMenu) {
        super(gameScreen);
        this.staffMenu = staffMenu;
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

    public void checkPressedPosition(int touchPositionX, int touchPositionY) {

        if (touchPositionX > 310 && touchPositionX < 400 && touchPositionY > 425 && touchPositionY < 700)
            staffMenu.changeCurrentPage(-1);
        else if (touchPositionX > 1500 && touchPositionX < 1580 && touchPositionY > 425 && touchPositionY < 700)
            staffMenu.changeCurrentPage(1);
        else if (touchPositionX >= 400 && touchPositionX <= 1500 && touchPositionY >= 300 && touchPositionY <= 840) {
            int staffColumn = (touchPositionX - 500) / 240;
            int staffRow = (825 - touchPositionY) / 240;
            staffColumn = Math.min(staffColumn, 3);
            staffRow = Math.min(staffRow, 1);
            int staff = (staffRow * 4 + staffColumn) + staffMenu.getCurrentPage() * 8;


            if (staff >= 4) return;

            if(gameScreen.getGameLogic().getMarketManager().getEmployees().
                    get(staff).getLeft().type.equals("Waiter")) {
                Employee reis = new Waiter(3, "waiter");
                reis.setPosition(new Vector3(20, -2, 0));
                reis.setOrientation(Orientation.West);

                gameScreen.getCurrentVenue().getEmployees().add(reis);

                gameScreen.getGameLogic().getAnimationManager().setUpAnimations(reis);



            }
            else if(gameScreen.getGameLogic().getMarketManager().getEmployees().
                    get(staff).getLeft().type.equals("Cook")){

                Employee reis = new Cook(3, "cook");
                reis.setPosition(new Vector3(18, -2, 0));
                reis.setOrientation(Orientation.West);

                gameScreen.getCurrentVenue().getEmployees().add(reis);
                gameScreen.getGameLogic().getAnimationManager().setUpAnimations(reis);
            }

        }
        else
            dispose();
    }
}
