package com.example.a259317proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a259317proj.Adapter.ChatAdapter;
import com.example.a259317proj.Constructors.Message;
import com.example.a259317proj.databinding.ChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    ChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database =  FirebaseDatabase.getInstance("https://projekt-bdfbc-default-rtdb.europe-west1.firebasedatabase.app");
        auth =  FirebaseAuth.getInstance();

        final String senderID = auth.getUid();

        String receiveID = getIntent().getStringExtra("userID");

        String usernamechat = getIntent().getStringExtra("username");

        Toast.makeText(this, usernamechat, Toast.LENGTH_SHORT).show();

        binding.usernamechat.setText(usernamechat);
        binding.back.setOnClickListener(view -> {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            startActivity(intent);
        });

        final ArrayList<Message> message = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(message, this, receiveID);

        binding.chatrecyclerview.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatrecyclerview.setLayoutManager(layoutManager);

        final String sender = senderID + receiveID;
        final String receiver = receiveID + senderID;

        database.getReference().child("Chats").child(sender)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                message.clear();
                                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                    Message mess = snapshot1.getValue(Message.class);
                                    mess.setMessageID(snapshot1.getKey());
                                    message.add(mess);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.message.getText().toString();
                final Message mess = new Message(senderID, message);
                mess.setTime(new Date().getTime());
                binding.message.setText("");

                database.getReference().child("Chats")
                        .child(sender).push().setValue(mess).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("Chats").child(receiver).push()
                                        .setValue(mess).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });
    }
}