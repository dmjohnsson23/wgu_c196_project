package me.dmjohnson.wgu.c196.db;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataRepository {
    private AppDatabase db;
    private Executor executor;
    private LiveData<List<Term>> terms;
    private LiveData<Term> term;
    private MutableLiveData<Term> mutableTerm;
    private LiveData<Course> course;
    private LiveData<List<Course>> courses;
    private MutableLiveData<Course> mutableCourse;
    private LiveData<List<Assessment>> assessments;
    private LiveData<Assessment> assessment;
    private MutableLiveData<Assessment> mutableAssessment;

    public DataRepository(Context context){
        executor = Executors.newSingleThreadExecutor();
        db = AppDatabase.getInstance(context);
    }

    public LiveData<List<Term>> getTerms(){
        if (terms == null){
            terms = db.getDao().getTerms();
        }
        return terms;
    }


    public LiveData<Term> getTerm(int id){
        if (term == null  || term.getValue() == null || term.getValue().getId() != id){
            term = db.getDao().getTerm(id);
        }
        return term;
    }

    public MutableLiveData<Term> getTermMutable(int id){
        if (mutableTerm == null  || mutableTerm.getValue() == null || mutableTerm.getValue().getId() != id){
            mutableTerm = new MutableLiveData<>();
            executor.execute(()->mutableTerm.postValue(db.getDao().getTermRaw(id)));
        }
        return mutableTerm;
    }

    public void addTestTerms(){
        final List<Term> testTerms = Arrays.asList(
            new Term("Term 1", null, null),
            new Term("Term 2", null, null),
            new Term("Term 3", null, null)
        );
        addTerms(testTerms);
    }

    public void addTerms(List<Term> terms) {
        executor.execute(()->db.getDao().addTerms(terms));
    }

    public void saveTerm(Term term) {
        if (term.getId() == null){
            // New term
            executor.execute(()->db.getDao().addTerm(term));
        }
        else{
            // Existing term
            executor.execute(()->db.getDao().updateTerm(term));
        }
    }

    public MutableLiveData<Course> getCourseMutable(int id) {
        if (mutableCourse == null  || mutableCourse.getValue() == null || mutableCourse.getValue().getId() != id){
            mutableCourse = new MutableLiveData<>();
            executor.execute(()->mutableCourse.postValue(db.getDao().getCourseRaw(id)));
        }
        return mutableCourse;
    }

    public void addCourses(List<Course> courses) {
        executor.execute(()-> db.getDao().addCourses(courses));
    }

    public void saveCourse(Course course) {
        if (course.getId() == null){
            // new Course
            executor.execute(()-> db.getDao().addCourse(course));
        }
        else{
            // Existing Course
            executor.execute(()-> db.getDao().updateCourse(course));
        }
    }

    public LiveData<List<Course>> getCourses(int termId) {
        if (courses == null){
            courses = db.getDao().getCourses(termId);
        }
        return courses;
    }

    public LiveData<Course> getCourse(int id) {
        if (course == null){
            course = db.getDao().getCourse(id);
        }
        return course;
    }

    public LiveData<List<Assessment>> getAssessments(int courseId) {
        if (assessments == null){
            assessments = db.getDao().getAssessments(courseId);
        }
        return assessments;
    }

    public MutableLiveData<Assessment> getAssessmentMutable(int id) {
        if (mutableAssessment == null  || mutableAssessment.getValue() == null || mutableAssessment.getValue().getId() != id){
            mutableAssessment = new MutableLiveData<>();
            executor.execute(()->mutableAssessment.postValue(db.getDao().getAssessmentRaw(id)));
        }
        return mutableAssessment;
    }

    public void saveAssessment(Assessment assessment) {
        if (assessment.getId() == null){
            // new Course
            executor.execute(()-> db.getDao().addAssessment(assessment));
        }
        else{
            // Existing Course
            executor.execute(()-> db.getDao().updateAssessment(assessment));
        }
    }

    public void deleteTerm(Term term) {
        executor.execute(()->db.getDao().deleteTerm(term));
    }

    public void deleteCourse(Course course) {
        executor.execute(()->db.getDao().deleteCourse(course));
    }

    public void deleteAssessment(Assessment assessment){
        executor.execute(()->db.getDao().deleteAssessment(assessment));
    }
}
