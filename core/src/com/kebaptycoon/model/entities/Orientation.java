package com.kebaptycoon.model.entities;

import com.badlogic.gdx.math.Vector3;

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
            default:
                return new Vector3(0,0,0);
        }
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
            default:
                return null;
        }
    }

    public static Orientation fromVector(Vector3 vector) {
        float absX = Math.abs(vector.x);
        float absY = Math.abs(vector.y);

        if(absX > absY) {
            if (vector.x >= 0) {
                return East;
            }
            else {
                return West;
            }
        }
        else {
            if (vector.y >= 0) {
                return North;
            }
            else {
                return South;
            }
        }
    }
}
