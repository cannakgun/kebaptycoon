package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.kebaptycoon.utils.IsometricHelper;

/**
 * Created by dogancandemirtas on 27/02/16.
 */
public class GameScreen implements Screen{
	
	private Matrix4 			isoTransform = null;
	private Matrix4				invIsotransform = null;
	private Matrix4				id = null;
	private SpriteBatch			spriteBatch = null;
	private OrthographicCamera	cam = null;
	private float				tileWidth = 1.0f;
	private float				tileHeight = (float) Math.tan(IsometricHelper.Angle);

	public GameScreen() {
		//Set up graphics
		GL20 gl = Gdx.graphics.getGL20();
		gl.glEnable(GL20.GL_BLEND);
		gl.glEnable(GL20.GL_TEXTURE_2D);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		//Create sprite batch
		spriteBatch = new SpriteBatch();
		
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
		
		spriteBatch.setProjectionMatrix(cam.combined);
		
		//Set identity matrix as transform matrix
		//	so that sprites will be drawn as they are
		spriteBatch.setTransformMatrix(id);

		spriteBatch.begin();
		renderMap();
		renderEntities();
		spriteBatch.end();

		//Set isometric transform matrix as transform matrix
		//	so that sprites will be drawn after isometricly transformed
		spriteBatch.setTransformMatrix(isoTransform);
		spriteBatch.begin();
		spriteBatch.end();
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
}
