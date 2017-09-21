package com.example.mohit.tpomnnit.student.profile;

import com.google.firebase.database.IgnoreExtraProperties;
/**
 * Created by mohit on 29/8/17.
 */
@IgnoreExtraProperties
public class Users {
    public String name;
    public String year;
    public String studentid;
    public String password;
    public String userid;
    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Users() {
    }

    public Users(String name, String year,String studentid,String password,String userid) {
        this.name       = name;
        this.year      = year;
        this.studentid  = studentid;
        this.password   = password;
        this.userid     = userid;
    }
}
