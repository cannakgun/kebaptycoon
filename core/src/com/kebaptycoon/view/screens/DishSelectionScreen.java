package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kebaptycoon.utils.TextureManager;

public class DishSelectionScreen implements Screen{

    private static DishSelectionScreen instance = null;


    private SpriteBatch batch;
    private Texture texture;
    private OrthographicCamera camera;

    private DishSelectionScreen(){

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch();
        texture = TextureManager.getInstance().dishSelectionScreenBackground;
    }

    public static DishSelectionScreen getInstance(){

        if (instance == null)
            instance = new DishSelectionScreen();

        return instance;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            batch.draw(texture, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
