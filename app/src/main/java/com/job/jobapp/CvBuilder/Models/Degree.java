package com.job.jobapp.CvBuilder.Models;

public class Degree {
    String schoolName;
    String schoolLocation;
    String degreeName;
    String filedOfStudy;
    String degreeStartDate;
    String degreeEndDate;

    public Degree() {
    }

    public Degree(String schoolName, String schoolLocation, String degreeName, String filedOfStudy, String degreeStartDate, String degreeEndDate) {
        this.schoolName = schoolName;
        this.schoolLocation = schoolLocation;
        this.degreeName = degreeName;
        this.filedOfStudy = filedOfStudy;
        this.degreeStartDate = degreeStartDate;
        this.degreeEndDate = degreeEndDate;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolLocation() {
        return schoolLocation;
    }

    public void setSchoolLocation(String schoolLocation) {
        this.schoolLocation = schoolLocation;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getFiledOfStudy() {
        return filedOfStudy;
    }

    public void setFiledOfStudy(String filedOfStudy) {
        this.filedOfStudy = filedOfStudy;
    }

    public String getDegreeStartDate() {
        return degreeStartDate;
    }

    public void setDegreeStartDate(String degreeStartDate) {
        this.degreeStartDate = degreeStartDate;
    }

    public String getDegreeEndDate() {
        return degreeEndDate;
    }

    public void setDegreeEndDate(String degreeEndDate) {
        this.degreeEndDate = degreeEndDate;
    }
}
