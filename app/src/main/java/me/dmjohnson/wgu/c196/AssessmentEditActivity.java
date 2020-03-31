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

import me.dmjohnson.wgu.c196.db.Assessment;

import static me.dmjohnson.wgu.c196.Globals.ASSESSMENT_ID;
import static me.dmjohnson.wgu.c196.Globals.COURSE_ID;

public class AssessmentEditActivity extends AppCompatActivity {
    Integer assessmentId;
    Integer courseId;
    private Assessment assessment;
    private AssessmentEditViewModel model;
    private EditText assessmentNameField;
    private EditText goalDateField;
    private DatePickerDialog goalDateDialog;
    private Spinner assessmentTypeDropdown;
    private SimpleDateFormat dateFormatter;
    private Date goalDateValue;
    private Assessment.Type assessmentTypeValue;

    public static final List<String> ASSESSMENT_TYPE_KEYS = Arrays.asList("Objective", "Performance");
    public static final List<Assessment.Type> ASSESSMENT_TYPE_VALUES = Arrays.asList(Assessment.Type.OBJECTIVE, Assessment.Type.PERFORMANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = ViewModelProviders.of(this).get(AssessmentEditViewModel.class);

        // Get assessment
        MutableLiveData<Assessment> liveAssessment;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(ASSESSMENT_ID)){
            assessmentId = extras.getInt(ASSESSMENT_ID);
            liveAssessment = model.getAssessment(assessmentId);
            this.setTitle("Edit Assessment");
        }
        else if (extras != null && extras.containsKey(COURSE_ID)){
            courseId = extras.getInt(COURSE_ID);
            this.setTitle("New Assessment");
            liveAssessment = model.getNewAssessment(courseId);
        }
        else{
            throw new RuntimeException("Term ID or Course ID must be passed to CourseEditActivity");
        }

        liveAssessment.observe(this, assessment->{
            AssessmentEditActivity.this.assessment = assessment;
            resetFields();
        });

        // Set stuff
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        assessmentNameField = findViewById(R.id.assessment_name_field);
        goalDateField = findViewById(R.id.goal_date_field);
        assessmentTypeDropdown = findViewById(R.id.assessment_type_dropdown);
        goalDateDialog = new DatePickerDialog(this);


        // Setup Date picker
        goalDateField.setOnClickListener(view -> goalDateDialog.show());
        goalDateField.setFocusable(false);

        goalDateDialog.setOnDateSetListener(
                (DatePicker view, int year, int month, int day)->{
                    handleDateSet(year, month, day);
                });

        // Setup save button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            setData();
            model.saveAssessment(assessment);
            finish();
        });

        // Setup dropdown
        assessmentTypeDropdown.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                ASSESSMENT_TYPE_KEYS));
        assessmentTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                assessmentTypeValue = ASSESSMENT_TYPE_VALUES.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Don't select nothing, this is required
            }
        });
    }

    private void resetFields() {
        if (assessment == null){
            assessmentNameField.setText("");
            goalDateField.setText("");
            assessmentTypeDropdown.setSelection(0);
            assessmentTypeValue = ASSESSMENT_TYPE_VALUES.get(0);
        }
        else {
            assessmentNameField.setText(assessment.getName());
            goalDateValue = assessment.getGoalDate();
            if (goalDateValue == null){
                goalDateField.setText("");
            }
            else{
                goalDateField.setText(dateFormatter.format(goalDateValue));
            }
            assessmentTypeValue = assessment.getType();
            assessmentTypeDropdown.setSelection(ASSESSMENT_TYPE_VALUES.indexOf(assessmentTypeValue));
        }
    }


    private void handleDateSet(int year, int month, int day){
        final String dateString = ""+year+"-"+(month+1)+"-"+day;
        goalDateField.setText((dateString));
        goalDateField.clearFocus();
        try {
            goalDateValue = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            return;
        }
    }

    private void setData(){
        assessment.setGoalDate(goalDateValue);
        assessment.setName(assessmentNameField.getText().toString());
        assessment.setType(assessmentTypeValue);
    }

}
