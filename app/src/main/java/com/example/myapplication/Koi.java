package com.example.myapplication;

import java.util.Arrays;
import java.util.List;

public class Koi implements FoodOption{
    @Override
    public String getShopName() {
        return "KOI";
    }

    @Override
    public List<String> getFoodNames() {
        return Arrays.asList("Milk Tea", "Hazelnut Tea", "Oolong Tea");
    }

    @Override
    public List<Double> getPrices() {
        return Arrays.asList(3.5, 4.5, 3.8);
    }

    @Override
    public List<Integer> getQuantities() {
        return Arrays.asList(1, 1, 1);
    }
}
