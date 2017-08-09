package com.team_fitm.myown.fitm_mobile.DataModels;

/**
 * Created by Myown on 2017-08-09.
 */

public class UserData {

    private String user_email;
    private String user_name;
    private String user_gender;
    private String user_phone_number;

    public UserData(){
        this.user_email = null;
        this.user_gender = null;
        this.user_phone_number = null;
        this.user_name = null;
    }

    public UserData(String email, String name, String gender, String phone_number){
        this.user_name = name;
        this.user_email = email;
        this.user_gender = gender;
        this.user_phone_number = phone_number;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }
}
