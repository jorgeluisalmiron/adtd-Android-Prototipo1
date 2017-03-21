package com.apps.mobile.utn.adtd.geco.Api;

import com.apps.mobile.utn.adtd.geco.Model.Person;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge Luis on 31/01/2017.
 */
public class PersonResponse {

    @SerializedName("msg")
    private String msg;
    @SerializedName("profile")
    private Person profile;

    public PersonResponse(String msg, Person profile) {
        this.msg = msg;
        this.profile = profile;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Person getProfile() {
        return profile;
    }

    public void setProfile(Person profile) {
        this.profile = profile;
    }
}
