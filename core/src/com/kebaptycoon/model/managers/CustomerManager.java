package com.kebaptycoon.model.managers;

import com.kebaptycoon.model.entities.Advertisement;
import com.kebaptycoon.model.entities.Customer;
import com.kebaptycoon.model.entities.CustomerPack;
import com.kebaptycoon.model.entities.Venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CustomerManager {

    private final static float MAX_POPULARITY = 1f;
    private final static float MIN_POPULARITY = 0.01f;
    private final static float SPAWN_CHANCE = 0.01f;
    private final static float SIZE_CHANCE = 0.3f;
    private final static float DECAY_RATE = -0.01f;

    HashMap<Customer.Type, Float> popularities;
    private Random random;

    public CustomerManager() {
        random = new Random();
    }

    public HashMap<Customer.Type, Float> getPopularities() {
        return popularities;
    }

    public Set<Customer.Type> getCustomerTypes() {
        return popularities.keySet();
    }

    public void decayPopularity() {
        for(Map.Entry<Customer.Type, Float> entry: popularities.entrySet()) {
            increasePopularity(entry.getKey(), DECAY_RATE);
        }
    }

    public void increasePopularity(Customer.Type type, float rate) {
        float old = popularities.get(type);
        float clamp = Math.max(MIN_POPULARITY, Math.min(MAX_POPULARITY, old + rate));
        popularities.put(type, clamp);
    }

    public void applyAdvertisement(Advertisement ad) {
        for(Map.Entry<Customer.Type, Float> entry: popularities.entrySet()) {
            increasePopularity(entry.getKey(), ad.getAbsoluteEffect(entry.getKey()));
        }
    }

    public void generateCustomers(ArrayList<Venue> venueList) {
        float sum = popularitySum();
        for (Venue venue:venueList) {
            if (random.nextFloat() < (SPAWN_CHANCE * sum)) continue;

            CustomerPack newCustomer = generateRandomCustomerPack();

            for(Customer customer: newCustomer.getCustomers()){
                customer.setPosition(venue.spawnPosition);
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

        ArrayList<Customer.Type> types = new ArrayList<Customer.Type>();
        int waiting = 0;
        int budget = 0;

        for (int i = 0; i < size; i++) {
            Customer.Type newType = weighedRandomType();
            types.add(newType);
            waiting = Math.max(waiting, newType.getWaitingTime());
            budget = Math.max(budget, newType.getBudget());
        }

        ArrayList<Customer> newPack = new ArrayList<Customer>();

        for (int i = 0; i < size; i++) {
            Customer.Type t = types.get(i);
            Customer newCustomer = new Customer(10, t.getSpriteName(), t, waiting, budget);
            newPack.add(newCustomer);
        }

        return new CustomerPack(newPack);
    }

    /**
     * Creates a random customer type weighted by their popularity
     * @return Random generated customer type
     */
    private Customer.Type weighedRandomType() {
        float sum = popularitySum();

        if (sum == 0f) return null;

        float randomNum = random.nextFloat() * sum;
        float currentSum = 0;

        for(Map.Entry<Customer.Type, Float> entry: popularities.entrySet()) {
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
        int sum = 0;
        for(Map.Entry<Customer.Type, Float> entry: popularities.entrySet())
            sum += entry.getValue();
        return sum;
    }
}
