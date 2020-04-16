package me.dmjohnson.wgu.c196.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.Date;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Assessment.class,
                parentColumns = "id",
                childColumns = "assessmentId",
                onDelete = ForeignKey.CASCADE
        )
)
public class AssessmentAlert implements AlertInterface {
    private int id;
    private int assessmentId;
    private Date date;
    private String title;
    private String text;

    public AssessmentAlert(int id, int assessmentId, Date date, String title, String text) {
        this.id = id;
        this.assessmentId = assessmentId;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    @Ignore
    public AssessmentAlert(Course course, Assessment assessment){
        assessmentId = assessment.getId();
        title = "Assessment Today";
        text = assessment.getName() + " for " + course.getTitle() + " is today!";
        date = assessment.getGoalDate();
    }

    @Ignore
    public AssessmentAlert(Assessment assessment){
        assessmentId = assessment.getId();
        title = "Assessment Today";
        text = assessment.getName() + " is today!";
        date = assessment.getGoalDate();
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
