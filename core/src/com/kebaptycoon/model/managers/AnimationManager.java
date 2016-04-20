package com.kebaptycoon.model.managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.kebaptycoon.model.entities.Entity;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Orientation;
import com.kebaptycoon.model.entities.Person;
import com.kebaptycoon.utils.ResourceManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AnimationManager {

    private ResourceManager resourceManager;
    private HashMap<Entity, Boolean> past;

    public AnimationManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        past = new HashMap<Entity, Boolean>();
    }

    public void autoSetUp(ArrayList<Entity> list) {
        for(Entity e: list) {
            if (!past.containsKey(e)) {
                setUpAnimations(e);
                past.put(e, true);
            }
        }
    }

    public void setUpAnimations(Entity entity) {
        if (entity instanceof Furniture) {
            setUpAnimations((Furniture) entity);
            return;
        }

        if (entity instanceof Person) {
            setUpAnimations((Person) entity);
            return;
        }

        String name = entity.getName();
        entity.setAnimation(resourceManager.animations.get(name));
    }

    private void setUpAnimations(Furniture furniture) {
        String name = furniture.getName();
        furniture.resetAnimations();

        for(Orientation orientation: Orientation.values()) {
            furniture.getAnimations().put(orientation,resourceManager.animations
                    .get(name + "_" + orientation.toString().toLowerCase()));
        }
    }

    private void setUpAnimations(Person person) {
        String name = person.getName();
        person.resetAnimations();

        for(Orientation orientation: Orientation.values()) {
            HashMap<Person.AnimationState, Animation> stateAnimations =
                    new HashMap<Person.AnimationState, Animation>();

            for(Person.AnimationState state: Person.AnimationState.values()) {
                stateAnimations.put(state, resourceManager.animations
                        .get(name + "_" + orientation.toString().toLowerCase()
                                + "_" + state.toString().toLowerCase()));
            }

            person.getAnimations().put(orientation,stateAnimations);
        }
    }
}
