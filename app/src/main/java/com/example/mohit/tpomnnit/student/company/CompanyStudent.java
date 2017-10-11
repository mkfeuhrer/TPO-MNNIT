package com.example.mohit.tpomnnit.student.company;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.student.profile.SectionsPageAdapter;

public class CompanyStudent extends AppCompatActivity {


    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_student);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        contextOfApplication=getApplicationContext();
        regnum=getIntent().getStringExtra("reg");
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new CompanyTab1(), "Current Opening");
        adapter.addFragment(new CompanyTab2(), "Registered");
        viewPager.setAdapter(adapter);
    }
    public static Context contextOfApplication;
    public static String regnum;
    public static String getRegnum()
    {
        return regnum;
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

}
