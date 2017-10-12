package crossfit_juan.chk.com.crossfitjuan.Common;

import android.content.Context;

import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class User {
    private static User Me=new User();

    private static UserData data;
    private static Context hereActivityContext;
    private static String hereActivity;
    private User(){}
    public static User getInstance(){
        return Me;
    }
    public void setUser(UserData data){
        User.data =data;
    }

    public UserData getData() {
        return data;
    }

    public static Context getHereActivityContext() {
        return hereActivityContext;
    }

    public static void setHereActivityContext(Context hereActivityContext) {
        User.hereActivityContext = hereActivityContext;
    }

    public static String getHereActivity() {
        return hereActivity;
    }

    public static void setHereActivity(String hereActivity) {
        User.hereActivity = hereActivity;
    }
}
