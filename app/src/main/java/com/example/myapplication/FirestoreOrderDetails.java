package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirestoreOrderDetails {

    /**
     * Sends the order data to Firestore.
     *
     * @param context   the context for showing Toast messages.
     * @param orderData a Map containing your order data.
     */
    public static void sendOrder(Context context, Map<String, Object> orderData) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a timestamp to the order data if not already present
        if (!orderData.containsKey("timestamp")) {
            orderData.put("timestamp", FieldValue.serverTimestamp());
        }

        db.collection("orders")
                .add(orderData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(context, "Order submitted successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Submission failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
