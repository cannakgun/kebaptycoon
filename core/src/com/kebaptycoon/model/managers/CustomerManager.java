package com.kebaptycoon.model.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kebaptycoon.model.entities.Advertisement;
import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.CustomerPack;
import com.kebaptycoon.model.entities.CustomerType;
import com.kebaptycoon.model.entities.Furniture;
import com.kebaptycoon.model.entities.Orientation;
import com.kebaptycoon.model.entities.Venue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CustomerManager {

    private final static float MAX_POPULARITY = 1f;
    private final static float MIN_POPULARITY = 0.01f;
    private final static float SPAWN_CHANCE_SCALE = 0.02f;
    private final static float SPAWN_CHANCE_FLAT = 0.002f;
    private final static float SIZE_CHANCE = 0.3f;
    private final static float DECAY_RATE = -0.01f;

    private final static int MAXIMUM_EXCEED = 2;

    HashMap<CustomerType, Float> popularities;
    private Random random;

    public CustomerManager() {
        random = new Random();
        popularities = new HashMap<CustomerType, Float>();

        List<CustomerType> types = new ArrayList<CustomerType>();

        ObjectMapper mapper = new ObjectMapper();

        try {

            String typeJSON = Gdx.files.internal("defaults/CustomerTypeList.json").readString();

            types = mapper.readValue(typeJSON,
                    new TypeReference<ArrayList<CustomerType>>() {
                    });

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (CustomerType type: types) {
            popularities.put(type, MIN_POPULARITY);
        }
    }

    public HashMap<CustomerType, Float> getPopularities() {
        return popularities;
    }

    public Set<CustomerType> getCustomerTypes() {
        return popularities.keySet();
    }

    public void decayPopularity() {
        for(Map.Entry<CustomerType, Float> entry: popularities.entrySet()) {
            increasePopularity(entry.getKey(), DECAY_RATE);
        }
    }

    public void increasePopularity(CustomerType type, float rate) {
        float old = popularities.get(type);
        float clamp = Math.max(MIN_POPULARITY, Math.min(MAX_POPULARITY, old + rate));
        popularities.put(type, clamp);
    }

    public void applyAdvertisement(Advertisement ad) {
        for(Map.Entry<CustomerType, Float> entry: popularities.entrySet()) {
            increasePopularity(entry.getKey(), ad.getAbsoluteEffect(entry.getKey()));
        }
    }

    public void generateCustomers(ArrayList<Venue> venueList) {
        float avg = popularitySum() / getCustomerTypes().size();
        for (Venue venue:venueList) {
            if(venue.getCustomers().size() - venue.getFurnitures(Furniture.Type.Table).size()
                    >= MAXIMUM_EXCEED)
                continue;
            if (random.nextFloat() > SPAWN_CHANCE_FLAT + (SPAWN_CHANCE_SCALE * avg)) continue;

            CustomerPack newCustomer = generateRandomCustomerPack();

            int i = 0;

            for(Customer customer: newCustomer.getCustomers()){
                customer.setPosition(Orientation.values()[i++].getUnitVector().add(venue.spawnPosition));
            }

            if (newCustomer != null)
                venue.getCustomers().add(newCustomer);
        }
    }

    /**
     * Creates a new and random customer pack
     * @return Created customer pack
     */
    private CustomerPack generateRandomCustomerPack() {
        int size = 1;
        for (int i = 0; i < 3; i++) {
            if(random.nextFloat() < SIZE_CHANCE)
                size++;
        }

        ArrayList<CustomerType> types = new ArrayList<CustomerType>();
        int waiting = 0;
        int budget = 0;

        for (int i = 0; i < size; i++) {
            CustomerType newType = weighedRandomType();
            types.add(newType);
            waiting = Math.max(waiting, newType.getWaitingTime());
            budget = Math.max(budget, newType.getBudget());
        }

        ArrayList<Customer> newPack = new ArrayList<Customer>();

        for (int i = 0; i < size; i++) {
            CustomerType t = types.get(i);
            Customer newCustomer = new Customer(5, t.getSpriteName(), t, waiting, budget);
            newPack.add(newCustomer);
        }

        return new CustomerPack(newPack);
    }

    /**
     * Creates a random customer type weighted by their popularity
     * @return Random generated customer type
     */
    private CustomerType weighedRandomType() {
        float sum = popularitySum();

        if (sum == 0f) return null;

        float randomNum = random.nextFloat() * sum;
        float currentSum = 0;

        for(Map.Entry<CustomerType, Float> entry: popularities.entrySet()) {
            float weight = entry.getValue();

            if (randomNum > currentSum && randomNum <= currentSum + weight)
                return entry.getKey();

            currentSum += weight;
        }


        //Should never reach here
        return null;
    }

    /**
     * Calculates the sum of the popularity scores
     * @return Sum of the popularity scores
     */
    private float popularitySum() {
        float sum = 0;
        for(Map.Entry<CustomerType, Float> entry: popularities.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }
}
