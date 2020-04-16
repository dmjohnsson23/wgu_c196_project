package me.dmjohnson.wgu.c196.util;

import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Date;

public class Utils {

    public static Intent getEventIntent (String title, Date date) {
        return new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date.getTime())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date.getTime())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
    }

}
