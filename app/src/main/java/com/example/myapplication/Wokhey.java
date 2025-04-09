package com.example.myapplication;

import java.util.Arrays;
import java.util.List;

public class Wokhey implements FoodOption {
    @Override
    public String getShopName() {
        return "Wok Hey";
    }

    @Override
    public List<String> getFoodNames() {
        return Arrays.asList("Egg Fried Rice", "Fried Udon", "Ramen");
    }

    @Override
    public List<Double> getPrices() {
        return Arrays.asList(5.0, 6.0, 5.5);
    }

    @Override
    public List<Integer> getQuantities() {
        return Arrays.asList(1, 1, 1);
    }

}
