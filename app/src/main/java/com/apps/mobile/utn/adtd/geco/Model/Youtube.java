package com.apps.mobile.utn.adtd.geco.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jorge Luis on 22/11/2016.
 */
public class Youtube {  // Atributos

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("url")
    private String url;

    public Youtube(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
