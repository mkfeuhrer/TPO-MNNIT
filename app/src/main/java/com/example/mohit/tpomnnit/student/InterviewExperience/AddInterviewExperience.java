package com.example.mohit.tpomnnit.student.InterviewExperience;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.mohit.tpomnnit.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddInterviewExperience extends AppCompatActivity {


    private static final String TAG = AddInterviewExperience.class.getSimpleName();
    private DatabaseReference mDatabase;
    private String interviewId;
    private Button addinterview;
    private EditText company,year,date,experience,time,name,regno;
    private int Year, month, day,hours,minutes;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interview_experience);

        mDatabase = FirebaseDatabase.getInstance().getReference("interviews");
        interviewId = mDatabase.push().getKey();


        addinterview   = (Button)findViewById(R.id.addinterview);
        year           = (EditText)findViewById(R.id.year);
        company        = (EditText)findViewById(R.id.company);
        experience     = (EditText)findViewById(R.id.experience);
        name           = (EditText)findViewById(R.id.name);
        regno           = (EditText)findViewById(R.id.location);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date = (EditText) findViewById(R.id.textDate);
        time = (EditText) findViewById(R.id.textTime);
        showTime(hours,minutes);
        showDate(Year, month+1, day);
        date.setFocusable(false);
        time.setFocusable(false);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime(v);
            }
        });

        addinterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for already existed userId
                if (TextUtils.isEmpty(interviewId)) {
                } else {
                    String curtime = time.getText().toString() + " " + date.getText().toString();
                    Interview interview = new Interview(name.getText().toString().trim(), regno.getText().toString().trim(), year.getText().toString().trim(), company.getText().toString().trim(), experience.getText().toString().trim(), curtime);
                    addCompanyChangeListener(interview);
                }


            }
        });
    }


    private void addCompanyChangeListener(final Interview interview) {
        // User data change listener
        mDatabase.child(interviewId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Interview interview1 = interview;

                // Check for null
                if (interview1 == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                final String companyId = mDatabase.push().getKey();
                mDatabase.child(companyId).setValue(interview1);
                Log.e(TAG, "User data is changed!" + interview1.company + ", " + interview1.year);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }
    public void setTime(View view)
    {
        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, Year, month, day);
        }
        else if(id == 998)
        {
            return new TimePickerDialog(this,myTimeListener,hours,minutes,false);
        }
        return null;
    }


    private TimePickerDialog.OnTimeSetListener myTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showTime(hourOfDay,minute);
                }
            };
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private  void showTime(int hours,int minutes)
    {
        time.setText(new StringBuilder().append(hours).append(":")
                .append(minutes));
    }
    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

}
