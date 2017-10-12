package com.example.mohit.tpomnnit.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.mohit.tpomnnit.Landing;
import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.login_signup.Login;
import com.example.mohit.tpomnnit.notification.NotificationHandler;
import com.example.mohit.tpomnnit.student.StudentProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by neera on 9/5/2017.
 */

public class NotificationService extends Service {
    DatabaseReference mDatabase;
    String companyId;
    int n,i=-10;
    public static int flag=-1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        flag=1;
        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flag=2;
        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        final int currentId = startId;

        Runnable r = new Runnable() {
            public void run() {

                mDatabase= FirebaseDatabase.getInstance().getReference("companies");
                companyId=mDatabase.push().getKey();
                //System.out.println("in onHandler called");
                while(true)
                {
                    Log.i("service", "In while");
                    SQLiteDatabase data = openOrCreateDatabase("login", MODE_PRIVATE, null); //nobody other can access
                    data.execSQL("create table if not exists student (regno varchar, password varchar);");
                    String s = "select * from student";
                    Cursor cursor = data.rawQuery(s, null); // whatever query i run i can store something in cursor it is a class
                    if (cursor.getCount()==1) {
                        cursor.moveToFirst();
                        String regno= cursor.getString(cursor.getColumnIndex("regno"));
                        ValueEventListener vel = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                n= (int) dataSnapshot.getChildrenCount();
                                if(i==-10)
                                {
                                    i=n;
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        mDatabase.addListenerForSingleValueEvent(vel);

                        if(i!=n&&i!=-10)
                        {
                            Log.i("service", "In while loop");
                            i=n;
                            System.out.println("new company added");
                            NotificationHandler.showNotification(NotificationService.this, R.drawable.company4, "Company added","a new company is added click here to check",regno);
                        }

                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }
        };

        Thread t = new Thread(r);
        t.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        flag=-1;
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
    }

}
