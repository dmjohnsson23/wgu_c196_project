package me.dmjohnson.wgu.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.CourseAlert;
import me.dmjohnson.wgu.c196.ui.CoursePagerAdapter;
import me.dmjohnson.wgu.c196.util.AlertCreator;

import static me.dmjohnson.wgu.c196.util.Globals.COURSE_ID;
import static me.dmjohnson.wgu.c196.util.Utils.getEventIntent;

public class CourseViewActivity extends AppCompatActivity {
    Integer courseId;
    private CourseViewViewModel model;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get course id
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(COURSE_ID)){
            courseId = extras.getInt(COURSE_ID);
        }
        else{
            throw new RuntimeException("No Course Id Provided to CourseViewActivity");
        }

        // Setup activity
        model = ViewModelProviders.of(this).get(CourseViewViewModel.class);
        setContentView(R.layout.activity_course_view);
        CoursePagerAdapter coursePagerAdapter = new CoursePagerAdapter(this, getSupportFragmentManager(), courseId);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(coursePagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        model.getCourse(courseId).observe(this, newCourse -> {
            course = newCourse;
            toolbar.setTitle(course.getTitle());
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_course_view, menu);
        return true;
    }

    public void onDeleteAction(MenuItem item) {
        model.getCourse(courseId).removeObservers(this);
        model.deleteCourse(course);
        finish();
    }

    public void onAlertEndDateAction(MenuItem item) {
        CourseAlert alert = new CourseAlert(course, false);
        AlertCreator.createAlert(this, alert);
    }

    public void onAlertStartDateAction(MenuItem item) {
        CourseAlert alert = new CourseAlert(course, false);
        AlertCreator.createAlert(this, alert);
    }

    public void onShareNoteAction(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_SUBJECT, course.getTitle()+" Course Notes")
                .putExtra(Intent.EXTRA_TEXT, course.getNote());
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_using)));

    }
}