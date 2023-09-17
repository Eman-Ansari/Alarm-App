package com.example.alarmapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Model> alarmList;

    public Adapter(ArrayList<Model> alarmList) {
        this.alarmList = alarmList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model alarm = alarmList.get(position);
        holder.bindData(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }
}
