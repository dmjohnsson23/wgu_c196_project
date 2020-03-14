package me.dmjohnson.wgu.c196.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Assessment {

    public enum Type {
        OBJECTIVE,
        PERFORMANCE
    }

    @PrimaryKey(autoGenerate = true) int id;
    String name;
    Type type;
    Date goalDate;
}
