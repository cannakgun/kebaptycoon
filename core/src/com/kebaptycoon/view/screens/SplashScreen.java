package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.kebaptycoon.KebapTycoonGame;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.ResourceManager;

public class SplashScreen implements Screen{

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private long startTime;

    private Texture background;

    private ResourceManager resourceManager;

    private int frame = 0;

    public SplashScreen(){

        resourceManager = new ResourceManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1920, 1080);
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("textures/screens/splash.jpeg"));

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
            batch.draw(background, 0, 0);
        batch.end();

        if(frame++ == 1)
            resourceManager.loadAssets();

        if ((TimeUtils.millis() - startTime) > Globals.SPLASH_SCREEN_DISPLAY_PERIOD)
            KebapTycoonGame.getInstance().setScreen(new DishSelectionScreen(resourceManager));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {

        startTime = TimeUtils.millis();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        background.dispose();
        batch.dispose();
    }
}

