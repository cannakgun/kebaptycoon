package com.kebaptycoon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    public Texture dishSelectionScreenBackground;
    public Texture gameScreenBackground;
    public Texture badLogic, menuBar, grass;
    public Texture splash;

    public Texture advertisement, estate, market, menu, reports, staff, stock;

    public Texture background;

    private static TextureManager instance;

    public void loadTextures(){

        dishSelectionScreenBackground = new Texture(Gdx.files.internal("dishSelectionScreen.png"));
        gameScreenBackground = new Texture(Gdx.files.internal("gameScreen.png"));
        badLogic = new Texture(Gdx.files.internal("badlogic.jpg"));
        splash = new Texture(Gdx.files.internal("splashScreenBackground.jpeg"));
        menuBar = new Texture(Gdx.files.internal("menu_bar.png"));
        grass = new Texture(Gdx.files.internal("grass.png"));

        advertisement = new Texture(Gdx.files.internal("advertisement.png"));
        estate = new Texture(Gdx.files.internal("estate.png"));
        market = new Texture(Gdx.files.internal("market.png"));
        menu = new Texture(Gdx.files.internal("menu.png"));
        reports = new Texture(Gdx.files.internal("reports.png"));
        staff = new Texture(Gdx.files.internal("staff.png"));
        stock = new Texture(Gdx.files.internal("stock.png"));

        background = new Texture(Gdx.files.internal("background.png"));
    }

    private TextureManager(){
        instance = this;
    }

    public static TextureManager getInstance(){
        if(instance == null)
            instance = new TextureManager();
        return instance;
    }
}