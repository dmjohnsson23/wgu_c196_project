package me.dmjohnson.wgu.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.dmjohnson.wgu.c196.CourseViewActivity;
import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.Term;

import static me.dmjohnson.wgu.c196.util.Globals.COURSE_ID;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private final List<Course> courses;
    private final Context context;

    public CourseListAdapter(List<Course> courses, Context context) {
        if (courses == null) this.courses = new ArrayList<Course>();
        else this.courses = courses;
        this.context = context;
    }


    @NonNull
    @Override
    public CourseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.ViewHolder holder, int position) {
        final Course course = courses.get(position);
        holder.courseName.setText(course.getTitle());
        holder.courseId = course.getId();
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setData(List<Term> terms) {
        this.courses.clear();
        this.courses.addAll(courses);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        Button editButton;
        int courseId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            editButton = itemView.findViewById(R.id.edit_button);

            editButton.setOnClickListener((View view)-> onClickEditButton());
        }

        private void onClickEditButton() {
            Intent intent = new Intent(context, CourseViewActivity.class);
            intent.putExtra(COURSE_ID, courseId);
            context.startActivity(intent);
        }
    }
}
