package me.dmjohnson.wgu.c196;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.dmjohnson.wgu.c196.db.Term;

import static me.dmjohnson.wgu.c196.util.Globals.TERM_ID;

public class TermEditActivity extends AppCompatActivity {
    int termId;
    private Term term;
    private TermEditViewModel model;
    private EditText termNameField;
    private EditText startDateField;
    private  EditText endDateField;
    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;
    private SimpleDateFormat dateFormatter;
    private Date startDateValue;
    private Date endDateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        model = ViewModelProviders.of(this).get(TermEditViewModel.class);

        // Get Term
        MutableLiveData<Term> liveTerm;
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            termId = extras.getInt(TERM_ID);
            liveTerm = model.getTerm(termId);
            this.setTitle("Edit Term");
        }
        else{
            this.setTitle("New Term");
            liveTerm = model.getTerm();
        }

        // Get Fields
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        termNameField = findViewById(R.id.term_name_field);
        startDateField = findViewById(R.id.start_date_field);
        endDateField = findViewById((R.id.end_date_field));
        startDateDialog = new DatePickerDialog(this);
        endDateDialog = new DatePickerDialog((this));

        // Get data from term
        liveTerm.observe(this, term->{
            TermEditActivity.this.term = term;
            resetFields();
        });

        // Set listeners and the like
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
        fab.setOnClickListener(view -> onClickSave());
    }

    private void onClickSave() {
        setData();
        model.saveTerm(term);
        finish();
    }

    private void resetFields() {
        if (term == null){
            termNameField.setText("");
            startDateField.setText("");
            endDateField.setText("");
        }
        else {
            termNameField.setText(term.getTitle());
            startDateValue = term.getStartDate();
            endDateValue = term.getEndDate();
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
        term.setStartDate(startDateValue);
        term.setEndDate(endDateValue);
        term.setTitle(termNameField.getText().toString());
    }

}
