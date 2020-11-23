package com.job.jobapp.CvBuilder.Models;

public class Heading {
    String firstName;
    String lastName;
    String country;
    String city;
    String zip;
    String phoneNumber;
    String email;
    String facebookURL;
    String linkedinURL;
    String gitHubURL;
    String twitterURL;

    public Heading() {

    }

    public Heading(String firstName, String lastName, String country, String city, String zip, String phoneNumber, String email, String facebookURL, String linkedinURL, String gitHubURL, String twitterURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.facebookURL = facebookURL;
        this.linkedinURL = linkedinURL;
        this.gitHubURL = gitHubURL;
        this.twitterURL = twitterURL;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookURL() {
        return facebookURL;
    }

    public void setFacebookURL(String facebookURL) {
        this.facebookURL = facebookURL;
    }

    public String getLinkedinURL() {
        return linkedinURL;
    }

    public void setLinkedinURL(String linkedinURL) {
        this.linkedinURL = linkedinURL;
    }

    public String getGitHubURL() {
        return gitHubURL;
    }

    public void setGitHubURL(String gitHubURL) {
        this.gitHubURL = gitHubURL;
    }

    public String getTwitterURL() {
        return twitterURL;
    }

    public void setTwitterURL(String twitterURL) {
        this.twitterURL = twitterURL;
    }
}
