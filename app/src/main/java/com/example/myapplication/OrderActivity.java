package com.example.myapplication;

import android.app.Activity;
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

        Button btn_koi = findViewById(R.id.koi_home);
        Button btn_wokhey = findViewById(R.id.wokhey_home);
        Button btn_fastfood = findViewById(R.id.btn_fastfood);
        Button btn_dessert = findViewById(R.id.btn_dessert);
        Button btn_maincourse = findViewById(R.id.btn_maincourse);
        Button btn_others = findViewById(R.id.btn_others);
        Button btn_mc = findViewById(R.id.mc_home);

        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}