package me.dmjohnson.wgu.c196.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.TermListActivity;

import static me.dmjohnson.wgu.c196.util.Globals.TITLE;
import static me.dmjohnson.wgu.c196.util.Globals.TEXT;
import static me.dmjohnson.wgu.c196.util.Globals.NOTIFICATION_ID;
import static me.dmjohnson.wgu.c196.util.Globals.NOTIFICATION_CHANNEL_ID_ALERTS;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent nextIntent = new Intent(context, TermListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, nextIntent, 0);
        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_ALERTS)
                .setContentTitle(intent.getStringExtra(TITLE))
                .setContentText(intent.getStringExtra((TEXT)))
                .setSmallIcon(R.drawable.ic_date_range_black)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(intent.getIntExtra(NOTIFICATION_ID, 0), notification);
    }
}
