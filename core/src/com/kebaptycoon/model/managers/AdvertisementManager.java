package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Advertisement;

import java.util.ArrayList;

public class AdvertisementManager {

    private ArrayList<Advertisement> advertisementList;

    public AdvertisementManager() {

        advertisementList = new ArrayList<Advertisement>();
    }

    public ArrayList<Advertisement> getAdvertisementList() {
        return advertisementList;
    }
}
