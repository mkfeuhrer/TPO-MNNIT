package com.example.mohit.tpomnnit.tpo;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.company.Companies;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddCompany extends AppCompatActivity {

    private static final String TAG = AddCompany.class.getSimpleName();
    private DatabaseReference mDatabase;
    private String companyId;
    private Button addcompany;
    private EditText year,ctc,location,name,time,date,ppo,profile,link;
    private int Year, month, day,hours,minutes;
    Calendar calendar;
    private TextView tvNotificationDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        mDatabase = FirebaseDatabase.getInstance().getReference("companies");
        companyId = mDatabase.push().getKey();


        addcompany   = (Button)findViewById(R.id.addcompany);
        year     = (EditText)findViewById(R.id.year);
        name     = (EditText)findViewById(R.id.companyname);
        ctc      = (EditText)findViewById(R.id.ctc);
        location   = (EditText)findViewById(R.id.location);
        ppo      = (EditText)findViewById(R.id.ppo);
        profile   = (EditText)findViewById(R.id.profile);
        //link=(EditText)findViewById(R.id.link);
        date = (EditText) findViewById(R.id.textDate);
        time = (EditText) findViewById(R.id.textTime);
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
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

        MultiSelectionSpinner spinner=(MultiSelectionSpinner) findViewById(R.id.input1);

        List<String> list = new ArrayList<String>();
        list.add("CSE");
        list.add("IT");
        list.add("ECE");
        list.add("EE");
        list.add("CHE");
        list.add("ME");
        list.add("PIE");
        list.add("CIVIL");
        list.add("BIO");
        spinner.setItems(list);

        addcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check for already existed userId
                if (TextUtils.isEmpty(companyId)) {
                }
                else
                {
                    ArrayList<String> branch = new ArrayList<String>();
                    ArrayList<String> registeredstudents = new ArrayList<String>();
                    ArrayList<String> selectedstudents = new ArrayList<String>();
                    String deadline=time.getText().toString()+" "+date.getText().toString();
                    Companies company = new Companies(name.getText().toString().trim(),ctc.getText().toString().trim(),location.getText().toString().trim(),profile.getText().toString().trim(),year.getText().toString().trim(),ppo.getText().toString().trim(),companyId,deadline,"www.google.com",null,null,null);
                    addCompanyChangeListener(company);
                }
                    //createCompany(name.getText().toString().trim(), year.getText().toString().trim(), ctc.getText().toString().trim(), location.getText().toString().trim(),companyId);

//                finish();
//                Intent i = new Intent(AddCompany.this,TpoHome.class);
//                i.putExtra("company",name.getText().toString().trim());
//                startActivity(i);
            }
        });


    }

    private void addCompanyChangeListener(final Companies company) {
        // User data change listener
        mDatabase.child(companyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Companies company1 = company;

                // Check for null
                if (company1 == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                final String companyId = mDatabase.push().getKey();
                mDatabase.child(companyId).setValue(company1);
                Log.e(TAG, "User data is changed!" + company1.name + ", " + company1.year);
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
