package me.dmjohnson.wgu.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import me.dmjohnson.wgu.c196.db.Assessment;
import me.dmjohnson.wgu.c196.ui.AssessmentListAdapter;

import static me.dmjohnson.wgu.c196.Globals.COURSE_ID;


public class AssessmentListFragment extends Fragment {

    private List<Assessment> assessments;
    private Integer courseId;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assessment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        // Set the adapter
        Context context = view.getContext();
        // TODO get assessments and init recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new AssessmentListAdapter(assessments, getContext()));

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
