package com.apps.mobile.utn.adtd.geco.Api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge Luis on 24/01/2017.
 */
public class TokenResponse {

    @SerializedName("token")
    public String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
