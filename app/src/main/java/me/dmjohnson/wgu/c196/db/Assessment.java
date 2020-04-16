package me.dmjohnson.wgu.c196.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
    foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "id",
        childColumns = "courseId",
        onDelete = ForeignKey.CASCADE
    )
)
public class Assessment {
    @PrimaryKey(autoGenerate = true) Integer id;
    private String name;
    private Type type;
    private Date goalDate;
    Integer courseId;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public enum Type {
        OBJECTIVE,
        PERFORMANCE
    }
}
