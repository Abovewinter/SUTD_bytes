package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class CartActivity extends Activity {
    int quantity1 = 1;
    List<Double> prices1 = Arrays.asList(5.8,3.5,5.5);
    List<Double> prices2 = Arrays.asList(4.9,2.2,6.0);
    CartItem cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Navigation.setupNavigation(this);
        cartItems = (CartItem) getIntent().getSerializableExtra("cart_items");

        Button add1 = findViewById(R.id.add_1);
        Button remove1 = findViewById(R.id.remove_1);
        TextView quantity = findViewById(R.id.food_quantity_cart);
        //initial quantity 1
        quantity.setText(String.valueOf(quantity1));

        TextView storename = findViewById(R.id.store_name_cart);
        TextView foodname = findViewById(R.id.food_name_cart);
        TextView price = findViewById(R.id.food_price_cart);
        TextView totalprice = findViewById(R.id.total_price_cart);


        if (cartItems != null) {
            CartItem firstItem = cartItems;

            storename.setText(firstItem.getStoreName());
            foodname.setText(firstItem.getFoodName());
            quantity.setText(firstItem.getQuantity() + "x");
            price.setText("$" + String.format("%.2f", firstItem.getTotalPrice()));

            // Calculate total + delivery fee
            double deliveryFee = 2.50;
            double total = firstItem.getTotalPrice() + deliveryFee;

            totalprice.setText("$" + String.format("%.2f", total));

        }

        // Increase quantity
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity1 += 1;
                quantity.setText((String.format("%dx", quantity1)));

                double pricing = quantity1 * prices1.get(0);
                price.setText("$" + pricing);

                double deliveryFee = 2.50;
                double total = quantity1 * prices1.get(0) + deliveryFee;
                totalprice.setText("$" + total);
            }
        });

        //decrease quantity
        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity1 > 0) {
                    quantity1 -= 1;

                    double total = quantity1 * prices2.get(0);
                    totalprice.setText("$" + total);
                }
                quantity.setText((String.format("%dx", quantity1)));
                if (quantity1 == 0){
                    storename.setText("NIL");
                    foodname.setText("Your cart is empty");
                    price.setText("NIL");
                    totalprice.setText("NIL");
                }
            }
        });

        //update total price
       // TextView totalPrice = findViewById(R.id.total_price_cart);
       // double total = quantity1 * option.getPrices().get(0) + quantity2 * option.getPrices().get(1);
       // totalPrice.setText((String.format("$%.2f", total)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}