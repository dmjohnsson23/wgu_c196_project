package me.dmjohnson.wgu.c196;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.Term;
import me.dmjohnson.wgu.c196.ui.CourseListAdapter;

import static me.dmjohnson.wgu.c196.Globals.TERM_ID;

public class TermViewActivity extends AppCompatActivity {
    Integer termId;
    Term term;
    List<Course> courses = new ArrayList<>();
    private TermViewViewModel model;
    private RecyclerView recyclerView;
    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy");
    private CourseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView datesArea = findViewById(R.id.dates_text);

        model = ViewModelProviders.of(this).get(TermViewViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(TERM_ID)){
            termId = extras.getInt(TERM_ID);
            LiveData<Term> liveTerm = model.getTerm(termId);
            liveTerm.observe(this, term->{
                this.term = term;
                System.out.println(term.getTitle());
                toolbar.setTitle(term.getTitle());
                Date startDate = term.getStartDate();
                Date endDate = term.getEndDate();
                datesArea.setText(
                        String.format("%s - %s",
                                startDate == null ? "No Start Date" : dateFormat.format(startDate),
                                endDate == null ? "No End Date" : dateFormat.format(endDate)
                        )
                );
            });
        }
        else{
            throw new RuntimeException("No Term ID Provided to TermViewActivity");
        }


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.getCourses(termId).observe(this, newCourses->{
            courses.clear();
            courses.addAll(newCourses);

            if (adapter== null){
                adapter = new CourseListAdapter(courses, this);
                recyclerView.setAdapter(adapter);
            }
            else{
                adapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TermEditActivity.class);
            intent.putExtra(TERM_ID, termId);
            startActivity(intent);
        });

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseEditActivity.class);
            intent.putExtra(TERM_ID, termId);
            startActivity(intent);
        });
    }
}
