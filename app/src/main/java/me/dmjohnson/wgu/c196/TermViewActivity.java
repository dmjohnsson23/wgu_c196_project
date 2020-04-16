package me.dmjohnson.wgu.c196;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.dmjohnson.wgu.c196.db.Course;
import me.dmjohnson.wgu.c196.db.Term;
import me.dmjohnson.wgu.c196.db.TermAlert;
import me.dmjohnson.wgu.c196.ui.CourseListAdapter;
import me.dmjohnson.wgu.c196.util.AlertCreator;

import static me.dmjohnson.wgu.c196.util.Globals.TERM_ID;
import static me.dmjohnson.wgu.c196.util.Utils.getEventIntent;

public class TermViewActivity extends AppCompatActivity {
    Integer termId;
    Term term;
    List<Course> courses = new ArrayList<>();
    DateFormat dateFormat = SimpleDateFormat.getDateInstance();
    private TermViewViewModel model;
    private RecyclerView recyclerView;
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

        // Get term
        Bundle extras = getIntent().getExtras();
        LiveData<Term> liveTerm;
        if (extras != null && extras.containsKey(TERM_ID)){
            termId = extras.getInt(TERM_ID);
            liveTerm = model.getTerm(termId);
        }
        else{
            throw new RuntimeException("No Term ID Provided to TermViewActivity");
        }

        // Setup recyclerview
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get data from term
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

        // Get courses and course data
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

        // Get buttons & set listeners
        FloatingActionButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> onClickEditTerm());

        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> onClickNewCourse());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_term_view, menu);
        return true;
    }

    private void onClickNewCourse() {
        Intent intent = new Intent(this, CourseEditActivity.class);
        intent.putExtra(TERM_ID, termId);
        startActivity(intent);
    }

    private void onClickEditTerm() {
        Intent intent = new Intent(this, TermEditActivity.class);
        intent.putExtra(TERM_ID, termId);
        startActivity(intent);
    }

    public void onAlertStartDateAction(MenuItem item) {
        TermAlert alert = new TermAlert(term, true);
        AlertCreator.createAlert(this, alert);
    }

    public void onAlertEndDateAction(MenuItem item) {
        TermAlert alert = new TermAlert(term, false);
        AlertCreator.createAlert(this, alert);
    }

    public void onDeleteAction(MenuItem item) {
        if (courses.size() == 0){
            model.getCourses(termId).removeObservers(this);
            model.getTerm(termId).removeObservers(this);
            model.deleteTerm(term);
            finish();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cannot delete term that has courses in it!")
                    .setTitle("Error");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
