package com.kebaptycoon.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ResourceManager {

    public HashMap<String, Texture> textures;
    public HashMap<String, Animation> animations;

    private boolean isLoaded = false;

    public void loadAssets(){

        if (isLoaded) return;

        textures = new HashMap<String, Texture>();
        animations = new HashMap<String, Animation>();


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
            textures.put(prefix + textureFile.nameWithoutExtension(), new Texture(textureFile));
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
        }
        
        Animation newAnim = new Animation(Globals.ANIMATION_DURATION_PER_FRAME, frameTextures);
        newAnim.setPlayMode(Animation.PlayMode.LOOP);

        animations.put(past.substring(0, past.length()-1), newAnim);
    }
}