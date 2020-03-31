package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.dmjohnson.wgu.c196.db.Assessment;
import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.DataRepository;

public class CourseViewViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<Course> course;
    private LiveData<List<Assessment>> assessments;

    public LiveData<Course> getCourse(int id) {
        if (course == null){
            course = repository.getCourse(id);
        }
        return course;
    }


    public LiveData<List<Assessment>> getAssessments(int courseId) {
        if (assessments == null){
            assessments = repository.getAssessments(courseId);
        }
        return assessments;
    }



    public CourseViewViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }


}
