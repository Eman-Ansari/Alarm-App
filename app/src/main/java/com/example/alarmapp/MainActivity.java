package com.example.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcv;
    private com.example.alarmapp.Adapter adapter;
    Button circlebtn;
    private ArrayList<Model> alarmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circlebtn = findViewById(R.id.circlebtn);
        rcv = findViewById(R.id.recview);
        adapter = new com.example.alarmapp.Adapter(alarmList);
        // Set up RecyclerView
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(adapter);
        circlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.example.alarmapp.Clock.class);
                startActivity(intent);
            }
        });

        BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int position = intent.getIntExtra("POSITION", -1);
                if (position != -1) {
                    // Remove the alarm at the specified position
                    alarmList.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        };

// Register the receiver to listen for the "DELETE_ALARM" intent
        IntentFilter deleteFilter = new IntentFilter("DELETE_ALARM");
        registerReceiver(deleteReceiver, deleteFilter);




        // Register a BroadcastReceiver to update RecyclerView when an alarm is set
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract the alarm time and title from the intent
                long alarmTimeInMillis = intent.getLongExtra("ALARM_TIME", 0);
                String alarmTitle = intent.getStringExtra("ALARM_TITLE");

                // Update the RecyclerView when an alarm is set
                updateRecyclerViewData(alarmTimeInMillis, alarmTitle);
            }
        };

        // Register the receiver to listen for the "ALARM_SET" intent
        IntentFilter filter = new IntentFilter("ALARM_SET");
        registerReceiver(receiver, filter);
    }
    private void updateRecyclerViewData(long alarmTimeInMillis, String alarmTitle) {
        // Convert the alarm time in millis to a readable format (e.g., HH:mm)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarmTimeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String alarmTime = sdf.format(calendar.getTime());

        // Create a new alarm model with the formatted alarm time and title
        Model alarm = new Model(alarmTime);
        alarm.setAlarmTitle(alarmTitle);

        // Add the alarm to the list and notify the adapter
        alarmList.add(alarm);
        adapter.notifyDataSetChanged();
    }


}
