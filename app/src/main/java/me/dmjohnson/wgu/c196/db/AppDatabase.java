package me.dmjohnson.wgu.c196.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(
        entities = {Assessment.class, Course.class, Term.class},
        version = 6
)
@TypeConverters(DatatypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataAccessObject getDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "terminal_plot.db")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }
}
