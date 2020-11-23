package com.job.jobapp.CvBuilder.Models;

public class Work {
    String jobTitle;
    String companyName;
    String employer;
    String address;
    String startDate;
    String endDate;
    String currentlyWorkHere;

    public Work() {
    }

    public Work(String jobTitle, String companyName, String employer, String address, String startDate, String endDate, String currentlyWorkHere) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.employer = employer;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentlyWorkHere = currentlyWorkHere;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrentlyWorkHere() {
        return currentlyWorkHere;
    }

    public void setCurrentlyWorkHere(String currentlyWorkHere) {
        this.currentlyWorkHere = currentlyWorkHere;
    }
}
