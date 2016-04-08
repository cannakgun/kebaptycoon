package com.kebaptycoon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {

    public Texture dishSelectionScreenBackground;
    public Texture gameScreenBackground;
    private static TextureManager instance;

    public void loadTextures(){

        dishSelectionScreenBackground = new Texture(Gdx.files.internal("dishSelectionScreen.png"));
        gameScreenBackground = new Texture(Gdx.files.internal("gameScreen.png"));
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