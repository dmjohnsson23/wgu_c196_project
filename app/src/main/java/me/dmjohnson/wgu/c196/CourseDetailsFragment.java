package me.dmjohnson.wgu.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static me.dmjohnson.wgu.c196.Globals.COURSE_ID;

public class CourseDetailsFragment extends Fragment {

    private CourseDetailsViewModel mViewModel;
    private Integer courseId;

    public static CourseDetailsFragment newInstance(Integer courseId) {
        CourseDetailsFragment instance = new CourseDetailsFragment();
        instance.courseId = courseId;
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CourseDetailsViewModel.class);
        // TODO: Use the ViewModel

        View view = getView();
        view.findViewById(R.id.edit_button).setOnClickListener(v->{
            Intent intent = new Intent(view.getContext(), CourseEditActivity.class);
            intent.putExtra(COURSE_ID, courseId);
            startActivity(intent);
        });
    }

}
