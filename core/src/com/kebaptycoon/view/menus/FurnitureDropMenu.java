package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.FurnitureDropMenuController;
import com.kebaptycoon.controller.menuControllers.MarketMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.controller.menuControllers.StockMenuController;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Ingredient;
import com.kebaptycoon.model.entities.prototypes.FurniturePrototype;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.utils.IsometricHelper;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;

public class FurnitureDropMenu extends Menu {


    private FurniturePrototype furniture;
    private Vector2 deltaTouch;

    public FurnitureDropMenu(GameScreen gameScreen, FurniturePrototype furniture, Vector2 deltaTouch) {
        super(gameScreen);
        this.deltaTouch = deltaTouch;
        MenuController mc = new FurnitureDropMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        this.furniture = furniture;

    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {

        batch.begin();

        Vector2 renderPos = deltaTouch.cpy().scl(-1);
        renderPos.add(gameScreen.menuUnproject(Gdx.input.getX(), Gdx.input.getY()));

        batch.draw(resourceManager.textures.get("furnitures_" + furniture.name),
                renderPos.x, renderPos.y);

        batch.end();
    }

    public Vector3 getFurnitureWorldPosition() {

        Vector2 renderPos = deltaTouch.cpy().scl(-1).add(Gdx.input.getX(), Gdx.input.getY());
        renderPos = gameScreen.worldUnproject(renderPos.x, renderPos.y);

        Vector3 unprojected = IsometricHelper.unproject(renderPos, 0);

        unprojected.x = (float)Math.floor(unprojected.x);
        unprojected.y = (float)Math.floor(unprojected.y);
        unprojected.z = (float)Math.floor(unprojected.z);

        //TODO: Include furniture width / height to calculation

        return unprojected;
    }

    public FurniturePrototype getFurniture() {
        return furniture;
    }
}
