package com.kebaptycoon.controller.screenControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.model.entities.Venue;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.menus.AdvertisementMenu;
import com.kebaptycoon.view.menus.DishMenu;
import com.kebaptycoon.view.menus.EstateMenu;
import com.kebaptycoon.view.menus.FriendsMenu;
import com.kebaptycoon.view.menus.MarketMenu;
import com.kebaptycoon.view.menus.Menu;
import com.kebaptycoon.view.menus.ReportsMenu;
import com.kebaptycoon.view.menus.StaffMenu;
import com.kebaptycoon.view.menus.StockMenu;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;
import java.util.Stack;

public class GameScreenController implements GestureDetector.GestureListener, InputProcessor{

    private static final float SCROLL_SCALE = .06f;

	private Vector2 			    oldInitialFirstPointer;
	private Vector2 			    oldInitialSecondPointer;
	private float 				    oldScale;
    private GameScreen              gameScreen;
    private Stack<Menu> 			menuStack;
    private boolean                 venue;

	public GameScreenController(GameScreen gameScreen) {

		oldInitialFirstPointer = null;
		oldInitialSecondPointer = null;
        menuStack = new Stack<Menu>();
        venue = true;
        //menus = {};
        this.gameScreen = gameScreen;
	}

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

		return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector2 actualTouch = gameScreen.menuUnproject(x, y);
        processTouch(actualTouch);
        return false;
    }

    public void processTouch(Vector2 v) {
        int touchPositionY = (int)v.y;
        //check for pressed menu
        if(touchPositionY >= 0&& touchPositionY <= gameScreen.getMenuHeight())
        {
            setMenuUtilities((int) v.x);
        }else if(v.x > 1600 && v.x < 1900 && v.y > 250 && v.y < 350 && gameScreen.getGameLogic().isAfterHours()){
            processTouch(new Vector2(200, 400));
            int index = gameScreen.getGameLogic().getVenueManager().getVenueList().
                    indexOf(gameScreen.getCurrentVenue());
            Venue ev =
                    gameScreen.getGameLogic().resetVenue(index);
            gameScreen.setVenue(ev);
            gameScreen.setReportSummaryOpen(false);
        }
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		gameScreen.moveCamera(deltaX, deltaY);
		return true;
	}

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }
	@Override
	public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){
		if(!(initialFirstPointer.equals(oldInitialFirstPointer)&&initialSecondPointer.equals(oldInitialSecondPointer))){
			oldInitialFirstPointer = initialFirstPointer.cpy();
			oldInitialSecondPointer = initialSecondPointer.cpy();
			oldScale = gameScreen.getCameraZoom();
		}
		Vector3 center = new Vector3(
				(firstPointer.x+initialSecondPointer.x)/2,
				(firstPointer.y+initialSecondPointer.y)/2,
				0
		);
        gameScreen.zoomCamera(center, oldScale * initialFirstPointer.dst(initialSecondPointer) / firstPointer.dst(secondPointer));
		return true;
	}

	public void setMenuUtilities(int x){

        float unit = 1920/(gameScreen.getMenuBarItems().size() + 1);

        for (int i = 0; i < gameScreen.getMenuBarItems().size(); i++) {

            float centerX = gameScreen.getMenuBarItems().get(i).getRight() * unit;

            String name = gameScreen.
                    getMenuBarItems().get(i).getLeft();
            
            Texture tx = gameScreen.getResourceManager().textures.get("menu_" + name);
            float leftX = centerX - (tx.getWidth() / 2);
            float rightX = centerX + (tx.getWidth() / 2);

            if(x > leftX && x < rightX && name.equals("stock")) {
                menuStack.push(new StockMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("menu")){
                menuStack.push(new DishMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("market")){
                menuStack.push(new MarketMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("staff")){
                menuStack.push(new StaffMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("estate")){
                //menuStack.push(new EstateMenu(gameScreen));
                if(venue) {
                    gameScreen.setVenue(gameScreen.getGameLogic().getVenueManager().getVenueList().get(1));
                    venue = false;
                }
                else{
                    gameScreen.setVenue(gameScreen.getGameLogic().getVenueManager().getVenueList().get(0));
                    venue = true;
                }

            }

            else if(x > leftX && x < rightX && name.equals("advertisement")) {
                menuStack.push(new AdvertisementMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("reports")) {
                menuStack.push(new ReportsMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }
            else if(x > leftX && x < rightX && name.equals("friends")) {
                menuStack.push(new FriendsMenu(gameScreen));
                gameScreen.getGameLogic().setPaused(true);
            }

            if(!menuStack.isEmpty()){
                InputProcessor ip = menuStack.peek().getMenuController();
                gameScreen.setInputProcessor(ip);
            }
        }

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            System.out.println("exit yaptık");

            Gdx.app.exit();

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameScreen.zoomCamera(mouse,
                gameScreen.getCameraZoom() - (amount * SCROLL_SCALE));
        return false;
    }

    public Stack<Menu> getMenuStack() {
        return menuStack;
    }

    public void setMenuStack(Stack<Menu> menuStack) {
        this.menuStack = menuStack;
    }

}