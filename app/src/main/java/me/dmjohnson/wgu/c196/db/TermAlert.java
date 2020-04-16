package me.dmjohnson.wgu.c196.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;

import java.util.Date;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Term.class,
                parentColumns = "id",
                childColumns = "termId",
                onDelete = ForeignKey.CASCADE
        )
)
public class TermAlert implements AlertInterface {
    private int id;
    private int termId;
    private Date date;
    private String title;
    private String text;

    public TermAlert(int id, int termId, Date date, String title, String text) {
        this.id = id;
        this.termId = termId;
        this.date = date;
        this.title = title;
        this.text = text;
    }

    @Ignore
    public TermAlert(Term term, boolean useStartDate){
        termId = term.getId();
        if (useStartDate){
            title = "Term Starting";
            text = term.getTitle() + " starts today!";
            date = term.getStartDate();
        }
        else{
            title = "Term Ending";
            text = term.getTitle() + " ends today!";
            date = term.getEndDate();
        }
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
