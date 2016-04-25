package com.kebaptycoon.model.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.kebaptycoon.utils.ResourceManager;

import java.util.Random;

public class SoundManager {

    private static ResourceManager resourceManager;
    private static Random random;
    private static Music currentMusic;
    private static Music backgroundMusic;

    public SoundManager(ResourceManager resourceManager){
        this.resourceManager = resourceManager;
    }

    public static void play(String categoryName){

        if(resourceManager.sounds.get(categoryName) == null ||
                resourceManager.sounds.get(categoryName).size() == 0)
            return;

        random = new Random();
        int randomIndex = random.nextInt(resourceManager.sounds.get(categoryName).size());
        if(currentMusic == null || !currentMusic.isPlaying()) {
            currentMusic = resourceManager.sounds.get(categoryName).get(randomIndex);
            currentMusic.play();
        }
    }

    public static void startBackgroundMusic(){
        backgroundMusic = resourceManager.sounds.get("background").get(0);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }
}
