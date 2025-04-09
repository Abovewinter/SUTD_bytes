package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;


public class PickupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        Navigation.setupNavigation(this); // Setup bottom navigation bar
        // Retrieve the order object passed from RequestActivity
        Order neworder = (Order) getIntent().getSerializableExtra("Selected Order");

        double Total = 0.0; // Initialize total cost
        // Extract data from Order object
        List<String> names = neworder.getFoodNames();
        Log.d("TAG", names.toString());
        List<Integer> quantities = neworder.getQuantities();
        List<Double> prices = neworder.getPrices();
        List<Integer> images = neworder.DrawableImage();
        // Initialize views
        TextView storeTitle = findViewById(R.id.StoreTitle);
        TextView malllocation = findViewById(R.id.mall_location);
        TextView storelocation = findViewById(R.id.store_location);
        TextView totalprice = findViewById(R.id.totalcost);
        ImageView storepic = findViewById(R.id.store_pic);
        LinearLayout container = findViewById(R.id.orderListLayout);
        Button btn_accept = findViewById(R.id.btn_accept);
        // Set store info at the top
        malllocation.setText(neworder.getmalllocation());
        storeTitle.setText("Store: "+ neworder.getShopName());
        storelocation.setText("Location: "+ neworder.getstorelocation());
        storepic.setImageResource(neworder.DrawableImage().get(0));
        // Dynamically add item in scroll view
        for (int i = 0; i < names.size(); i++) {
            View itemView = getLayoutInflater().inflate(R.layout.item_order, null);
            ImageView img = itemView.findViewById(R.id.item_image);
            TextView name = itemView.findViewById(R.id.item_name);
            TextView qty = itemView.findViewById(R.id.item_quantity);
            TextView price = itemView.findViewById(R.id.item_price);
            Total = (quantities.get(i)* prices.get(i)) + Total;
            name.setText(names.get(i));
            qty.setText("Qty: " + quantities.get(i));
            price.setText(String.format("$%.2f", prices.get(i)));
            img.setImageResource(images.get(0));
            container.addView(itemView);
        }
        // Set total cost text
        totalprice.setText("Total cost: $"+ String.valueOf(Total));
        // Handle accept button click
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickupActivity.this, MessagesActivity.class);
                startActivity(intent);
                Toast.makeText(PickupActivity.this, "Request accepted", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}