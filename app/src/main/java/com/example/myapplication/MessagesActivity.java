package com.example.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Navigation.setupNavigation(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences mPreferences = getSharedPreferences("com.example.android.cartsaver", MODE_PRIVATE);
        String cartJson = mPreferences.getString("confirmed_cart", null);
        String location = mPreferences.getString("meetup_location", "No location provided");

        if (cartJson != null) {
            Gson gson = new Gson();
            CartItem[] cartArray = gson.fromJson(cartJson, CartItem[].class);
            List<CartItem> confirmedItems = new ArrayList<>(Arrays.asList(cartArray));

            LinearLayout cartOrderLayout = findViewById(R.id.cart_order);

            for (CartItem item : confirmedItems) {
                View cartItemView = getLayoutInflater().inflate(R.layout.item_cart, null);
                cartOrderLayout.addView(cartItemView);

                TextView foodname = cartItemView.findViewById(R.id.food_name_cart);
                TextView price = cartItemView.findViewById(R.id.food_price_cart);
                TextView quantity = cartItemView.findViewById(R.id.food_quantity_cart);

                Button addBtn = cartItemView.findViewById(R.id.add_1);
                Button removeBtn = cartItemView.findViewById(R.id.remove_1);

                foodname.setText(item.getFoodName());
                price.setText("$" + String.format("%.2f", item.getTotalPrice() * item.getQuantity()));
                quantity.setText(String.valueOf(item.getQuantity()));

                addBtn.setVisibility(View.GONE);
                removeBtn.setVisibility(View.GONE);
            }

            findViewById(R.id.confirm_cart).setVisibility(View.GONE);

            EditText locationField = findViewById(R.id.meet_up_location);
            locationField.setText(location);
            locationField.setFocusable(false);
            locationField.setClickable(false);
            locationField.setCursorVisible(false);
            locationField.setKeyListener(null);

            TextView totalPrice = findViewById(R.id.total_price_cart);
            TextView deliveryfee = findViewById(R.id.delivery_fee_cart);
            TextView voucher = findViewById(R.id.reedeming_distance_cart);

            double subtotal = 0.0;
            for (CartItem item : confirmedItems) {
                subtotal += item.getQuantity() * item.getTotalPrice();
            }
            double deliveryFee = 1.50;
            totalPrice.setText("$" + String.format("%.2f", subtotal + deliveryFee));
            deliveryfee.setText("$" + String.format("%.2f", deliveryFee));
            voucher.setText("Short distance");
        }
    }
}