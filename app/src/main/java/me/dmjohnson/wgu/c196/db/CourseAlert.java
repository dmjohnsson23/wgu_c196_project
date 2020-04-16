package me.dmjohnson.wgu.c196.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.Date;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "id",
                childColumns = "courseId",
                onDelete = ForeignKey.CASCADE
        )
)
public class CourseAlert implements AlertInterface {
    private int id;
    private int courseId;
    private Date date;
    private String title;
    private String text;

    @Ignore
    public CourseAlert(Course course, boolean useStartDate){
        courseId = course.getId();
        if (useStartDate){
            title = "Course Starting";
            text = course.getTitle() + " starts today!";
            date = course.getStartDate();
        }
        else{
            title = "Course Ending";
            text = course.getTitle() + " ends today!";
            date = course.getEndDate();
        }
    }

    public CourseAlert(int id, int courseId, Date date, String title, String text) {
        this.id = id;
        this.courseId = courseId;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getText() {
        return text;
    }
}
