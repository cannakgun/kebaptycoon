package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kebaptycoon.controller.menuControllers.FriendsMenuController;
import com.kebaptycoon.controller.menuControllers.MenuController;
import com.kebaptycoon.model.managers.FacebookFriendManager;
import com.kebaptycoon.utils.Globals;
import com.kebaptycoon.view.screens.GameScreen;

import java.io.IOException;
import java.util.ArrayList;

public class FriendsMenu extends Menu{

    FacebookFriendManager facebookFriendManager;
    ArrayList<FacebookFriendManager.FacebookFriend> facebookFriends;
    int frame = 0;

    public FriendsMenu(GameScreen gameScreen) {

        super(gameScreen);
        MenuController mc = new FriendsMenuController(gameScreen, this);
        GestureDetector gd = new GestureDetector(mc);
        InputProcessor ip = mc;
        InputMultiplexer mul = new InputMultiplexer(gd, ip);

        super.menuController = mul;
        Gdx.input.setInputProcessor(menuController);
        facebookFriendManager = gameScreen.getGameLogic().getFacebookFriendManager();

        currentPage = 0;
    }

    @Override
    public void render(SpriteBatch batch, Viewport viewPort) {
        batch.begin();
        batch.draw(resourceManager.textures.get("menu_background"), 300, 300);

        heading1Font.draw(batch, Globals.FRIENDS_MENU_TITLE, 835, 920);
        if (frame == 0){
            heading2Font.draw(batch, "Yükleniyor...", 700 , 750);
        }else{
            if(!facebookFriendManager.isLoaded()){
                try {
                    facebookFriends = facebookFriendManager.getFriendsFromFacebook();
                    facebookFriendManager.setLoaded(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                for(int i = 0; i < facebookFriends.size(); i++){
                    if(facebookFriends.get(i).getProfilePicture() != null) {
                        batch.draw(facebookFriends.get(i).getProfilePicture(), 550, 550 + i * 140, 100, 100);
                    }
                    heading3Font.draw(batch, facebookFriends.get(i).getName(), 700, 600 + i * 140);
                }
            }
        }
        batch.end();
        frame++;
    }
}
