package com.example.mohit.tpomnnit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyProfile extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private String regno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        regno=getIntent().getStringExtra("reg");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        contextOfApplication = getApplicationContext();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }
    public String getRegno()
    {
        return regno;
    }

    private void setupViewPager(ViewPager viewPager) {
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Personal");
        adapter.addFragment(new Tab2Fragment(), "Academic");
        adapter.addFragment(new Tab3Fragment(), "Project & Intern");
        adapter.addFragment(new Tab4Fragment(), "Photo & Resume");
        adapter.addFragment(new Tab5Fragment(), "Password");
        viewPager.setAdapter(adapter);
    }
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
  /*  @Override
    public void onBackPressed()
    {
        finish();
        Intent i = new Intent(MyProfile.this,StudentProfile.class);
        startActivity(i);
    }*/
}
