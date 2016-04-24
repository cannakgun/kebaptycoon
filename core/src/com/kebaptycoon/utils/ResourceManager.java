package com.kebaptycoon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourceManager {

    public HashMap<String, Texture> textures;
    public HashMap<String, Animation> animations;
    public HashMap<String, FreeTypeFontGenerator> fonts;
    public HashMap<String, ArrayList<Music>> sounds;

    private boolean isLoaded = false;

    public void loadAssets(){

        if (isLoaded) return;

        textures = new HashMap<String, Texture>();
        animations = new HashMap<String, Animation>();
        fonts = new HashMap<String, FreeTypeFontGenerator>();
        sounds = new HashMap<String, ArrayList<Music>>();

        /*FileHandle[] textureFiles = Gdx.files.internal("textures/").list();
        for(FileHandle topFile: textureFiles) {
            if (topFile.isDirectory())
                for (FileHandle file: topFile.list())
                    loadTexture(file, topFile.name());
            else
                loadTexture(topFile, null);
        }*/

        loadTextures("", Gdx.files.internal("textures/"));

        loadAnimations("", Gdx.files.internal("anim/"));

        loadFonts();

        loadSoundEffects();

        isLoaded = true;
    }

    private void loadTextures(String prefix, FileHandle folder) {
        FileHandle[] children = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });

        ArrayList<FileHandle> subFolders = new ArrayList<FileHandle>();
        ArrayList<FileHandle> textureFiles = new ArrayList<FileHandle>();

        for (FileHandle child:children) {
            if(child.isDirectory())
                subFolders.add(child);
            else
                textureFiles.add(child);
        }

        for (FileHandle subFolder: subFolders) {
            loadTextures(prefix + subFolder.name() + "_", subFolder);
        }

        for (FileHandle textureFile: textureFiles) {
            Texture tx = new Texture(textureFile);
            tx.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textures.put(prefix + textureFile.nameWithoutExtension(), tx);
        }
    }

    private void loadAnimations(String past, FileHandle folder)
    {
        FileHandle[] children = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });

        ArrayList<FileHandle> subFolders = new ArrayList<FileHandle>();
        ArrayList<FileHandle> frameFiles = new ArrayList<FileHandle>();

        for (FileHandle child:children) {
            if(child.isDirectory())
                subFolders.add(child);
            else
                frameFiles.add(child);
        }

        for (FileHandle subFolder: subFolders) {
            loadAnimations(past + subFolder.name() + "_", subFolder);
        }

        if (frameFiles.size() <= 0) return;

        TextureRegion[] frameTextures = new TextureRegion[frameFiles.size()];

        for (FileHandle frameFile: frameFiles) {
            frameTextures[new Integer(frameFile.nameWithoutExtension())]
                    = new TextureRegion(new Texture(frameFile));
            frameTextures[new Integer(frameFile.nameWithoutExtension())].getTexture()
                    .setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        
        Animation newAnim = new Animation(Globals.ANIMATION_DURATION_PER_FRAME, frameTextures);
        newAnim.setPlayMode(Animation.PlayMode.LOOP);

        animations.put(past.substring(0, past.length()-1), newAnim);
    }

    private void loadFonts(){
        FileHandle folder = Gdx.files.internal("fonts/");

        FileHandle[] children = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        });

        for (FileHandle child:children) {

            fonts.put(child.nameWithoutExtension(),
                    new FreeTypeFontGenerator(child));
        }
    }

    private void loadSoundEffects(){
        FileHandle folder = Gdx.files.internal("sounds/");

        FileHandle[] children = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".") && !name.endsWith("raw");
            }
        });

        for (FileHandle child : children) {

            FileHandle[] childFolders = child.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !name.startsWith(".") && !name.endsWith("raw");
                }
            });

            ArrayList<Music> effects = new ArrayList<Music>();

            for(FileHandle effect: childFolders){
                effects.add(Gdx.audio.newMusic(effect));
            }
            sounds.put(child.nameWithoutExtension(), effects);
        }

    }
}