package crossfit_juan.chk.com.crossfitjuan.Common;

import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class User {
    private static User Me=new User();

    private UserData data;

    private User(){}
    public static User getInstance(){
        return Me;
    }
    public void setUser(UserData a){
        data=new UserData();
        data.setUser_access_key(a.getUser_access_key());
        data.setUser_email(a.getUser_email());
        data.setUser_gender(a.getUser_gender());
        data.setUser_name(a.getUser_name());
        data.setUser_phone_number(a.getUser_phone_number());
    }

    public UserData getData() {
        return data;
    }
}
