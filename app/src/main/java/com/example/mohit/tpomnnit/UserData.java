package com.example.mohit.tpomnnit;

/**
 * Created by mohit on 29/8/17.
 */

public class UserData {
    public String regnum,name,branch,batch,course,dob,email,skypeid,linkedinid,gender,category,phychal,residentialstatus,guardian;
    public String presentadd, permanentadd,mobileno,guardianmobile,maritalstatus,state,country;
    public String school10,year10,board10,percentage10,school12,year12,board12,percentage12;
    public String spi1,spi2,spi3,spi4,spi5,spi6,spi7,spi8;
    public String project,internship;
    public int iseditable;

    public UserData() {
    }

    public UserData(String regnum,String name,String branch,String batch,String course,String dob,String email,String skypeid,String linkedinid,String gender,String category,String phychal,String residentialstatus,String guardian,
                    String presentadd,String permanentadd,String mobileno,String guardianmobile,String maritalstatus,String state,String country,
                    String school10,String year10,String board10,String percentage10,String school12,String year12,String board12,String percentage12,
                    String spi1,String spi2,String spi3,String spi4,String spi5,String spi6,String spi7,String spi8,String project,String internship,int iseditable) {
        this.name       = name;
        this.regnum     = regnum;
        this.branch     = branch;
        this.course     = course;
        this.batch      = batch;
        this.dob        = dob;
        this.email      = email;
        this.skypeid    = skypeid;
        this.linkedinid = linkedinid;
        this.gender     = gender;
        this.category   = category;
        this.phychal    = phychal;
        this.residentialstatus = residentialstatus;
        this.guardian   = guardian;
        this.presentadd = presentadd;
        this.permanentadd = permanentadd;
        this.mobileno   = mobileno;
        this.guardianmobile = guardianmobile;
        this.maritalstatus  = maritalstatus;
        this.state          = state;
        this.country        = country;
        this.school10       = school10;
        this.year10         = year10;
        this.board10        = board10;
        this.percentage10   = percentage10;
        this.school12       = school12;
        this.year12         = year12;
        this.board12        = board12;
        this.percentage12   = percentage12;
        this.spi1           = spi1;
        this.spi2           = spi2;
        this.spi3           = spi3;
        this.spi4           = spi4;
        this.spi5           = spi5;
        this.spi6           = spi6;
        this.spi7           = spi7;
        this.spi8           = spi8;
        this.project        = project;
        this.internship     = internship;
        this.iseditable     = iseditable;
    }
}
