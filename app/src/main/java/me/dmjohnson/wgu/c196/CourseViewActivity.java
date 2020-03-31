package me.dmjohnson.wgu.c196;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import me.dmjohnson.wgu.c196.ui.CoursePagerAdapter;

import static me.dmjohnson.wgu.c196.Globals.COURSE_ID;

public class CourseViewActivity extends AppCompatActivity {
    Integer courseId;
    private CourseViewViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(COURSE_ID)){
            courseId = extras.getInt(COURSE_ID);
        }
        else{
            throw new RuntimeException("No Course Id Provided to CourseViewActivity");
        }
        setTitle("");
        setContentView(R.layout.activity_course_view);
        CoursePagerAdapter coursePagerAdapter = new CoursePagerAdapter(this, getSupportFragmentManager(), courseId);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(coursePagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        model = ViewModelProviders.of(this).get(CourseViewViewModel.class);

        model.getCourse(courseId).observe(this, course -> setTitle(course.getTitle()));

    }
}