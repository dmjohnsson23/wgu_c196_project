package me.dmjohnson.wgu.c196;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.dmjohnson.wgu.c196.db.Contact;
import me.dmjohnson.wgu.c196.db.Course;

import static me.dmjohnson.wgu.c196.util.Globals.COURSE_ID;

public class CourseDetailsFragment extends Fragment {

    private CourseViewViewModel model;
    private Integer courseId;
    private Course course;
    private View view;
    private Context context;
    private TextView datesView;
    private TextView statusView;
    private TextView mentorNameView;
    private TextView mentorEmailView;
    private TextView mentorPhoneView;

    private DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    public static CourseDetailsFragment newInstance(Integer courseId) {
        CourseDetailsFragment instance = new CourseDetailsFragment();
        instance.courseId = courseId;
        return instance;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_details, container, false);

        // Find text views
        datesView = view.findViewById(R.id.dates_view);
        statusView = view.findViewById(R.id.status_view);
        mentorNameView = view.findViewById(R.id.mentor_name_view);
        mentorEmailView = view.findViewById(R.id.mentor_email_view);
        mentorPhoneView = view.findViewById(R.id.mentor_phone_view);

        // Set listeners
        view.findViewById(R.id.edit_button).setOnClickListener(v->onClickEditCourse());

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        model = ViewModelProviders.of((FragmentActivity) context).get(CourseViewViewModel.class);

        // Get course
        model.getCourse(courseId).observe((LifecycleOwner) context, newCourse -> {
            course = newCourse;
            setData();
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    private void setData() {
        // dates
        Date startDate = course.getStartDate();
        Date endDate = course.getEndDate();
        datesView.setText(
                String.format("%s - %s",
                        startDate == null ? "No Start Date" : dateFormat.format(startDate),
                        endDate == null ? "No End Date" : dateFormat.format(endDate)
                )
        );
        // status
        switch (course.getStatus()){
            case DROPPED:
                statusView.setText(R.string.course_status_dropped);
                break;
            case PLANNED:
                statusView.setText(R.string.course_status_planned);
                break;
            case COMPLETED:
                statusView.setText(R.string.course_status_completed);
                break;
            case IN_PROGRESS:
                statusView.setText(R.string.course_status_in_progress);
            default:
                statusView.setText("");
                break;
        }
        // Mentor
        Contact mentor = course.getMentor();
        if (mentor != null){
            mentorNameView.setText(mentor.getName());
            mentorEmailView.setText(mentor.getEmail());
            mentorPhoneView.setText(mentor.getPhone());
        }
        else{
            mentorNameView.setText("");
            mentorEmailView.setText("");
            mentorPhoneView.setText("");
        }
    }

    private void onClickEditCourse() {
        Intent intent = new Intent(getContext(), CourseEditActivity.class);
        intent.putExtra(COURSE_ID, courseId);
        startActivity(intent);
    }

}
