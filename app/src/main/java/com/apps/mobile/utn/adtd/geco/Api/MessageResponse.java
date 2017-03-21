package com.apps.mobile.utn.adtd.geco.Api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge Luis on 02/02/2017.
 */
public class MessageResponse {

    @SerializedName("msg")
    private String msg;

    public MessageResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
