package com.example.alarmapp;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class Clock extends AppCompatActivity implements View.OnClickListener {
    private TimePicker timePicker;
    private Button set_time;
    private Button changealarmtone;

    EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        edittext = findViewById(R.id.edittext);
        timePicker = findViewById(R.id.timePicker);
        set_time = findViewById(R.id.set_time);
        changealarmtone = findViewById(R.id.changealarmtone);

        changealarmtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the ringtone picker
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                startActivityForResult(intent, 0);
            }
        });

        set_time.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                timePicker.getHour(),
                timePicker.getMinute(), 0);
        Alarm_set(cal.getTimeInMillis(), edittext.getText().toString());

        finish();

    }
    private void Alarm_set(long timeInMillis, String alarmTitle) {
        Intent alarmIntent = new Intent("ALARM_SET");
        alarmIntent.putExtra("ALARM_TIME", timeInMillis);
        alarmIntent.putExtra("ALARM_TITLE", edittext.getText().toString());
        // Add this line to get the text from EditText
        sendBroadcast(alarmIntent);
        playAlarmRingtone(this);


        // Create snooze intent and send it
        Intent snoozeIntent = new Intent("SNOOZE_ALARM");
        sendBroadcast(snoozeIntent);

        // Create dismiss intent and send it
        Intent dismissIntent = new Intent("DISMISS_ALARM");
        sendBroadcast(dismissIntent);
        Intent ringIntent = new Intent("RING_ALARM");
        sendBroadcast(ringIntent);
    }
    private void stopAlarm() {
        stopAlarmRingtone(this);
        // ... any additional logic you need when stopping the alarm ...
    }
    private void playAlarmRingtone(Context context) {
        // Start the AlarmService to play the ringtone
        Intent serviceIntent = new Intent(context, AlarmService.class);
        context.startService(serviceIntent);
    }

    private void stopAlarmRingtone(Context context) {
        // Stop the AlarmService to stop the ringtone
        Intent serviceIntent = new Intent(context, AlarmService.class);
        context.stopService(serviceIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null) {
                // You can save the selected ringtone URI or do something with it
                // For simplicity, we'll just toast the selected ringtone title
                Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
                String ringtoneTitle = ringtone.getTitle(this);
                Toast.makeText(this, "Selected ringtone: " + ringtoneTitle, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
