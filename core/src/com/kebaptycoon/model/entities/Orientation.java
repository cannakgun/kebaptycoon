package com.kebaptycoon.model.entities;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Can Akgün on 16.4.2016.
 */
public enum Orientation {
    East,
    North,
    West,
    South;

    public Vector3 getUnitVector() {
        switch (this) {
            case East:
                return new Vector3(1,0,0);
            case North:
                return new Vector3(0,1,0);
            case West:
                return new Vector3(-1,0,0);
            case South:
                return new Vector3(0,-1,0);
        }
        return null;
    }

    public Orientation getReverse() {
        switch (this) {
            case East:
                return West;
            case North:
                return South;
            case West:
                return East;
            case South:
                return North;
        }
        return null;
    }
}
