package com.example.mohit.tpomnnit.messenger;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohit.tpomnnit.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neera on 3/24/2017. 
 */

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.MyViewHolder> {
    private ArrayList<String> users;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.name);
        }
    }
    public ChatUserAdapter(ArrayList<String> users)
    {
        this.users=users;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String user=users.get(position);
        holder.name.setText(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
} 