package me.dmjohnson.wgu.c196.db;


import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class CourseWithAssessments {
    @Embedded Course course;
    @Relation(parentColumn = "id", entityColumn = "courseId")
    List<Assessment> assessments;
}
