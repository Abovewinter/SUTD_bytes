package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class FoodOptionActivity extends Activity {

    FoodOption option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.koi_order);
        Navigation.setupNavigation(this);
        option = (FoodOption) getIntent().getSerializableExtra("food_option");
        if (option != null) {
            //<-- changing of store info according to food selected-->
            TextView storeTitle = findViewById(R.id.StoreTitle);
            storeTitle.setText(option.getShopName());

            TextView malllocation = findViewById(R.id.mall_location);
            malllocation.setText(option.getmalllocation());

            TextView storelocation = findViewById(R.id.store_location);
            storelocation.setText(option.getstorelocation());

            //<-- changing of food categories -->
            TextView category1 = findViewById(R.id.food1_cat);
            category1.setText(option.getcat().get(0));

            TextView category2 = findViewById(R.id.food2_cat);
            category2.setText(option.getcat().get(1));

            //<-- changing of food options and prices-->
            TextView food_1 = findViewById(R.id.food1);
            food_1.setText(option.getFoodNames().get(0));

            TextView food_2 = findViewById(R.id.food2);
            food_2.setText(option.getFoodNames().get(1));

            TextView food1price = findViewById(R.id.food1_price);
            food1price.setText(String.format("$%.2f", option.getPrices().get(0)));

            TextView food2price = findViewById(R.id.food2_price);
            food2price.setText(String.format("$%.2f", option.getPrices().get(1)));

            //<-- changing of pictures -->
            ImageView storepic = findViewById(R.id.store_pic);
            //storepic.setImageDrawable(null);
            storepic.setImageResource(option.DrawableImage().get(0));

            ImageView food1 = findViewById(R.id.option1_pic);
            food1.setImageResource(option.DrawableImage().get(1));

            ImageView food2 = findViewById(R.id.option2_pic);
            food2.setImageResource(option.DrawableImage().get(2));


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
}
