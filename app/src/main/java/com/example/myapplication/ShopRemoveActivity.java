package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;


public class ShopRemoveActivity extends Activity {

    private ListView requestListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop_remove);

        requestListView = findViewById(R.id.requestListView);

        // Fake data for requests
        ArrayList<HashMap<String, String>> requestList = new ArrayList<>();
        HashMap<String, String> request1 = new HashMap<>();
        request1.put("location", "Block 57 Lobby");
        request1.put("distance", "0.5 km");
        requestList.add(request1);

        HashMap<String, String> request2 = new HashMap<>();
        request2.put("location", "Canteen A");
        request2.put("distance", "1.2 km");
        requestList.add(request2);

        HashMap<String, String> request3 = new HashMap<>();
        request3.put("location", "Hostel Gate 3");
        request3.put("distance", "0.8 km");
        requestList.add(request3);

        // Define keys for the SimpleAdapter
        String[] from = {"location", "distance"};
        int[] to = {R.id.locationText, R.id.distanceText};

        // Set up the adapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                requestList,
                R.layout.request_item, // Layout for each list item
                from,                   // Keys from the map
                to                      // TextViews to bind the data
        );

        // Set the adapter to the ListView
        requestListView.setAdapter(adapter);

        // Handle item click
        requestListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the clicked item
            HashMap<String, String> selectedRequest = requestList.get(position);
            String location = selectedRequest.get("location");
            String distance = selectedRequest.get("distance");

            // Show a toast with the clicked item's data
            Toast.makeText(this, "Clicked: " + location + " - " + distance, Toast.LENGTH_SHORT).show();
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}