package me.dmjohnson.wgu.c196.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataAccessObject {
    @Query("SELECT * from Term")
    LiveData<List<Term>> getTerms();

    @Query("SELECT * from Term WHERE id = :id")
    LiveData<Term> getTerm(int id);

    @Query("SELECT * from Term WHERE id = :id")
    Term getTermRaw(int id);

    @Insert
    void addTerms(List<Term> terms);

    @Insert
    void addTerm(Term term);

    @Update
    void updateTerm(Term term);

    @Query("SELECT * FROM Course WHERE id = :id")
    LiveData<Course> getCourse(int id);

    @Query("SELECT * FROM Course WHERE id = :id")
    Course getCourseRaw(int id);

    @Insert
    void addCourses(List<Course> courses);

    @Insert
    void addCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM Course WHERE termId = :termId")
    LiveData<List<Course>> getCourses(int termId);

    @Query("SELECT * FROM Assessment WHERE courseId = :courseId")
    LiveData<List<Assessment>> getAssessments(int courseId);

    @Insert
    void addAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Query("SELECT * FROM Assessment WHERE id = :id")
    Assessment getAssessmentRaw(int id);

    @Delete
    void deleteTerm(Term term);

    @Delete
    void deleteCourse(Course course);

    @Delete
    void deleteAssessment(Assessment assessment);
}
