package com.apps.mobile.utn.adtd.geco.Api;


import com.apps.mobile.utn.adtd.geco.Model.Link;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jorge Luis on 25/01/2017.
 */
public class LinkResponse {

    @SerializedName("msg")
    private String msg;
    @SerializedName("results")
    private List<Link> results;


    public LinkResponse(String msg, List<Link> results) {
        this.msg = msg;
        this.results = results;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Link> getResults() {
        return results;
    }

    public void setResults(List<Link> results) {
        this.results = results;
    }
}
