package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends Activity {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private TextView nameTextView, emailTextView, pointsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize Firebase and Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize TextViews to display user information
        // Change to actual TextView ID in your layout
        emailTextView = findViewById(R.id.emailEditText);  // Change to actual TextView ID
        pointsTextView = findViewById(R.id.pointsTextView);  // Change to actual TextView ID

        // Get the current logged-in user
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            // Fetch user data if logged in
            String uid = currentUser.getUid();
            fetchUserProfile(uid);  // Call the method to fetch the user profile
        } else {
            Toast.makeText(this, "No user logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    // Function to fetch user profile from Firestore
    private void fetchUserProfile(String uid) {
        db.collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Get user data from the Firestore document
                            String name = document.getString("name");
                            String email = document.getString("email");
                            long points = document.getLong("points");

                            // Display the user data in the TextViews
                            nameTextView.setText("Name: " + name);
                            emailTextView.setText("Email: " + email);
                            pointsTextView.setText("Points: " + points);
                        } else {
                            Toast.makeText(UserProfileActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserProfileActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
