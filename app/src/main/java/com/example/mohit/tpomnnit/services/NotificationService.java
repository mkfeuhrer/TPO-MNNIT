package com.example.mohit.tpomnnit.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.mohit.tpomnnit.R;
import com.example.mohit.tpomnnit.notification.NotificationHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by neera on 9/5/2017.
 */

public class NotificationService extends IntentService{

    DatabaseReference mDatabase;
    String companyId;
    int n,i=-10;
    public NotificationService() {
        super("Notification Service");
        Log.i("service", "Constructor called");
        //System.out.println("Service Constructor called");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("service", "In onHandleIntent");
        mDatabase= FirebaseDatabase.getInstance().getReference("companies");
        companyId=mDatabase.push().getKey();
        //System.out.println("in onHandler called");
        while(true)
        {
            Log.i("service", "In while");

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
            mDatabase.addValueEventListener(vel);

            if(i!=n&&i!=-10)
            {
                //Log.i("service", "In while loop");
                i=n;
                System.out.println("new company added");
                NotificationHandler.showNotification(this, R.drawable.company4, "Company added","a new company is added click here to check");
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
