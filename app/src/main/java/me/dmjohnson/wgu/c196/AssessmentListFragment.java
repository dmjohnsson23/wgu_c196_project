package me.dmjohnson.wgu.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import me.dmjohnson.wgu.c196.db.Assessment;
import me.dmjohnson.wgu.c196.ui.AssessmentListAdapter;

import static me.dmjohnson.wgu.c196.util.Globals.COURSE_ID;


public class AssessmentListFragment extends Fragment {

    private List<Assessment> assessments = new ArrayList<>();
    private Integer courseId;
    private AssessmentListViewModel model;
    private AssessmentListAdapter adapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AssessmentListFragment() {
    }


    public static AssessmentListFragment newInstance(Integer courseId) {
        AssessmentListFragment fragment = new AssessmentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.courseId = courseId;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        model = ViewModelProviders.of(this).get(AssessmentListViewModel.class);

        // Setup recyclerview
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        model.getAssessments(courseId).observe(getViewLifecycleOwner(), newAssessments->{
            assessments.clear();
            assessments.addAll(newAssessments);

            if (adapter== null){
                adapter = new AssessmentListAdapter(assessments, context);
                recyclerView.setAdapter(adapter);
            }
            else{
                adapter.notifyDataSetChanged();
            }
        });

        // Setup Add Button
        FloatingActionButton fab = view.findViewById(R.id.add_button);
        fab.setOnClickListener(v->{
            Intent intent = new Intent(context, AssessmentEditActivity.class);
            intent.putExtra(COURSE_ID, courseId);
            startActivity(intent);
        });

        return view;
    }



}
