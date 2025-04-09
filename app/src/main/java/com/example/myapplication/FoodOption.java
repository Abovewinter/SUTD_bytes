package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public interface FoodOption extends Serializable {
    String getShopName();
    String getmalllocation();
    String getstorelocation();
    List<String> getcat();
    List<String> getFoodNames();
    List<Double> getPrices();
    List<Integer> getQuantities(); // optional, can return default values

    List<Integer> DrawableImage();
}
