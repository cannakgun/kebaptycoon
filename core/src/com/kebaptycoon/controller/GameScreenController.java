package com.kebaptycoon.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.view.screens.GameScreen;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class GameScreenController implements GestureDetector.GestureListener{

	private static GameScreenController instance = null;
	
	private Vector2 			oldInitialFirstPointer;
	private Vector2 			oldInitialSecondPointer;
	private float 				oldScale;
	private OrthographicCamera 	cam;
	
	private GameScreenController()
	{
		oldInitialFirstPointer = null;
		oldInitialSecondPointer = null;
	}
	
	public static GameScreenController getInstance()
	{
		if (instance == null)
		{
			instance = new GameScreenController();
		}
		return instance;
	}

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
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
		cam.update();
		cam.position.add(
				cam.unproject(new Vector3(0, 0, 0))
						.add(cam.unproject(new Vector3(deltaX, deltaY, 0)).scl(-1f))
		);
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
			oldScale = cam.zoom;
		}
		Vector3 center = new Vector3(
				(firstPointer.x+initialSecondPointer.x)/2,
				(firstPointer.y+initialSecondPointer.y)/2,
				0
		);
		zoomCamera(center, oldScale * initialFirstPointer.dst(initialSecondPointer) / firstPointer.dst(secondPointer));
		return true;
	}

	private void zoomCamera(Vector3 origin, float scale){
		cam.update();
		Vector3 oldUnprojection = cam.unproject(origin.cpy()).cpy();
		cam.zoom = scale; //Larger value of zoom = small images, border view
		cam.zoom = Math.min(2.0f, Math.max(cam.zoom, 0.5f));
		cam.update();
		Vector3 newUnprojection = cam.unproject(origin.cpy()).cpy();
		cam.position.add(oldUnprojection.cpy().add(newUnprojection.cpy().scl(-1f)));
	}

}
