package com.apps.mobile.utn.adtd.geco.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jorge Luis on 31/01/2017.
 */
public class Person {
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("skype")
    private String skype;
   @SerializedName("phone_mobile")
    private String phone_mobile;
    @SerializedName("phone_home")
    private String phone_home;
    @SerializedName("address")
    private String address;
    @SerializedName("birthdate")
    private Date birthdate;

    public Person(String firstname, String lastname, String skype, String phone_mobile, String phone_home, String address, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.skype = skype;
        this.phone_mobile = phone_mobile;
        this.phone_home = phone_home;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone_mobile() {
        return phone_mobile;
    }

    public void setPhone_mobile(String phone_mobile) {
        this.phone_mobile = phone_mobile;
    }

    public String getPhone_home() {
        return phone_home;
    }

    public void setPhone_home(String phone_home) {
        this.phone_home = phone_home;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
