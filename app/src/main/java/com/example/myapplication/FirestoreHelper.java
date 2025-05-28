package com.example.myapplication;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
public class FirestoreHelper {
//this function is supposed to handle logic for initialising points for new user
private FirebaseFirestore db;

    public FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    // Function to add a new user with initial points (0 points)
    public void addNewUserToFirestore(String uid, String email) {
        // Create a Map of user data to store in Firestore
        Map<String, Object> user = new HashMap<>();

        user.put("email", email);
        user.put("points", 0);  // Initialize points to 0 when the user first registers

        // Add the user document to the "users" collection
        db.collection("users")
                .document(uid)  // Use the UID as the document ID
                .set(user)  // Set the user data in Firestore
                .addOnSuccessListener(aVoid -> {
                    // Optionally, handle success
                    // E.g., Log success or show a toast
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., show a toast or log the error)
                    // E.g., Log the error or show a message
                });
    }

    // Function to fetch user points from Firestore
    public void fetchUserPoints(String uid, OnUserPointsFetchedListener listener) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null && task.getResult().exists()) {
                            long points = task.getResult().getLong("points");
                            listener.onSuccess(points);
                        } else {
                            listener.onFailure("User not found.");
                        }
                    } else {
                        listener.onFailure("Error fetching data.");
                    }
                });
    }

    // Interface to handle success/failure in fetching points
    public interface OnUserPointsFetchedListener {
        void onSuccess(long points);
        void onFailure(String errorMessage);
    }
}