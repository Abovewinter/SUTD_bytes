package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomePageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage); // ← your current layout file

        LinearLayout menuProfile = findViewById(R.id.menu_profile);

        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 🧭 Navigate to UserProfileActivity
                Intent intent = new Intent(HomePageActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
