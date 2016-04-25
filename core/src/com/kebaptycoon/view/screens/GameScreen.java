package com.kebaptycoon.view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kebaptycoon.controller.screenControllers.GameScreenController;
import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.CustomerPack;
import com.kebaptycoon.model.entities.Emotion;
import com.kebaptycoon.model.entities.Entity;
import com.kebaptycoon.model.entities.Recipe;
import com.kebaptycoon.model.entities.Venue;
import com.kebaptycoon.model.logic.GameLogic;
import com.kebaptycoon.model.logic.StartingDish;
import com.kebaptycoon.model.managers.EmotionManager;
import com.kebaptycoon.model.managers.SoundManager;
import com.kebaptycoon.utils.IsometricHelper;
import com.badlogic.gdx.utils.viewport.*;
import com.kebaptycoon.utils.Pair;
import com.kebaptycoon.utils.ResourceManager;
import com.kebaptycoon.view.menus.Menu;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameScreen implements Screen{

    private ResourceManager                     resourceManager;
    private GameScreenController                gameScreenController;
    private GameLogic                           gameLogic;

    private Venue                               currentVenue;
	
	private Matrix4 		                    isoTransform = null;
	private Matrix4			                    invIsotransform = null;
    private Matrix4			                    id = null;
    private SpriteBatch		                    spriteBatch = null;
    private SpriteBatch		                    menuBatch = null;
    private OrthographicCamera                  worldCamera = null;
    private OrthographicCamera                  menuCamera = null;
	private float			                    tileWidth = 1.0f;
	private float			                    tileHeight = (float) Math.tan(IsometricHelper.Angle);
	private Texture                             background;
    private Viewport                            viewPortWorld;
    private Viewport                            viewPortMenu;
    private float                               maxZoom, minZoom = 0.5f;
    private float                               menuHeight;
    private Entity                              testEntity;
    private SoundManager                        soundManager;
    private ArrayList<Pair<String, Integer>>    menuBarItems;
    private StartingDish                        startingDish;

    private FreeTypeFontGenerator               heading2Generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter heading2Parameter;
    BitmapFont                                  heading2Font;

	public GameScreen(ResourceManager resourceManager, StartingDish startingDish) {

        this.resourceManager = resourceManager;
        this.startingDish = startingDish;
        gameLogic = new GameLogic(resourceManager);
        soundManager = new SoundManager(resourceManager);

        //Create menu bar items from JSON config file
        ObjectMapper mapper = new ObjectMapper();

        try {

            String menuBarJSON = Gdx.files.internal("defaults/MenuConfig.json").readString();

            menuBarItems = mapper.readValue(menuBarJSON,
                    new TypeReference<ArrayList<Pair<String, Integer>>>(){});

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		//Create Controller
		gameScreenController = new GameScreenController(this);
        GestureDetector gd = new GestureDetector(gameScreenController);
        InputProcessor ip = gameScreenController;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);
		Gdx.input.setInputProcessor(mul);

        heading2Generator = resourceManager.fonts.get("Boogaloo");
        heading2Parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        heading2Parameter.characters = "abcçdefgğhıijklmnoöpqrsştuüvwxyzABCÇDEFGĞHIİIJKLMNOÖPQRSŞTUÜVWXYZ0123456789.,:";
        heading2Parameter.size = 40;
        heading2Parameter.color = Color.WHITE;

        heading2Font = heading2Generator.generateFont(heading2Parameter);
        heading2Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        //Set up graphics
		GL20 gl = Gdx.graphics.getGL20();
		gl.glEnable(GL20.GL_BLEND);
		gl.glEnable(GL20.GL_TEXTURE_2D);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//Create sprite batch
        spriteBatch = new SpriteBatch();
        menuBatch = new SpriteBatch();

		//Identity Matrix
		id = new Matrix4();
		id.idt();

        //The worldCamera will show 10 tiles
        float camWidth = tileWidth * 10.0f;

        //For the height, we just maintain the aspect ratio
        float camHeight = tileHeight * 10;// * ((float)height / (float)width);

        worldCamera = new OrthographicCamera(camWidth, camHeight);
        menuCamera = new OrthographicCamera();
        viewPortMenu = new FitViewport(1920,1080, worldCamera);
        viewPortWorld = new FitViewport(1920,1080, menuCamera);

        setVenue(gameLogic.getVenueManager().getVenueList().get(0));

		/*/Create the isometric transform matrix
		isoTransform = new Matrix4();
		isoTransform.idt();
		isoTransform.translate(0.0f, 0.25f, 0.0f);
		isoTransform.scale((float)(Math.sqrt(2.0) / 2.0), (float)(Math.sqrt(2.0) / 4.0), 1.0f);
		isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);

		//... and the inverse isometric transform matrix
		invIsotransform = new Matrix4(isoTransform);
		invIsotransform.inv();*/

	}

    public void setVenue(Venue venue) {
        background = venue.background;

        currentVenue = venue;

        //The worldCamera will show 10 tiles
        float camWidth = tileWidth * 10.0f;

        worldCamera.position.set(camWidth / 2.0f, 0, 0);
        worldCamera.update();


        menuCamera.position.set(1920 / 2, 1080 / 2, 0);
        menuCamera.update();


        menuHeight = 200;
        maxZoom = calculateMaxZoom(new Vector2(background.getWidth(), background.getHeight()),
                new Vector2(viewPortWorld.getWorldWidth(), viewPortWorld.getWorldHeight()
                        - menuHeight));
        minZoom = Math.min(minZoom, maxZoom);

        worldCamera.zoom = maxZoom;
    }

    @Override
    public void show() {

    }

	@Override
	public void render(float delta)
	{
        gameLogic.update();

        clampCamera();
        worldCamera.update();

        //Clear the screen
        GL20 gl = Gdx.graphics.getGL20();
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //viewPortCam.apply();

        viewPortWorld.apply();
        spriteBatch.setProjectionMatrix(worldCamera.combined);
        //shapeRenderer.setProjectionMatrix(worldCamera.combined);

        //Set identity matrix as transform matrix
        //	so that sprites will be drawn as they are
        //spriteBatch.setTransformMatrix(id);
        //shapeRenderer.setTransformMatrix(id);
        spriteBatch.begin();
        renderMap(delta);
        renderEntities(delta);
        spriteBatch.end();

        viewPortMenu.apply();
        menuBatch.setProjectionMatrix(menuCamera.combined);

        menuBatch.begin();
        if(gameLogic.isPaused()){
            menuBatch.draw(resourceManager.textures.get("screens_shade"),0,0);
        }

        menuBatch.draw(resourceManager.textures.get("menu_bar"), 0, 0,
                1920, menuHeight);

        float unit = 1920/(menuBarItems.size() + 1);

        for (Pair<String, Integer> pair: menuBarItems) {
            float centerX = pair.getRight() * unit;
            float centerY = menuHeight / 2;
            Texture tx = resourceManager.textures.get("menu_" + pair.getLeft());
            float actualX = centerX - (tx.getWidth() / 2);
            float actualY = centerY - (tx.getHeight() / 2);
            menuBatch.draw(tx, actualX, actualY);
        }

        menuBatch.draw(resourceManager.textures.get("menu_backgrounds_clockBackground"), 1670, 920);
        menuBatch.draw(resourceManager.textures.get("menu_clock"), 1710, 1005, 50, 50);
        heading2Font.draw(menuBatch, ""+gameLogic.getTime(), 1790, 1050);

        menuBatch.draw(resourceManager.textures.get("menu_gold"), 1710, 930, 50, 50);
        heading2Font.draw(menuBatch, ""+gameLogic.getMoney() + " TL", 1790, 970);


        menuBatch.end();

        for(Menu menu: gameScreenController.getMenuStack()) {
            menu.render(menuBatch, viewPortMenu);
        }

		//Set isometric transform matrix as transform matrix
		//	so that sprites will be drawn after isometricly transformed
		//spriteBatch.setTransformMatrix(isoTransform);
		//spriteBatch.begin();
		//spriteBatch.end();

    }


	@Override
	public void resize(int width, int height) {

        viewPortWorld.update(width, height);
        viewPortMenu.update(width, height);
        menuCamera.position.set(menuCamera.viewportWidth / 2, menuCamera.viewportHeight/2,0);
	}

	private void renderMap(float delta)
	{
        spriteBatch.draw(background, 0, 0);
	}
	
	private void renderEntities(float delta)
	{
        delta = gameLogic.isPaused() ? 0f : delta;

		ArrayList<Entity> renderables = new ArrayList<Entity>();

        if(!gameLogic.isAfterHours()) {
            for (CustomerPack customerPack : currentVenue.getCustomers()) {
                renderables.addAll(customerPack.getCustomers());
                for(Customer c: customerPack.getCustomers()) {
                    if(c.getDish() != null)
                        renderables.add(c.getDish());
                }
            }

            renderables.addAll(currentVenue.getEmployees());
        }
        renderables.addAll(currentVenue.getFurnitures());

        Collections.sort(renderables, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                float s1 = (float)(Math.floor(o1.getPosition().x) + Math.floor(o1.getPosition().y) +
                        Math.floor(o1.getRender3DDelta().x) + Math.floor(o1.getRender3DDelta().y));
                float s2 = (float)(Math.floor(o2.getPosition().x) + Math.floor(o2.getPosition().y) +
                        Math.floor(o2.getRender3DDelta().x) + Math.floor(o2.getRender3DDelta().y));
                if (s1 < s2)
                    return 1;
                else if (s1 > s2)
                    return -1;
                else if(o1.getPosition().z + o1.getRender3DDelta().z
                        < o1.getPosition().z + o2.getRender3DDelta().z)
                    return -1;
                else if(o1.getPosition().z + o1.getRender3DDelta().z
                        > o1.getPosition().z + o2.getRender3DDelta().z)
                    return 1;
                return 0;
            }
        });

        for (Entity ent: renderables) {
            ent.render(spriteBatch, delta);
        }

        ArrayList<Emotion> emotions = EmotionManager.getEmotionArrayList();

        Texture bg = resourceManager.textures.get("emoticons_bubble");

        for (Emotion e: emotions) {
            if(!renderables.contains(e.getOwner())) continue;

            Vector3 iso = e.getOwner().getPosition().cpy().add(e.getOwner().getRender3DDelta())
                    .add(0, 0, 2);
            Vector2 view = IsometricHelper.project(iso);
            view.add(bg.getWidth()/2,0);

            spriteBatch.draw(bg, view.x, view.y);
            spriteBatch.draw(resourceManager.textures
                    .get("emoticons_" + e.getType().toString().toLowerCase()),
                    view.x, view.y);
        }
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

    public float getCameraZoom(){
        return worldCamera.zoom;
    }

	public void zoomCamera(Vector3 origin, float scale){

        Vector3 oldUnprojection = worldCamera.unproject(origin.cpy()).cpy();

        worldCamera.zoom = scale; //Larger value of zoom = small images, border view
		worldCamera.zoom = Math.min(maxZoom, Math.max(worldCamera.zoom, minZoom));
		worldCamera.update();

        Vector3 newUnprojection = worldCamera.unproject(origin.cpy()).cpy();

        worldCamera.position.add(oldUnprojection.cpy().add(newUnprojection.cpy().scl(-1f)));
	}

	public boolean moveCamera(float deltaX, float deltaY) {

        worldCamera.position.add(
                worldCamera.unproject(new Vector3(0, 0, 0))
                        .add(worldCamera.unproject(new Vector3(deltaX, deltaY, 0)).scl(-1f))
        );
		return true;
	}

    private void clampCamera() {

        float zoom = worldCamera.zoom;
        Vector3 prevPos = worldCamera.position;

        float renderWidth = viewPortWorld.getWorldWidth() * zoom;
        float renderHeight = (viewPortWorld.getWorldHeight() - menuHeight) * zoom;

        float minX = renderWidth / 2;
        float maxX = background.getWidth() - (renderWidth / 2);

        float minY = (renderHeight / 2) - (menuHeight * zoom / 2f);
        float maxY = background.getHeight() - (menuHeight * zoom / 2f) - (renderHeight / 2);

        float newX = (prevPos.x < minX) ? minX : (prevPos.x > maxX) ? maxX : prevPos.x;
        float newY = (prevPos.y < minY) ? minY : (prevPos.y > maxY) ? maxY : prevPos.y;

        worldCamera.position.set(newX, newY, worldCamera.position.z);
    }

    public Vector2 worldUnproject(float x, float y) {
        Vector3 touch = worldCamera.unproject(new Vector3(x, y, 0),
                viewPortWorld.getScreenX(), viewPortWorld.getScreenY(),
                viewPortWorld.getScreenWidth(), viewPortWorld.getScreenHeight());
        return new Vector2(touch.x, touch.y);
    }

    public float getMenuHeight() {
        return menuHeight;
    }

    public ResourceManager getResourceManager() {

        return resourceManager;
    }

    public Vector2 menuUnproject(float x, float y) {
        Vector3 touch = menuCamera.unproject(new Vector3(x, y, 0),
                viewPortMenu.getScreenX(),viewPortMenu.getScreenY(),
                viewPortMenu.getScreenWidth(),viewPortMenu.getScreenHeight());
        return new Vector2(touch.x, touch.y);
    }

    private float calculateMaxZoom(Vector2 bgSize, Vector2 screenSize)
    {
        float xRatio = bgSize.x / screenSize.x;
        float yRatio = bgSize.y / screenSize.y;

        return Math.min(xRatio, yRatio);
    }

	public void setInputProcessor(InputProcessor gestureDetector){

        Gdx.input.setInputProcessor(gestureDetector);
	}

    public GameScreenController getGameScreenController() {
        return gameScreenController;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public ArrayList<Pair<String, Integer>> getMenuBarItems(){
        return menuBarItems;
    }
    public Venue getCurrentVenue() {
        return currentVenue;
    }

    public void resetController() {
        GestureDetector gd = new GestureDetector(gameScreenController);
        InputProcessor ip = gameScreenController;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);
        Gdx.input.setInputProcessor(mul);
    }
}
