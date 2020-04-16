package me.dmjohnson.wgu.c196;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.dmjohnson.wgu.c196.db.Course;

public class CourseNotesFragment extends Fragment {

    private CourseEditViewModel model;
    private Course course;
    private String noteValue;
    private EditText noteField;
    private Integer courseId;

    public static CourseNotesFragment newInstance(Integer courseId) {
        CourseNotesFragment instance =  new CourseNotesFragment();
        instance.courseId = courseId;
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_notes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(this).get(CourseEditViewModel.class);

        // get field
        noteField = getView().findViewById(R.id.note_text);


        // Get the course
        MutableLiveData<Course> liveCourse;
        liveCourse = model.getCourse(courseId);

        // Get data from the course
        liveCourse.observe(getViewLifecycleOwner(), course->{
            this.course = course;
            resetField();
        });

        // Setup listener
        FloatingActionButton fab = getView().findViewById(R.id.save_button);
        fab.setOnClickListener(v->onClickSave());
    }

    private void onClickSave() {
        setData();
        model.saveCourse(course);
    }

    private void setData() {
        noteValue = noteField.getText().toString();
        course.setNote(noteValue);
    }

    private void resetField() {
        noteValue = course.getNote();
        noteField.setText(noteValue);
    }

}
