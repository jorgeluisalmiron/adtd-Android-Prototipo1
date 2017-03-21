package com.apps.mobile.utn.adtd.geco.Model;

/**
 * Created by Jorge Luis on 01/02/2017.
 */
public class Profile {

    private String TOKEN;
    private String email;
    private Person person;
    private static Profile instance=null;

    private Profile() {
           }

    public static Profile getInstance() {

        if (instance == null) {
            instance = new Profile();

        }
        return instance;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
