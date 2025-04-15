package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CartActivity extends Activity {
    double deliveryFee = 1.50;
    double grandTotal = 0.0;
    String redeem_distance = "short distance";
    List<Double> pricelist = new ArrayList<>();
    List<CartItem> cartItems;
    List<Button> addButtons = new ArrayList<>();
    List<Button> removeButtons = new ArrayList<>();


    private final String sharedPrefFile = "com.example.android.cartsaver";
    public static final String KEY = "MyKey";
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Navigation.setupNavigation(this);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        cartItems = (List<CartItem>) getIntent().getSerializableExtra("cart_items");

        // Reference the scrollable container inside ScrollView
        LinearLayout cartOrderLayout = findViewById(R.id.cart_order);
        TextView storename = findViewById(R.id.store_name_cart);

        if (cartItems == null) {
            String cartJson = mPreferences.getString(KEY, null);
            if (cartJson != null) {
                Gson gson = new Gson();
                CartItem[] cartArray = gson.fromJson(cartJson, CartItem[].class);
                cartItems = new ArrayList<>(Arrays.asList(cartArray));
            }
        }

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            View cartItemView = getLayoutInflater().inflate(R.layout.item_cart, null);
            cartOrderLayout.addView(cartItemView);

            Button add1 = cartItemView.findViewById(R.id.add_1);
            Button remove1 = cartItemView.findViewById(R.id.remove_1);
            TextView quantity = cartItemView.findViewById(R.id.food_quantity_cart);

            TextView foodname = cartItemView.findViewById(R.id.food_name_cart);
            TextView deliveryprice = findViewById(R.id.delivery_fee_cart);
            TextView distance = findViewById(R.id.reedeming_distance_cart);
            TextView price = cartItemView.findViewById(R.id.food_price_cart);
            TextView totalprice = findViewById(R.id.total_price_cart);

            addButtons.add(add1);
            removeButtons.add(remove1);

            storename.setText(item.getStoreName());
            foodname.setText(item.getFoodName());
            deliveryprice.setText(String.format("$%.2f", deliveryFee));
            distance.setText(redeem_distance);
            price.setText("$" + String.format("%.2f", item.getTotalPrice()));

            //one element array -> each loop holds diferrent quantity for each item
            final int[] itemQuantity = {item.getQuantity()};
            quantity.setText(String.valueOf(itemQuantity[0]));
            recalculateTotal(totalprice);


            // Increase quantity
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemQuantity[0]++;
                item.setQuantity(itemQuantity[0]);
                quantity.setText(String.valueOf(itemQuantity[0]));

                double pricing = itemQuantity[0] * item.getTotalPrice();
                price.setText("$" + String.format("%.2f", pricing));
                pricelist.add(pricing);

                recalculateTotal(totalprice);
            }
        });

        //decrease quantity
        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemQuantity[0] > 1) {

                    itemQuantity[0]--;
                    item.setQuantity(itemQuantity[0]);
                    quantity.setText(String.valueOf(itemQuantity[0]));

                    double pricing = itemQuantity[0] * item.getTotalPrice();

                    price.setText("$" + String.format("%.2f", pricing));
                    pricelist.remove(pricing);

                   recalculateTotal(totalprice);
                }

                else {
                    itemQuantity[0] = 0;
                    item.setQuantity(itemQuantity[0]);

                    cartItemView.setVisibility(View.GONE);

                    recalculateTotal(totalprice);
                }
            }
        });


        //confirm button
        Button confirmButton = findViewById(R.id.confirm_cart);
        EditText location = findViewById(R.id.meet_up_location);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationtext = location.getText().toString();
                if (locationtext.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Enter your location!", Toast.LENGTH_SHORT).show();
                } else if (cartItems == null || itemQuantity[0] == 0) {
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "Request successfully sent!", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                    Gson gson = new Gson();
                    String finaljson = gson.toJson(cartItems);
                    preferencesEditor.putString("confirmed_cart",finaljson);
                    preferencesEditor.putString("meetup_location", locationtext);
                    preferencesEditor.apply();

                    preferencesEditor.remove(KEY);
                    preferencesEditor.apply();
                    cartItems.clear();

                    Intent intent = new Intent(CartActivity.this, MessagesActivity.class);
                    startActivity(intent);
                }
            }
        });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void recalculateTotal(TextView totalView) {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            if (item.getQuantity() > 0) {
                subtotal += item.getTotalPrice() * item.getQuantity();
            }
        }
        double finalTotal = subtotal + deliveryFee;
        totalView.setText(String.format("$%.2f", finalTotal));
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        Gson gson = new Gson();
        String cartJson = gson.toJson(cartItems);

        // Save to SharedPreferences
        preferencesEditor.putString(KEY, cartJson);
        preferencesEditor.apply();
    }

}