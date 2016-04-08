package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.kebaptycoon.controller.screenControllers.GameScreenController;
import com.kebaptycoon.utils.IsometricHelper;
import com.kebaptycoon.utils.TextureManager;
import com.kebaptycoon.view.menus.DishMenu;
import com.kebaptycoon.view.menus.Menu;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class GameScreen implements Screen{

	private GameScreenController gameScreenController;
	
	private Matrix4 			isoTransform = null;
	private Matrix4				invIsotransform = null;
	private Matrix4				id = null;
	private SpriteBatch			spriteBatch = null;
    private OrthographicCamera	cam = null;
	private float				tileWidth = 1.0f;
	private float				tileHeight = (float) Math.tan(IsometricHelper.Angle);
	private Texture 			texture;

	public GameScreen() {

		//Create Controller
		gameScreenController = new GameScreenController(this);
		Gdx.input.setInputProcessor(new GestureDetector(gameScreenController));
		//Set up graphics
		GL20 gl = Gdx.graphics.getGL20();
		gl.glEnable(GL20.GL_BLEND);
		gl.glEnable(GL20.GL_TEXTURE_2D);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		

		//Create sprite batch
		spriteBatch = new SpriteBatch();
		texture = TextureManager.getInstance().gameScreenBackground;

		//Identity Matrix
		id = new Matrix4();
		id.idt();

		//Create the isometric transform matrix
		isoTransform = new Matrix4();
		isoTransform.idt();
		isoTransform.translate(0.0f, 0.25f, 0.0f);
		isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
		isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);

		//... and the inverse isometric transform matrix
		invIsotransform = new Matrix4(isoTransform);
		invIsotransform.inv();

	}

    @Override
    public void show() {

    }

	@Override
	public void render(float delta)
	{
		//Clear the screen
		GL20 gl = Gdx.graphics.getGL20();
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//TODO: Call the game logic from here

        //spriteBatch.setProjectionMatrix(cam.combined);
        //shapeRenderer.setProjectionMatrix(cam.combined);
		
		//Set identity matrix as transform matrix
		//	so that sprites will be drawn as they are
		//spriteBatch.setTransformMatrix(id);
        //shapeRenderer.setTransformMatrix(id);
		spriteBatch.begin();
		renderMap();
		renderEntities();
        spriteBatch.end();

		//check any of the menus is pressed and if so render it
		if(!gameScreenController.getMenuStack().isEmpty()){
            for(int i = 0; i < gameScreenController.getMenuStack().size(); i++){
                gameScreenController.getMenuStack().getMenuAtIndex(i).render();

            }

		}

		//Set isometric transform matrix as transform matrix
		//	so that sprites will be drawn after isometricly transformed
		//spriteBatch.setTransformMatrix(isoTransform);
		//spriteBatch.begin();
		//spriteBatch.end();
	}


	@Override
	public void resize(int width, int height) {

		//The cam will show 10 tiles
		float camWidth = tileWidth * 10.0f;

		//For the height, we just maintain the aspect ratio
		float camHeight = camWidth * ((float)height / (float)width);

		cam = new OrthographicCamera(camWidth, camHeight);
		cam.position.set(camWidth / 2.0f, 0, 0);
		cam.update();

	}

	private void renderMap()
	{
        spriteBatch.draw(texture, 0, 0);
//		for (int x = 0; x < 10; x++)
//		{
//			for(int y = 10-1; y >= 0; y--)
//			{
//				float x_pos = (x * tileWidth /2.0f ) + (y * tileWidth / 2.0f);
//				float y_pos = - (x * tileHeight / 2.0f) + (y * tileHeight /2.0f);
//
//				spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
//
//				spriteBatch.draw(tileSet[map[x][y]], x_pos, y_pos, tileWidth, tileHeight);
//			}
//		}
		//TODO: Render the background associated to the current venue
	}
	
	private void renderEntities()
	{
		//TODO: Get the entities to be rendered from the current venue
		//		and render them in order of distance to the camera
	}


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

	@Override
	public void dispose() {
		GL20 gl = Gdx.graphics.getGL20();
		gl.glDisable(GL20.GL_BLEND);
		gl.glDisable(GL20.GL_TEXTURE_2D);

	}
	
	public float getCameraZoom(){
		return cam.zoom;
	}

	public void zoomCamera(Vector3 origin, float scale){
		cam.update();
		Vector3 oldUnprojection = cam.unproject(origin.cpy()).cpy();
		cam.zoom = scale; //Larger value of zoom = small images, border view
		cam.zoom = Math.min(2.0f, Math.max(cam.zoom, 0.5f));
		cam.update();
		Vector3 newUnprojection = cam.unproject(origin.cpy()).cpy();
		cam.position.add(oldUnprojection.cpy().add(newUnprojection.cpy().scl(-1f)));
	}

	public boolean moveCamera(float deltaX, float deltaY) {
		cam.update();
        cam.position.add(
                cam.unproject(new Vector3(0, 0, 0))
                        .add(cam.unproject(new Vector3(deltaX, deltaY, 0)).scl(-1f))
		);
		return true;
	}

	public void setInputProcessor(GestureDetector gestureDetector){

        Gdx.input.setInputProcessor(gestureDetector);
	}

    public GameScreenController getGameScreenController() {
        return gameScreenController;
    }

}
