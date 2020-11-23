package com.job.jobapp.Models;

public class Company {
    String imageURL;
    String companyName;
    String companyEmail;
    String companyPassword;
    String companyDescription;
    String phNumber;
    String city;
    String country;
    String zip;
    String address;
    String webLink;
    public Company(String imageURL, String companyName, String companyEmail, String companyPassword, String companyDescription,String phNumber, String city, String country, String zip, String address, String webLink) {
        this.imageURL = imageURL;
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
        this.companyDescription = companyDescription;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.address = address;
        this.webLink = webLink;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
