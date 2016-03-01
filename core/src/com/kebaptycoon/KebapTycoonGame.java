package com.kebaptycoon;

import com.badlogic.gdx.Game;
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
