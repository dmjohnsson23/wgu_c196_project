package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.DataRepository;
import me.dmjohnson.wgu.c196.db.Term;

public class TermViewViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<Term> term;
    private LiveData<List<Course>> courses;

    public LiveData<Term> getTerm(int id) {
        if (term == null){
            term = repository.getTerm(id);
        }
        return term;
    }


    public LiveData<List<Course>> getCourses(int termId) {
        if (courses == null){
            courses = repository.getCourses(termId);
        }
        return courses;
    }



    public TermViewViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }


}
