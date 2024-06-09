package com.example.a259317proj.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a259317proj.Constructors.Message;
import com.example.a259317proj.R;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Message> messages;
    Context context;
    String receiverID;
    int senderview = 1;
    int receiverview = 2;


    public ChatAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    public ChatAdapter(ArrayList<Message> messages, Context context, String receiverID) {
        this.messages = messages;
        this.context = context;
        this.receiverID = receiverID;
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receivertext, receivertime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receivertext = itemView.findViewById(R.id.textreceive);
            receivertime = itemView.findViewById(R.id.timereceive);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView sendertext, sendertime;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            sendertext = itemView.findViewById(R.id.textsent);
            sendertime = itemView.findViewById(R.id.timesent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == senderview) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_sender, parent, false);
            return new SenderViewHolder(view);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.activity_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUserID().equals(FirebaseAuth.getInstance().getUid())) {
            return senderview;
        } else {
            return receiverview; //sprawdza ktory user jest zalogowany po id
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder)holder).sendertext.setText(message.getMessage());
            Date date= new Date(message.getTime());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String strDate = format.format(date);
            ((SenderViewHolder)holder).sendertime.setText(strDate.toString());
        } else {
            ((ReceiverViewHolder) holder).receivertext.setText(message.getMessage());
            Date date= new Date(message.getTime());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String strDate = format.format(date);
            ((ReceiverViewHolder)holder).receivertime.setText(strDate.toString());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}

