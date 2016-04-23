package com.kebaptycoon.model.managers;

import com.kebaptycoon.utils.ResourceManager;

public class SoundManager {

    private static ResourceManager resourceManager;
    public SoundManager(ResourceManager resourceManager){
        this.resourceManager = resourceManager;
    }
    public static void play(String effectName){
        resourceManager.sounds.get(effectName).play();
    }
}
