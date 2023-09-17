package com.example.alarmapp;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView alarmTimeTextView;
    private TextView alarmTitleTextView;
    private ImageButton deleteButton;

    private Switch switchButton;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        alarmTimeTextView = itemView.findViewById(R.id.alarmTimeTextView);
        alarmTitleTextView = itemView.findViewById(R.id.alarmTitleTextView);
        switchButton = itemView.findViewById(R.id.switchButton); // Added this line
        deleteButton = itemView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete action here
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Notify the main activity to delete the alarm at this position
                    Intent deleteIntent = new Intent("DELETE_ALARM");
                    deleteIntent.putExtra("POSITION", position);
                    itemView.getContext().sendBroadcast(deleteIntent);
                }
            }
        });
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Handle the switch state change here
                updateAlarmState(isChecked);

            }


        });

    }

    public void bindData(com.example.alarmapp.Model alarmModel) {
        alarmTimeTextView.setText(alarmModel.getAlarmTime());
        // Set other views based on your model's data
        alarmTitleTextView.setText(alarmModel.getAlarmTitle());

        // Set the switch state based on the alarm's state (on/off)
        switchButton.setChecked(alarmModel.isAlarmOn());


    }
    public void setAlarmTitle(String title) {
        alarmTitleTextView.setText(title);
    }
    private void updateAlarmState(boolean isOn) {
        // Here you would typically update the alarm state in your data structure
        // For this example, we'll just show a toast based on the switch state
        String stateMessage = isOn ? "Alarm is ON" : "Alarm is OFF";
        Toast.makeText(itemView.getContext(), stateMessage, Toast.LENGTH_SHORT).show();
    }

}

