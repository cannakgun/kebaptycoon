package com.kebaptycoon.model.entities;

public class Emotion {
    public enum Type {
        Happy,
        Sad,
        Excited,
        Expensive
    }

    private Person owner;
    private Type type;

    public Emotion(Person owner, Type type) {
        this.owner = owner;
        this.type = type;
    }

    public Person getOwner() {
        return owner;
    }

    public Type getType() {
        return type;
    }
}
