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

    public Companies(String name,String ctc,String location,String profile,String year,String ppo,String companyid,String time,String link,ArrayList<String> branch,ArrayList<String> registeredstudents,ArrayList<String> selectedstudents)
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
