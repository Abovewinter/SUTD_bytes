package com.example.myapplication;
import java.io.Serializable;

public class FoodSelectionDetails implements Serializable {
    private FoodOption option;

    public FoodSelectionDetails(FoodOption option) {
        this.option = option;
    }

    public FoodOption getOption() {
        return option;
    }
}