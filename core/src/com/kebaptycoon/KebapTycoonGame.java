package com.kebaptycoon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kebaptycoon.view.screens.LoginScreen;

public class KebapTycoonGame extends Game {

    public static KebapTycoonGame instance;

	@Override
	public void create () {
        instance = this;
        this.setScreen(new LoginScreen());
	}

	@Override
	public void render () {
        super.render();
	}

    public static KebapTycoonGame getInstance(){
        return instance;
    }
}
