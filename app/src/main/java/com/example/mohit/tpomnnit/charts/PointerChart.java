package com.example.mohit.tpomnnit.charts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.UserData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PointerChart extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String userId, registrationnum,spi[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer_chart);

        mDatabase = FirebaseDatabase.getInstance().getReference("userdata");
        userId = mDatabase.push().getKey();
        registrationnum = getIntent().getStringExtra("reg");
        spi = new String[8];

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData user = dataSnapshot.getValue(UserData.class);
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {

                    if (registrationnum.equals(userDetails.child("regnum").getValue().toString())) {
                        spi[0] = userDetails.child("spi1").getValue().toString();
                        spi[1] = userDetails.child("spi2").getValue().toString();
                        spi[2] = userDetails.child("spi3").getValue().toString();
                        spi[3] = userDetails.child("spi4").getValue().toString();
                        spi[4] = userDetails.child("spi5").getValue().toString();
                        spi[5] = userDetails.child("spi6").getValue().toString();
                        spi[6] = userDetails.child("spi7").getValue().toString();
                        spi[7] = userDetails.child("spi8").getValue().toString();
                        Log.e("sp",spi[0] + " " + spi[1]);
                    }
                }
                solve();
                solve2();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(vel);
    }
    public void solve()
    {
        BarChart lineChart = (BarChart) findViewById(R.id.chart);
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i< 8;i++) {
            String valspi = spi[i];
            if (valspi.equals("n/a"))
                valspi = "0";
            entries.add(new BarEntry(Float.parseFloat(valspi), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "Semester");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");

        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
//        dataset.setDrawCubic(true);
//        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(2000);
    }
    public void solve2()
    {
        LineChart lineChart = (LineChart) findViewById(R.id.chart2);
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i = 0; i< 8;i++) {
            String valspi = spi[i];
            if (valspi.equals("n/a"))
                valspi = "0";
            entries.add(new Entry(Float.parseFloat(valspi), i));
        }

        LineDataSet dataset = new LineDataSet(entries, "Semester");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        labels.add("6");
        labels.add("7");
        labels.add("8");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(2000);
    }

}

