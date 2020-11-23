package com.job.jobapp.CvBuilder.Models;

public  class Project {
    String projectName;
    String projectDescriptions;
    String projectURL;
    String projectStartDate;
    String projectEndDate;

    public Project() {
    }

    public Project(String projectName, String projectDescriptions, String projectURL, String projectStartDate, String projectEndDate) {
        this.projectName = projectName;
        this.projectDescriptions = projectDescriptions;
        this.projectURL = projectURL;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescriptions() {
        return projectDescriptions;
    }

    public void setProjectDescriptions(String projectDescriptions) {
        this.projectDescriptions = projectDescriptions;
    }

    public String getProjectURL() {
        return projectURL;
    }

    public void setProjectURL(String projectURL) {
        this.projectURL = projectURL;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }
}
