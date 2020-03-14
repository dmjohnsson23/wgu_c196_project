package me.dmjohnson.wgu.c196.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Course {

    public enum Status{
        IN_PROGRESS,
        COMPLETED,
        PLANNED,
        DROPPED
    }

    @PrimaryKey(autoGenerate = true) int id;
    String title;
    Date startDate;
    Date endDate;
    Status status;
    @Embedded Contact mentor;
    String note;
}
