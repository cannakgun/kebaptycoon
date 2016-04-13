package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.kebaptycoon.KebapTycoonGame;
import com.kebaptycoon.controller.screenControllers.DishSelectionScreenController;
import com.kebaptycoon.utils.TextureManager;

public class DishSelectionScreen implements Screen{

    private DishSelectionScreenController dishSelectionScreenController;
    private SpriteBatch batch;
    private Texture texture;
    private OrthographicCamera camera;

    public DishSelectionScreen(){

        dishSelectionScreenController = new DishSelectionScreenController();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch();
        texture = TextureManager.getInstance().dishSelectionScreenBackground;
        Gdx.input.setInputProcessor(new GestureDetector(dishSelectionScreenController));
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

        if(dishSelectionScreenController.isDishTypeSelected()){
            KebapTycoonGame.getInstance().setScreen(new GameScreen());
        }
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
