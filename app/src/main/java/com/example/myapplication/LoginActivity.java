package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

public class LoginActivity extends Activity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText;
    private Button loginButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login); // Set the layout for this activity
        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.emailEditText);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signUpButton);
        loginPassword = findViewById(R.id.passwordEditText);  // Initialize loginPassword

        db = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get email and password entered by the user
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                // Use Firebase Authentication to sign in
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Login is successful
                                // Show a success toast
                                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

                                // Start the HomePageActivity
                                Intent intent = new Intent(LoginActivity.this, OrderActivity.class);
                                startActivity(intent);

                                // Optional: Close the current login activity
                                finish();
                            } else {
                                // Login failed
                                // Show a failure toast
                                Toast.makeText(LoginActivity.this, "Login unsuccessful. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set OnClickListener for the signupRedirectText (TextView)
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the RegistrationActivity (Sign up page)
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    // Fetch user data based on UID
    private void fetchUserData(String uid) {
        db.collection("users")  // Get the "users" collection
                .document(uid)  // Access the document for this user
                .get()  // Get the document
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            long points = document.getLong("points");

                            // Pass the points data to the next activity
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                            intent.putExtra("USER_POINTS", points);
                            intent.putExtra("UID", uid);
                            startActivity(intent);

                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Error fetching user data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Update user points after a purchase
    private void addPointsToUser(String uid, double purchaseAmount) {
        long pointsToAdd = (long) (purchaseAmount / 10);  // Example: 1 point for every 10 units of purchase

        db.collection("users")
                .document(uid)
                .update("points", FieldValue.increment(pointsToAdd))  // Increment the points
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Points added successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to add points. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }
}
