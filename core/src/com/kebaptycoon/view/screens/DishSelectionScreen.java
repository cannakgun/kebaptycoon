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
import com.kebaptycoon.utils.ResourceManager;

public class DishSelectionScreen implements Screen{

    private DishSelectionScreenController dishSelectionScreenController;
    private SpriteBatch batch;
    private Texture background;
    private OrthographicCamera camera;
    private ResourceManager resourceManager;

    public DishSelectionScreen(ResourceManager resourceManager){

        this.resourceManager = resourceManager;
        dishSelectionScreenController = new DishSelectionScreenController();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch();
        background = resourceManager.textures.get("screens_dishSelection");
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
            batch.draw(background, 0, 0);
        batch.end();

        if(dishSelectionScreenController.isDishTypeSelected()){
            KebapTycoonGame.getInstance().setScreen(new GameScreen(resourceManager));
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
