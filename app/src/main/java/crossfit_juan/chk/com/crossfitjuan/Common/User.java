package crossfit_juan.chk.com.crossfitjuan.Common;

import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class User {
    private static User Me=new User();

    private static UserData data;

    private User(){}
    public static User getInstance(){
        return Me;
    }
    public void setUser(UserData data){
        this.data=data;
    }

    public UserData getData() {
        return data;
    }
}
