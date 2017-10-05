package com.example.mohit.tpomnnit.student.profile;

import java.util.ArrayList;

/**
 * Created by mohit on 29/8/17.
 */

public class UserData {
    public String regnum,name,branch,batch,course,dob,email,skypeid,linkedinid,gender,category,phychal,residentialstatus,guardian;
    public String presentadd, permanentadd,mobileno,guardianmobile,maritalstatus,state,country;
    public String school10,year10,board10,percentage10,school12,year12,board12,percentage12;
    public String spi1,spi2,spi3,spi4,spi5,spi6,spi7,spi8;
    public String project,internship,company;
    public int iseditable,isverified,tpocredit;
    ArrayList<String> companies = new ArrayList<String>();
    ArrayList<String> messages = new ArrayList<String>();

    public UserData() {
    }

    public UserData(String regnum, String name, String branch, String batch, String course, String dob, String email, String skypeid, String linkedinid, String gender, String category, String phychal, String residentialstatus, String guardian,
                    String presentadd, String permanentadd, String mobileno, String guardianmobile, String maritalstatus, String state, String country,
                    String school10, String year10, String board10, String percentage10, String school12, String year12, String board12, String percentage12,
                    String spi1, String spi2, String spi3, String spi4, String spi5, String spi6, String spi7, String spi8, String project, String internship, String company, int tpocredit, int isverified, int iseditable) {
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
        this.isverified     = isverified;
        this.company        = company;
        this.tpocredit      = tpocredit;
    }

    public ArrayList<String> getCompanies() { return companies; }

    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }

    public ArrayList<String> getMessages() { return messages; }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkypeid() {
        return skypeid;
    }

    public void setSkypeid(String skypeid) {
        this.skypeid = skypeid;
    }

    public String getLinkedinid() {
        return linkedinid;
    }

    public void setLinkedinid(String linkedinid) {
        this.linkedinid = linkedinid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhychal() {
        return phychal;
    }

    public void setPhychal(String phychal) {
        this.phychal = phychal;
    }

    public String getResidentialstatus() {
        return residentialstatus;
    }

    public void setResidentialstatus(String residentialstatus) {
        this.residentialstatus = residentialstatus;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getPresentadd() {
        return presentadd;
    }

    public void setPresentadd(String presentadd) {
        this.presentadd = presentadd;
    }

    public String getPermanentadd() {
        return permanentadd;
    }

    public void setPermanentadd(String permanentadd) {
        this.permanentadd = permanentadd;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getGuardianmobile() {
        return guardianmobile;
    }

    public void setGuardianmobile(String guardianmobile) {
        this.guardianmobile = guardianmobile;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSchool10() {
        return school10;
    }

    public void setSchool10(String school10) {
        this.school10 = school10;
    }

    public String getYear10() {
        return year10;
    }

    public void setYear10(String year10) {
        this.year10 = year10;
    }

    public String getBoard10() {
        return board10;
    }

    public void setBoard10(String board10) {
        this.board10 = board10;
    }

    public String getPercentage10() {
        return percentage10;
    }

    public void setPercentage10(String percentage10) {
        this.percentage10 = percentage10;
    }

    public String getSchool12() {
        return school12;
    }

    public void setSchool12(String school12) {
        this.school12 = school12;
    }

    public String getYear12() {
        return year12;
    }

    public void setYear12(String year12) {
        this.year12 = year12;
    }

    public String getBoard12() {
        return board12;
    }

    public void setBoard12(String board12) {
        this.board12 = board12;
    }

    public String getPercentage12() {
        return percentage12;
    }

    public void setPercentage12(String percentage12) {
        this.percentage12 = percentage12;
    }

    public String getSpi1() {
        return spi1;
    }

    public void setSpi1(String spi1) {
        this.spi1 = spi1;
    }

    public String getSpi2() {
        return spi2;
    }

    public void setSpi2(String spi2) {
        this.spi2 = spi2;
    }

    public String getSpi3() {
        return spi3;
    }

    public void setSpi3(String spi3) {
        this.spi3 = spi3;
    }

    public String getSpi4() {
        return spi4;
    }

    public void setSpi4(String spi4) {
        this.spi4 = spi4;
    }

    public String getSpi5() {
        return spi5;
    }

    public void setSpi5(String spi5) {
        this.spi5 = spi5;
    }

    public String getSpi6() {
        return spi6;
    }

    public void setSpi6(String spi6) {
        this.spi6 = spi6;
    }

    public String getSpi7() {
        return spi7;
    }

    public void setSpi7(String spi7) {
        this.spi7 = spi7;
    }

    public String getSpi8() {
        return spi8;
    }

    public void setSpi8(String spi8) {
        this.spi8 = spi8;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getInternship() {
        return internship;
    }

    public void setInternship(String internship) {
        this.internship = internship;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getIseditable() {
        return iseditable;
    }

    public void setIseditable(int iseditable) {
        this.iseditable = iseditable;
    }

    public int getIsverified() {
        return isverified;
    }

    public void setIsverified(int isverified) {
        this.isverified = isverified;
    }

    public int getTpocredit() {
        return tpocredit;
    }

    public void setTpocredit(int tpocredit) {
        this.tpocredit = tpocredit;
    }
}
