package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    private EditText editLocation, editOrderDetails;
    private ImageView restaurantImage;
    private Button btnSubmitOrder;
    private FirebaseFirestore db;
    private FoodOption selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Initialize UI elements
        editLocation = findViewById(R.id.editLocation);
        editOrderDetails = findViewById(R.id.editOrderDetails);
        restaurantImage = findViewById(R.id.restaurantImage);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);

        // Get passed food option (use Serializable)
        FoodSelectionDetails selectionDetails = (FoodSelectionDetails) getIntent().getSerializableExtra("selection_details");
        if (selectionDetails != null) {
            selectedOption = selectionDetails.getOption();
            if (selectedOption != null && !selectedOption.DrawableImage().isEmpty()) {
                restaurantImage.setImageResource(selectedOption.DrawableImage().get(0)); // first image
            }
        }

        // Handle order submission
        btnSubmitOrder.setOnClickListener(v -> {
            String location = editLocation.getText().toString().trim();
            String details = editOrderDetails.getText().toString().trim();

            if (location.isEmpty() || details.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Prepare order data
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("restaurant", selectedOption.getShopName()); // use getShopName()
            orderData.put("location", location);
            orderData.put("details", details);
            orderData.put("timestamp", FieldValue.serverTimestamp());

            // Use the FirestoreOrderDetails to send order data
            FirestoreOrderDetails.sendOrder(OrderDetailsActivity.this, orderData);
        });
    }
}
