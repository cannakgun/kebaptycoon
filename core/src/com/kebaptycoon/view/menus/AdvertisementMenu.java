package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.AdvertisementMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.entities.Advertisement;
import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.CustomerType;
import com.kebaptycoon.view.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

public class AdvertisementMenu extends Menu {

    private ArrayList<Advertisement> advertisementList;
    private int platfom = -1;
    private int quality = -1;
    private int focus = -1;

    public AdvertisementMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new AdvertisementMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);

        advertisementList = gameScreen.getGameLogic().getAdvertisementManager().getAdvertisementList();
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();
        batch.draw(resourceManager.textures.get("menu_backgrounds_advertisementBackground"), 300, 300);

        int y = 800;

        for (int i = 0; i < 3; i++) {

            y -= 140;

            if (i == 2) {
                batch.draw(resourceManager.textures.get("advertisements_newspaper"),
                        500, y, 70, 70);
                batch.draw(resourceManager.textures.get("advertisements_old"),
                        1040, y);
                batch.draw(resourceManager.textures.get("advertisements_goldStar"),
                        770, y);
            }
            else if (i == 1) {
                batch.draw(resourceManager.textures.get("advertisements_tv"),
                        500, y, 70, 70);
                batch.draw(resourceManager.textures.get("advertisements_silverStar"),
                        770, y);
                batch.draw(resourceManager.textures.get("advertisements_businessman"),
                        1040, y);
            }
            else {
                batch.draw(resourceManager.textures.get("advertisements_internet"),
                        500, y, 70, 70);
                batch.draw(resourceManager.textures.get("advertisements_bronzeStar"),
                        770, y);
                batch.draw(resourceManager.textures.get("advertisements_young"),
                        1040, y);
            }
        }

        switch(platfom){
            case 0:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        480, 640, 110, 110);
                break;
            case 1:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        480, 500, 110, 110);
                break;
            case 2:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        480, 360, 110, 110);
                break;
        }

        switch(quality){
            case 0:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        750, 640, 110, 110);
                break;
            case 1:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        750, 500, 110, 110);
                break;
            case 2:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        750, 360, 110, 110);
                break;
        }

        switch(focus){
            case 0:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        1020, 640, 110, 110);
                break;
            case 1:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        1020, 500, 110, 110);
                break;
            case 2:
                batch.draw(resourceManager.textures.get("menu_frame"),
                        1020, 360, 110, 110);
                break;
        }

        heading2Font.draw(batch, "Platform", 450 , 800);
        heading2Font.draw(batch, "Kalite", 740 , 800);
        heading2Font.draw(batch, "Odak", 1020 , 800);
        heading2Font.draw(batch, "Fiyat", 1300 , 800);
        heading2Font.draw(batch, calculatePriceText(), 1320 , 680);
        batch.draw(resourceManager.textures.get("advertisements_advertise"), 1240, 500, 250 ,90);
        batch.draw(resourceManager.textures.get("menu_line"), 670, 350);
        batch.draw(resourceManager.textures.get("menu_line"), 930, 350);
        batch.draw(resourceManager.textures.get("menu_line"), 1200, 350);
        batch.end();
    }

    public void setOption(int col, int row) {
        switch (col) {
            case 0:
                platfom = row;
                break;
            case 1:
                quality = row;
                break;
            case 2:
                focus = row;
                break;
        }
    }

    public Advertisement constructAdvertisement(int platform, int quality,int focus) {
        Advertisement.Quality q;
        Advertisement.Platform p;
        CustomerType c = new CustomerType();

        switch (platform) {
            case 0:
                p = Advertisement.Platform.Internet;
                break;
            case 1:
                p = Advertisement.Platform.Television;
                break;
            case 2:
                p = Advertisement.Platform.Newspaper;
                break;
            default:
                p = Advertisement.Platform.Internet;
        }
        switch (quality) {
            case 0:
                q = Advertisement.Quality.Low;
                break;
            case 1:
                q = Advertisement.Quality.Medium;
                break;
            case 2:
                q = Advertisement.Quality.High;
                break;
            default:
                q = Advertisement.Quality.Low;
        }

        c = (CustomerType)gameLogic.getCustomerManager().getCustomerTypes().toArray()[focus];

        Advertisement newAdvert = new Advertisement(q, Advertisement.Duration.Day,
                p, c);
        return newAdvert;
    }

    public int calculatePrice() {
        int base;
        switch (platfom) {
            case 0:
                base = 100;
                break;
            case 1:
                base = 300;
                break;
            case 2:
                base = 50;
                break;
            default:
                return -1;
        }

        if(quality == -1) return -1;

        if(focus == -1) return -1;

        int qualityMult = (2 * quality) + 1;

        return (qualityMult * base);
    }

    public String calculatePriceText() {
        int base;
        switch (platfom) {
            case 0:
                base = 100;
                break;
            case 1:
                base = 300;
                break;
            case 2:
                base = 50;
                break;
            default:
                return "N/A";
        }

        if(quality == -1) return "N/A";

        if(focus == -1) return "N/A";

        int qualityMult = (2 * quality) + 1;

        return (qualityMult * base) + " TL";
    }

    public int getPlatfom() {
        return platfom;
    }

    public void setPlatfom(int platfom) {
        this.platfom = platfom;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getFocus() {
        return focus;
    }

    public void setFocus(int focus) {
        this.focus = focus;
    }
}
