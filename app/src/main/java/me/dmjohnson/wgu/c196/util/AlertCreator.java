package me.dmjohnson.wgu.c196.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.TermViewActivity;
import me.dmjohnson.wgu.c196.db.AlertInterface;
import me.dmjohnson.wgu.c196.db.AssessmentAlert;
import me.dmjohnson.wgu.c196.db.CourseAlert;
import me.dmjohnson.wgu.c196.db.TermAlert;

import static me.dmjohnson.wgu.c196.util.Globals.TITLE;
import static me.dmjohnson.wgu.c196.util.Globals.TEXT;
import static me.dmjohnson.wgu.c196.util.Globals.NOTIFICATION_ID;

public class AlertCreator {

    public static void createAlert(Context context, AlertInterface alert) {
        Calendar now = Calendar.getInstance();

        if (alert.getDate().getTime() <= now.getTime().getTime()) {
            Toast.makeText(context.getApplicationContext(), context.getString(R.string.notification_set_err_past), Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(context.getApplicationContext(), ReminderBroadcast.class);
        int notificationId = alert.getId();

        // Make ids unique despite different types
        // (If a student has more than 10,000 of any one type of alert, that would be pretty ridiculous)
        if (alert instanceof TermAlert) notificationId += 10000;
        else if (alert instanceof CourseAlert) notificationId += 20000;
        else if (alert instanceof AssessmentAlert) notificationId += 30000;

        // Set intent values
        intent.putExtra(TITLE, alert.getTitle());
        intent.putExtra(TEXT, alert.getText());
        intent.putExtra(NOTIFICATION_ID, notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, alert.getDate().getTime(), pendingIntent);

        Toast.makeText(context.getApplicationContext(), context.getString(R.string.notification_set_success), Toast.LENGTH_LONG).show();
    }

}
