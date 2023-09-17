package com.example.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the action from the intent to determine snooze or dismiss
        String action = intent.getAction();

        if (action != null) {
            switch (action) {
                case "SNOOZE_ALARM":
                    // Handle snooze action (e.g., set a new alarm for snooze)
                    // For simplicity, we'll just toast a snooze message
                    Toast.makeText(context, "Alarm snoozed", Toast.LENGTH_SHORT).show();
                    break;
                case "DISMISS_ALARM":
                    // Handle dismiss action (e.g., stop the alarm)
                    // For simplicity, we'll just toast a dismiss message
                    Toast.makeText(context, "Alarm dismissed", Toast.LENGTH_SHORT).show();
                    break;
                case "RING_ALARM":
                    // Play the alarm ringtone
                    playAlarmRingtone(context);
                    break;
            }
        }

    }

    private void playAlarmRingtone(Context context) {
        // Play the default alarm ringtone
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);

        // Set a listener to stop the ringtone when it's finished
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Release the MediaPlayer resources after playback is completed
                mediaPlayer.release();
            }
        });
        // Start the ringtone
        mediaPlayer.start();

    }






}
