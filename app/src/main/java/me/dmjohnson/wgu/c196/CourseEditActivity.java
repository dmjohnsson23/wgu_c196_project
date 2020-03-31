package me.dmjohnson.wgu.c196;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.dmjohnson.wgu.c196.db.Course;

import static me.dmjohnson.wgu.c196.Globals.COURSE_ID;
import static me.dmjohnson.wgu.c196.Globals.TERM_ID;

public class CourseEditActivity extends AppCompatActivity {
    int courseId;
    int termId;
    private Course course;
    private CourseEditViewModel model;
    private EditText courseNameField;
    private EditText startDateField;
    private EditText endDateField;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;
    private SimpleDateFormat dateFormatter;
    private Spinner statusDropdown;

    // I would use a HashMap but it doesn't play well with the Spinner class....
    private static final List<String> COURSE_STATUS_KEYS = Arrays.asList("Planned", "In Progress", "Dropped", "Completed");
    private static final List<Course.Status> COURSE_STATUS_VALUES =
            Arrays.asList(Course.Status.COMPLETED, Course.Status.IN_PROGRESS, Course.Status.DROPPED, Course.Status.COMPLETED);
    private Date startDateValue;
    private Date endDateValue;
    private Course.Status statusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = ViewModelProviders.of(this).get(CourseEditViewModel.class);

        MutableLiveData<Course> liveCourse;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(COURSE_ID)){
            courseId = extras.getInt(COURSE_ID);
            liveCourse = model.getCourse(courseId);
            this.setTitle("Edit Course");
        }
        else if (extras != null && extras.containsKey(TERM_ID)){
            termId = extras.getInt(TERM_ID);
            this.setTitle("New Course");
            liveCourse = model.getNewCourse(termId);
        }
        else{
            throw new RuntimeException("Term ID or Course ID must be passed to CourseEditActivity");
        }

        liveCourse.observe(this, course->{
            this.course = course;
            resetFields();
        });

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        courseNameField = findViewById(R.id.course_name_field);
        startDateField = findViewById(R.id.start_date_field);
        endDateField = findViewById((R.id.end_date_field));
        startDateDialog = new DatePickerDialog(this);
        endDateDialog = new DatePickerDialog((this));
        statusDropdown = findViewById(R.id.course_status_dropdown);

        ArrayAdapter<String> courseStatusAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                COURSE_STATUS_KEYS);
        statusDropdown.setAdapter(courseStatusAdapter);


        statusDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusValue =COURSE_STATUS_VALUES.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Don't select nothing, this field is required
            }
        });

        startDateField.setOnClickListener(view -> startDateDialog.show());
        startDateField.setFocusable(false);
        endDateField.setOnClickListener(view -> endDateDialog.show());
        endDateField.setFocusable(false);

        startDateDialog.setOnDateSetListener(
                (DatePicker view, int year, int month, int day)->{
                    handleDateSet(startDateField, year, month, day);
                });
        endDateDialog.setOnDateSetListener(
                (DatePicker view, int year, int month, int day)->{
                    handleDateSet(endDateField, year, month, day);
                });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            setData();
            model.saveCourse(course);
            finish();
        });
    }

    private void resetFields() {
        if (course == null){
            courseNameField.setText("");
            startDateField.setText("");
            endDateField.setText("");
            statusDropdown.setSelection(0);
        }
        else {
            courseNameField.setText(course.getTitle());
            startDateValue = course.getStartDate();
            endDateValue = course.getEndDate();
            if (startDateValue == null){
                startDateField.setText("");
            }
            else{
                startDateField.setText(dateFormatter.format(startDateValue));
            }
            if (endDateValue == null){
                endDateField.setText("");
            }
            else{
                endDateField.setText(dateFormatter.format(endDateValue));
            }
            statusValue = course.getStatus();
            statusDropdown.setSelection(COURSE_STATUS_VALUES.indexOf(statusValue));
        }
    }



    private void handleDateSet(EditText fieldToUpdate, int year, int month, int day){
        final String dateString = ""+year+"-"+(month+1)+"-"+day;
        fieldToUpdate.setText((dateString));
        fieldToUpdate.clearFocus();
        final Date date;
        try {
            date = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            return;
        }
        if (fieldToUpdate == startDateField){
            endDateDialog.getDatePicker().setMinDate(date.getTime());
            startDateValue = date;
        }
        else if (fieldToUpdate == endDateField){
            startDateDialog.getDatePicker().setMaxDate(date.getTime());
            endDateValue = date;
        }
    }

    private void setData(){
        course.setTitle(courseNameField.getText().toString());
        course.setStatus(statusValue);
        course.setStartDate(startDateValue);
        course.setEndDate(endDateValue);
    }

}
