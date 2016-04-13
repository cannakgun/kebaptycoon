package com.kebaptycoon.controller.screenControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.view.menus.DishMenu;
import com.kebaptycoon.view.menus.Menu;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class GameScreenController implements GestureDetector.GestureListener{

	private Vector2 			oldInitialFirstPointer;
	private Vector2 			oldInitialSecondPointer;
	private float 				oldScale;
	private int 				touchPositionX;
	private int 				touchPositionY;
    private GameScreen          gameScreen;
    private MenuStack 			menuStack;
	
	public GameScreenController(GameScreen gameScreen)
	{
		oldInitialFirstPointer = null;
		oldInitialSecondPointer = null;
        menuStack = new MenuStack();
        this.gameScreen = gameScreen;
	}

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 actualTouch = gameScreen.menuUnproject(Gdx.input.getX(), Gdx.input.getY());
        touchPositionX = (int)actualTouch.x;
		touchPositionY = (int)actualTouch.y;
		//check for pressed menu
		if(touchPositionY >= 900&& touchPositionY <= 1080)
			setMenuUtilities();
		return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
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
	public void setMenuUtilities(){

		if(touchPositionX <= 200) {
            menuStack.push(new DishMenu(gameScreen));
            GestureDetector gestureDetector = new GestureDetector(menuStack.getTop().getMenuController());
            gameScreen.setInputProcessor(gestureDetector);
		}

		//TODO: set menu types according to the coordinates that player pressed
	}
    public class MenuStack {

        private ArrayList<Menu> stackArray;

        public MenuStack() {
            stackArray = new ArrayList<Menu>();
        }

        public void push(Menu menu) {
            stackArray.add(menu);
        }

        public Menu pop() {
            Menu menu = stackArray.get(stackArray.size() - 1);
            stackArray.remove(stackArray.size() - 1);
            return menu;
        }
        public int size(){
            return stackArray.size();
        }

        public Menu getTop() {
            return stackArray.get(stackArray.size() - 1);
        }

        public boolean isEmpty() {
            return (stackArray.size() == 0);
        }

        public Menu getMenuAtIndex(int index){
            return stackArray.get(index);
        }
    }

    public MenuStack getMenuStack() {
        return menuStack;
    }

    public void setMenuStack(MenuStack menuStack) {
        this.menuStack = menuStack;
    }

	public int getTouchPositionX() {
		return touchPositionX;
	}

	public void setTouchPositionX(int touchPositionX) {
		this.touchPositionX = touchPositionX;
	}

	public int getTouchPositionY() {
		return touchPositionY;
	}

	public void setTouchPositionY(int touchPositionY) {
		this.touchPositionY = touchPositionY;
	}

}