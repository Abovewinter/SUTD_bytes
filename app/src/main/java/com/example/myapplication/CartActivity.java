package com.example.myapplication;

import android.app.Activity;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CartActivity extends Activity {
    int quantity1 = 1;
    double deliveryFee = 1.50;
    double grandTotal = 0.0;
    String redeem_distance = "short distance";
    List<Double> prices1 = Arrays.asList(5.8,3.5,5.5);
    List<Double> prices2 = Arrays.asList(4.9,2.2,6.0);
    List<CartItem> cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Navigation.setupNavigation(this);
        cartItems = (List<CartItem>) getIntent().getSerializableExtra("cart_items"); // ✅

        // Reference the scrollable container inside ScrollView
        LinearLayout cartOrderLayout = findViewById(R.id.cart_order);
        TextView storename = findViewById(R.id.store_name_cart);


        for (CartItem item : cartItems) {
            View cartItemView = getLayoutInflater().inflate(R.layout.item_cart, null);
            // Add the inflated cart item view into the scroll container
            cartOrderLayout.addView(cartItemView);

            Button add1 = cartItemView.findViewById(R.id.add_1);
            Button remove1 = cartItemView.findViewById(R.id.remove_1);
            TextView quantity = cartItemView.findViewById(R.id.food_quantity_cart);
            quantity.setText(String.valueOf(quantity1));

            //TextView storename = cartItemView.findViewById(R.id.store_name_cart);
            TextView foodname = cartItemView.findViewById(R.id.food_name_cart);
            TextView deliveryprice = findViewById(R.id.delivery_fee_cart);
            TextView distance = findViewById(R.id.reedeming_distance_cart);
            TextView price = cartItemView.findViewById(R.id.food_price_cart);
            TextView totalprice = findViewById(R.id.total_price_cart);

            storename.setText(item.getStoreName());
            foodname.setText(item.getFoodName());
            deliveryprice.setText(String.format("$%.2f", deliveryFee));
            distance.setText(redeem_distance);
            //quantity.setText(item.getQuantity() + "x");
            price.setText("$" + String.format("%.2f", item.getTotalPrice()));
            //price.setText("$" + String.format("%.2f", item.getTotalPrice()));

            final int[] itemQuantity = {item.getQuantity()};
            //double unitPrice = item.getTotalPrice() / item.getQuantity();
           // double itemTotalPrice = unitPrice * itemQuantity[0];
           // grandTotal += itemTotalPrice;

           // quantity.setText(itemQuantity[0] + "x");
           // price.setText(String.format("$%.2f", itemTotalPrice));
           // totalprice.setText(String.format("$%.2f", grandTotal + deliveryFee));

            //Calculate total + delivery fee
            double total = item.getTotalPrice() + deliveryFee;
            totalprice.setText("$" + String.format("%.2f", total));


            // Increase quantity
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quantity1 += 1;
                //quantity.setText((String.format("%dx", quantity1)));

               // double pricing = quantity1 * prices1.get(0);
                //price.setText("$" + pricing);

                itemQuantity[0]++;
               // item.setQuantity(itemQuantity[0]);
                quantity.setText(itemQuantity[0] + "x");

                double pricing = itemQuantity[0] * item.getTotalPrice();
                price.setText("$" + pricing);

                //double updatedPrice = itemQuantity[0] * unitPrice;
               // price.setText(String.format("$%.2f", updatedPrice));
                recalculateTotal(totalprice);

                double total = item.getTotalPrice() + deliveryFee;
                totalprice.setText("$" + String.format("%.2f", total));

                //recalculateTotal(cartOrderLayout, totalprice);
                
            }
        });

        //decrease quantity
        remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemQuantity[0] > 1) {
                    //quantity1 -= 1;

                   // double total = quantity1 * prices2.get(0);
                   // totalprice.setText("$" + total);

                  // double pricing = quantity1 * prices1.get(0);
                   //price.setText("$" + pricing);

                   // quantity.setText((String.format("%dx", quantity1)));

                    itemQuantity[0]--;
                    //item.setQuantity(itemQuantity[0]);
                    quantity.setText(itemQuantity[0] + "x");

                    double pricing = itemQuantity[0] * item.getTotalPrice();
                    price.setText("$" + pricing);

                    //double updatedPrice = itemQuantity[0] * unitPrice;
                    //price.setText(String.format("$%.2f", updatedPrice));
                   recalculateTotal(totalprice);

                    double total = item.getTotalPrice() + deliveryFee;
                    totalprice.setText("$" + String.format("%.2f", total));
                }

                else {
                    //storename.setText("NIL");
                    //foodname.setText("Your cart is empty");
                    //price.setText("NIL");
                    //totalprice.setText("NIL");

                    //storename.setVisibility(View.GONE);
                    //foodname.setVisibility(View.GONE);
                    //price.setVisibility(View.GONE);
                    //totalprice.setVisibility(View.GONE);
                    //quantity.setVisibility(View.GONE);
                    //add1.setVisibility(View.GONE);
                    //remove1.setVisibility(View.GONE);

                    cartItemView.setVisibility(View.GONE);
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
                    confirmButton.setVisibility(View.GONE);
                    add1.setVisibility(View.GONE);
                    remove1.setVisibility(View.GONE);
                }
            }
        });
        }

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

}