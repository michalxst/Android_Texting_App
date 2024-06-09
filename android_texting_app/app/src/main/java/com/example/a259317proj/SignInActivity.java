package com.example.a259317proj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a259317proj.databinding.ActivitySignInBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://projekt-bdfbc-default-rtdb.europe-west1.firebasedatabase.app");
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Checking your credentials");

        binding.clicksignup.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.login.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                progressDialog.show();
                signInUser(email, password);
            } else {
                Toast.makeText(SignInActivity.this, "Email/ password missing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
