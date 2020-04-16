package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.DataRepository;

public class CourseEditViewModel extends AndroidViewModel {
    private DataRepository repository;
    private MutableLiveData<Course> course;

    public CourseEditViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public MutableLiveData<Course> getCourse(int id) {
        if (course == null){
            course = repository.getCourseMutable(id);
        }
        return course;
    }

    public MutableLiveData<Course> getNewCourse(int termId){
        if (course == null){
            course = new MutableLiveData<>();
            Course newCourse = new Course();
            newCourse.setTermId(termId);
            course.postValue(newCourse);
        }
        return course;
    }

    public void saveCourse(Course course) {
        this.course.postValue(course);
        repository.saveCourse(course);
    }
}
