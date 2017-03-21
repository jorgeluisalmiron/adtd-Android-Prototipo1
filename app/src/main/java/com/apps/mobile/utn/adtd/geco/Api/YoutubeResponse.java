package com.apps.mobile.utn.adtd.geco.Api;


import com.apps.mobile.utn.adtd.geco.Model.Youtube;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jorge Luis on 25/01/2017.
 */
public class YoutubeResponse {

    @SerializedName("msg")
    private String msg;
    @SerializedName("results")
    private List<Youtube> results;


    public YoutubeResponse(String msg, List<Youtube> results) {
        this.msg = msg;
        this.results = results;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Youtube> getResults() {
        return results;
    }

    public void setResults(List<Youtube> results) {
        this.results = results;
    }
}
