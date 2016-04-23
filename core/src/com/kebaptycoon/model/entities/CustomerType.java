package com.kebaptycoon.model.entities;

import java.util.ArrayList;

public class CustomerType {

    String              displayName;
    String              texureName;
    String              spriteName;
    int 				waitingTime;
    int 				budget;
    ArrayList<String> likes;
    ArrayList<String> 	dislikes;

    public CustomerType() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTexureName() {
        return texureName;
    }

    public void setTexureName(String texureName) {
        this.texureName = texureName;
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
