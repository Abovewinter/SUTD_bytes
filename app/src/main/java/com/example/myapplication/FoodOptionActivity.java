package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            TextView storeTitle = findViewById(R.id.StoreTitle);
            storeTitle.setText(option.getShopName());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
}
