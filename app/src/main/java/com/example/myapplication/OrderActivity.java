package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OrderActivity extends Activity {
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        Navigation.setupNavigation(this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String user_email = currentUser.getEmail();
        // Get reference to the TextView
        TextView TextUser = findViewById(R.id.textUser);
        TextUser.setText("Logged in as: " + user_email);

        //Not implemented
        EditText FoodSearch = findViewById(R.id.foodsearch);
        Button btn_fastfood = findViewById(R.id.btn_fastfood);
        Button btn_dessert = findViewById(R.id.btn_dessert);
        Button btn_maincourse = findViewById(R.id.btn_maincourse);
        Button btn_others = findViewById(R.id.btn_others);
        //Food from trending and favourite
        Button btnKoi = findViewById(R.id.koi_home);
        Button btnWokhey = findViewById(R.id.wokhey_home);
        Button btnMc = findViewById(R.id.mc_home);
        //uncomment this if you want your old functionality
       btnKoi.setOnClickListener(v -> openFoodOptionActivity(new Koi()));
       // btnKoi.setOnClickListener(v -> openOrderDetailsActivity(new Koi()));

        btnWokhey.setOnClickListener(v -> openFoodOptionActivity(new Wokhey()));
        btnMc.setOnClickListener(v -> openFoodOptionActivity(new Mac()));

        
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
    private void openOrderDetailsActivity(FoodOption option) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("selection_details", new FoodSelectionDetails(option));
        startActivity(intent);
    }

}