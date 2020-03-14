package me.dmjohnson.wgu.c196.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class DatatypeConverter {
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Assessment.Type stringToAssessmentType(String string){
        return Assessment.Type.valueOf(string);
    }

    @TypeConverter
    public static String assessmentTypeToString(Assessment.Type type){
        return type.name();
    }

    @TypeConverter
    public static Course.Status stringToCourseStatus(String string){
        return Course.Status.valueOf(string);
    }

    @TypeConverter
    public static String courseStatusToString(Course.Status type){
        return type.name();
    }

}
