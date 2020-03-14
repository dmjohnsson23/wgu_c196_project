package me.dmjohnson.wgu.c196.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class TermWithCourses {
    @Embedded Term term;
    @Relation(parentColumn = "id", entityColumn = "termId")
    List<Course> courses;
}
