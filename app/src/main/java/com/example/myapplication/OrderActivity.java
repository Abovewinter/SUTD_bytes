package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        Navigation.setupNavigation(this);
        Button btn_fastfood = findViewById(R.id.btn_fastfood);
        Button btn_dessert = findViewById(R.id.btn_dessert);
        Button btn_maincourse = findViewById(R.id.btn_maincourse);
        Button btn_others = findViewById(R.id.btn_others);
        //Food from trending and favourite
        Button btnKoi = findViewById(R.id.koi_home);
        Button btnWokhey = findViewById(R.id.wokhey_home);
        Button btnMc = findViewById(R.id.mc_home);

        btnKoi.setOnClickListener(v -> openFoodOptionActivity(new Koi()));
        btnWokhey.setOnClickListener(v -> openFoodOptionActivity(new Wokhey()));
        //btnMc.setOnClickListener(v -> openFoodOptionActivity(new Mc()));

        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openFoodOptionActivity(FoodOption option) {
        Intent intent = new Intent(this, FoodOptionActivity.class);
        intent.putExtra("food_option", option);
        startActivity(intent);
    }
}