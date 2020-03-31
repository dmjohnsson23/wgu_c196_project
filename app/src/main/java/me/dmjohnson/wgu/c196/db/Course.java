package me.dmjohnson.wgu.c196.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
    foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "id",
        childColumns = "termId",
        onDelete = ForeignKey.RESTRICT
    )
)
public class Course {

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMentor(Contact mentor) {
        this.mentor = mentor;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public void setTermId(Term term) {
        this.termId = term.getId();
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Status getStatus() {
        return status;
    }

    public Contact getMentor() {
        return mentor;
    }

    public String getNote() {
        return note;
    }

    public Integer getTermId() {
        return termId;
    }

    public enum Status{
        IN_PROGRESS,
        COMPLETED,
        PLANNED,
        DROPPED
    }

    @PrimaryKey(autoGenerate = true) Integer id;
    String title;
    Date startDate;
    Date endDate;
    Status status;
    @Embedded Contact mentor;
    String note;
    Integer termId;
}
