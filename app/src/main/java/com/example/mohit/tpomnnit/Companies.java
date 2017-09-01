package com.example.mohit.tpomnnit;

import java.util.ArrayList;

/**
 * Created by mohit on 31/8/17.
 */

public class Companies {

    public String name,ctc,location,profile,year,ppo,companyid,time,link;
    ArrayList<String> branch = new ArrayList<String>();
    ArrayList<String> registeredstudents = new ArrayList<String>();
    ArrayList<String> selectedstudents = new ArrayList<String>();
    public Companies(){
    }

    public ArrayList<String> getBranch() {
        return branch;
    }

    public void setBranch(ArrayList<String> branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPpo() {
        return ppo;
    }

    public void setPpo(String ppo) {
        this.ppo = ppo;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<String> getRegisteredstudents() {
        return registeredstudents;
    }

    public void setRegisteredstudents(ArrayList<String> registeredstudents) {
        this.registeredstudents = registeredstudents;
    }

    public ArrayList<String> getSelectedstudents() {
        return selectedstudents;
    }

    public void setSelectedstudents(ArrayList<String> selectedstudents) {
        this.selectedstudents = selectedstudents;
    }

    public Companies(String name, String ctc, String location, String profile, String year, String ppo, String companyid, String time, String link, ArrayList<String> branch, ArrayList<String> registeredstudents, ArrayList<String> selectedstudents)
    {
        this.name = name;
        this.ctc  = ctc;
        this.location = location;
        this.profile = profile;
        this.year = year;
        this.ppo = ppo;
        this.branch = branch;
        this.companyid = companyid;
        this.time = time;
        this.registeredstudents = registeredstudents;
        this.selectedstudents = selectedstudents;
        this.link = link;
    }
}
