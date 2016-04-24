package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Emotion;
import com.kebaptycoon.model.entities.Person;

import java.util.ArrayList;

public class EmotionManager {

    private static final int DURATION = 60;

    private static ArrayList<Emotion> emotionArrayList = new ArrayList<Emotion>();
    private static ArrayList<Integer> expireList = new ArrayList<Integer>();

    public static void addEmotion(Emotion emotion) {
        emotionArrayList.add(emotion);
        expireList.add(DURATION);
    }

    public static void decayEmotions() {
        for (int i = 0; i < emotionArrayList.size(); i++) {
            expireList.set(i, expireList.get(i) - 1);
        }

        expireEmotions();
    }

    public static void disposeEmotion(Person p) {
        int ind = -1;
        for (int i = 0; i < emotionArrayList.size(); i++) {
            if(emotionArrayList.get(i).getOwner() == p) {
                ind = i;
                break;
            }
        }

        if(ind == -1) return;

        emotionArrayList.remove(ind);
        expireList.remove(ind);
    }

    private static void expireEmotions() {
        boolean loop = true;
        while (loop) {
            loop = (expireList.size() > 0 && expireList.get(0) <= 0);
            if (loop) {
                emotionArrayList.remove(0);
                expireList.remove(0);
            }
        }
    }

    public static ArrayList<Emotion> getEmotionArrayList() {
        return new ArrayList<Emotion>(emotionArrayList);
    }

    public static void flushEmotions() {
        emotionArrayList.clear();
        expireList.clear();
    }
}
