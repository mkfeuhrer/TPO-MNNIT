package com.example.mohit.tpomnnit.student.InterviewExperience;

/**
 * Created by mohit on 23/9/17.
 */

public class Interview {
    public String username,regno,company,experience,date,year;

    public String getExperience(){return experience;}
    public String getDate(){return date;}
    public String getYear(){return year;}
    public String getName(){return username;}
    public String getReg(){return regno;}
    public String getCompany(){return company;}

    public Interview(String username,String regno,String year,String company,String experience,String date){
        this.username   = username;
        this.regno      = regno;
        this.year       = year;
        this.company    = company;
        this.experience = experience;
        this.date       = date;

    }
}
