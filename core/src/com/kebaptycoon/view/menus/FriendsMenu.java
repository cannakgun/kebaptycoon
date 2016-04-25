package com.kebaptycoon.view.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        batch.draw(resourceManager.textures.get("menu_backgrounds_friendsBackground"), 300, 300);
        int min = 0;
        if(facebookFriends == null){
            try {
                heading2Font.draw(batch, "Yükleniyor...", 820 , 670);
                facebookFriends = facebookFriendManager.getFacebookFriends();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {
            min = Math.min((currentPage + 1) * 3, facebookFriends.size());

            for(int i = currentPage * 3; i < min; i++){
                if(facebookFriends.get(i).getProfilePicture() != null) {
                    batch.draw(facebookFriends.get(i).getProfilePicture(), 550 + ((i%3) * 350), 650, 100, 100);

                    String name = facebookFriends.get(i).getName();

                    if(facebookFriends.get(i).getName().contains(" "))
                        name = name.substring(0, facebookFriends.get(i).getName().indexOf(" "));

                    heading3Font.draw(batch, name, 550 + ((i%3) * 350), 630);
                    heading3Font.draw(batch, "Seviye: 1", 550 + ((i%3) * 350), 600);
                    heading3Font.draw(batch, "Günlük Kazanç:", 550 + ((i%3) * 350), 550);
                    heading3Font.draw(batch, i * 100 + " TL", 550 + ((i%3) * 350), 530);

                }
            }
            if (facebookFriends.size() - currentPage * 3 >= 3){
                for(int i = 0; i < 2; i++){
                    batch.draw(resourceManager.textures.get("menu_lineShort"), 550 + ((i%3) * 350) + 200, 450);
                }
            }else if(facebookFriends.size() - currentPage * 3 >= 2){
                batch.draw(resourceManager.textures.get("menu_lineShort"), 550 + 200, 450);
            }

        }

        batch.end();
    }

    public void changeCurrentPage(int delta) {
        int pages = ((facebookFriends.size() - 1) / 3) + 1;
        this.currentPage = (currentPage + pages + delta) % pages;
    }
}
