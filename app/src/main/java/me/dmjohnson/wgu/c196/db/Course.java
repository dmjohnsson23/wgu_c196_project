package me.dmjohnson.wgu.c196.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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

    @PrimaryKey(autoGenerate = true) Integer id;
    private String title;
    private Date startDate;
    private Date endDate;
    private Status status;
    @Embedded
    private Contact mentor;
    private String note;
    Integer termId;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Contact getMentor() {
        return mentor;
    }

    public void setMentor(Contact mentor) {
        this.mentor = mentor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public void setTermId(Term term) {
        this.termId = term.getId();
    }
    public enum Status{
        IN_PROGRESS,
        COMPLETED,
        PLANNED,
        DROPPED
    }
}
