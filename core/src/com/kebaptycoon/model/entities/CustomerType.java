package com.kebaptycoon.model.entities;

import java.util.ArrayList;

/**
 * Created by sophy on 22/04/2016.
 */
public class CustomerType {

    String              spriteName;
    int 				waitingTime;
    int 				budget;
    ArrayList<String> likes;
    ArrayList<String> 	dislikes;

    public CustomerType() {

    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<String> getDislikes() {
        return dislikes;
    }

    public void setDislikes(ArrayList<String> dislikes) {
        this.dislikes = dislikes;
    }
}
