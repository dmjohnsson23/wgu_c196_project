package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.dmjohnson.wgu.c196.db.Assessment;
import me.dmjohnson.wgu.c196.db.DataRepository;

public class AssessmentListViewModel extends AndroidViewModel {
    private LiveData<List<Assessment>> assessments;
    private DataRepository repository;

    public AssessmentListViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public LiveData<List<Assessment>> getAssessments(Integer courseId){
        if (assessments == null){
            assessments = repository.getAssessments(courseId);
        }
        return assessments;
    }
}
