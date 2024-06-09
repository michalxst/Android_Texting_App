package com.example.a259317proj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a259317proj.databinding.ActivitySignUpBinding;
import com.example.a259317proj.Constructors.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://projekt-bdfbc-default-rtdb.europe-west1.firebasedatabase.app");
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Creating your account...");

        binding.backtologin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        binding.signup.setOnClickListener(view -> {
            String username = binding.username.getText().toString().trim();
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "All fields are necessary", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                createUserAccount(email, password, username);
            }
        });
    }

    private void createUserAccount(String email, String password, String username) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                saveUserToDatabase(username, password, email);
            } else {
                Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void saveUserToDatabase(String email, String password, String username) {
        Users user = new Users(email, password, username);
        String userID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        database.getReference().child("Users").child(userID).setValue(user);

        try {
            progressDialog.dismiss();
            TimeUnit.SECONDS.sleep(1);
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
