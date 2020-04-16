package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import me.dmjohnson.wgu.c196.db.Assessment;
import me.dmjohnson.wgu.c196.db.DataRepository;

public class AssessmentEditViewModel extends AndroidViewModel {
    private DataRepository repository;
    private MutableLiveData<Assessment> assessment;

    public AssessmentEditViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public MutableLiveData<Assessment> getAssessment(int id) {
        if (assessment == null){
            assessment = repository.getAssessmentMutable(id);
        }
        return assessment;
    }

    public MutableLiveData<Assessment> getNewAssessment(int courseId){
        if (assessment == null){
            assessment = new MutableLiveData<>();
            Assessment newAssessment = new Assessment();
            newAssessment.setCourseId(courseId);
            assessment.postValue(newAssessment);
        }
        return assessment;
    }

    public void saveAssessment(Assessment assessment) {
        this.assessment.postValue(assessment);
        repository.saveAssessment(assessment);
    }

    public void deleteAssessment(Assessment assessment) {
        repository.deleteAssessment(assessment);
    }
}
