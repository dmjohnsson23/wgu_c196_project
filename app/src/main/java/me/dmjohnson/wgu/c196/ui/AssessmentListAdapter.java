package me.dmjohnson.wgu.c196.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import me.dmjohnson.wgu.c196.AssessmentEditActivity;
import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.db.Assessment;

import java.util.List;

import static me.dmjohnson.wgu.c196.util.Globals.ASSESSMENT_ID;


public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.ViewHolder> {

    private final List<Assessment> assessments;
    private final Context context;

    public AssessmentListAdapter(List<Assessment> assessments, Context context) {
        this.assessments = assessments;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_assessment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.assessment = assessment;
        holder.assessmentName.setText(assessment.getName());

        holder.view.setOnClickListener(v -> {
            Intent intent = new Intent(context, AssessmentEditActivity.class);
            intent.putExtra(ASSESSMENT_ID, assessment.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (assessments == null) return 0;
        return assessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView assessmentName;
        public Assessment assessment;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            assessmentName =view.findViewById(R.id.assessment_name);
        }
    }
}
