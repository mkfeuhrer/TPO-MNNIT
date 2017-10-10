package com.example.mohit.tpomnnit.student.InterviewExperience;

/**
 * Created by mohit on 23/9/17.
 */

public class Interview {
    public String name,regno,company,experience,date,year,profile;
    public Interview()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Interview(String name, String regno, String year, String company, String experience, String date){
        this.name   = name;
        this.regno      = regno;
        this.year       = year;
        this.company    = company;
        this.experience = experience;
        this.date       = date;

    }
}
