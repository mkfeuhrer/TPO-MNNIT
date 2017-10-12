package com.example.mohit.tpomnnit.charts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class PlacementStat extends AppCompatActivity {

    private DatabaseReference mDatabase;
    String userId, registrationnum;
    ArrayList<String> branch,stats;
    ValueEventListener vel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement_stat);

        mDatabase = FirebaseDatabase.getInstance().getReference("placementstat");
        branch = new ArrayList<String>();
        stats = new ArrayList<String>();

        vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        branch.add(userDetails.getKey().toString());
                        stats.add(userDetails.getValue().toString());
                }
                //solve();
                solve2();
                mDatabase.removeEventListener(vel);
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
        for(int i = 0; i< stats.size();i++) {
            String valspi = stats.get(i);
            if (valspi.equals("n/a"))
                valspi = "0";
            entries.add(new BarEntry(Float.parseFloat(valspi), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "Year");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0;i < stats.size();i++) {
            String tmp = Integer.toString(i+1);
            labels.add(tmp);
        }
        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
    }
    public void solve2()
    {
        LineChart lineChart = (LineChart) findViewById(R.id.chart2);
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(int i = 0; i< stats.size();i++) {
            String valspi = stats.get(i);
            if (valspi.equals("n/a"))
                valspi = "0";
            else if(valspi.equals(""))
                valspi = "0";
            else
                entries.add(new Entry(Float.parseFloat(valspi), i));
        }

        LineDataSet dataset = new LineDataSet(entries, "Year");

        ArrayList<String> labels = new ArrayList<String>();
        for(int i = 0;i < stats.size();i++)
        {
            labels.add(branch.get(i));
        }

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setHighlightEnabled(true);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(2000);
    }

}

