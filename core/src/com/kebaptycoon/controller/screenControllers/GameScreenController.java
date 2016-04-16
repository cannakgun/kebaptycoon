package com.kebaptycoon.controller.screenControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.kebaptycoon.view.menus.DishMenu;
import com.kebaptycoon.view.menus.Menu;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class GameScreenController implements GestureDetector.GestureListener, InputProcessor{

    private static final float SCROLL_SCALE = .06f;

	private Vector2 			oldInitialFirstPointer;
	private Vector2 			oldInitialSecondPointer;
	private float 				oldScale;
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
        Vector2 actualTouch = gameScreen.menuUnproject(x, y);
        System.out.println(actualTouch);
        int touchPositionY = (int)actualTouch.y;
		//check for pressed menu
		if(touchPositionY >= 0&& touchPositionY <= gameScreen.getMenuHeight())
			setMenuUtilities((int) actualTouch.x);
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
	public void setMenuUtilities(int x){

		if(x <= 200) {
            System.out.println("DIIISH");
            menuStack.push(new DishMenu(gameScreen));
            GestureDetector gestureDetector = new GestureDetector(menuStack.getTop().getMenuController());
            gameScreen.setInputProcessor(gestureDetector);
		}

		//TODO: set menu types according to the coordinates that player pressed
	}

    @Override
    public boolean keyDown(int keycode) {
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

}