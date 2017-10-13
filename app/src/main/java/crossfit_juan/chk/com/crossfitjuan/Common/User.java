package crossfit_juan.chk.com.crossfitjuan.Common;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;

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

    public static void LoadStomUserInfo() throws JSONException {
        JSONObject send_data = new JSONObject();
        send_data.put("id_email", User.getInstance().getData().getUser_email());
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_GET_USER_DATA_STOM, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        result_data = new JSONObject(result);
        result_code = result_data.getInt("code");
        if (result_code == 2300) {
            JSONObject response=result_data.getJSONObject("response");
            int check_register=response.getInt("check_register");
            String locker_start=response.getString("locker_start");
            String locker_finish=response.getString("locker_finish");
            String locker_num=response.getString("locker_num");
            String gender=response.getString("gender");
            String birthday=response.getString("birthday");
            //String doc_type=response.getString("doc_type");
            int certification=response.getInt("certification");;
            int remain_break_day=response.getInt("remain_break_day");;
            String finish_date=response.getString("finish_date");
            String start_date=response.getString("start_date");
            String phone_number=response.getString("phone_number");
            String name=response.getString("name");
            String id_email=response.getString("id_email");
            String access_key=response.getString("access_key");
            // setting
            User.getInstance().getData().setUser_lock_start(locker_start);
            User.getInstance().getData().setUser_lock_finish(locker_finish);
            User.getInstance().getData().setUser_locker_num(Integer.parseInt(locker_num));
            User.getInstance().getData().setUser_gender(gender);
            User.getInstance().getData().setUser_birtyday(birthday);
            User.getInstance().getData().setCertification(certification);
            User.getInstance().getData().setRemain_break_day(remain_break_day);
            User.getInstance().getData().setUser_finish_date(finish_date);
            User.getInstance().getData().setUser_start_date(start_date);
            User.getInstance().getData().setUser_phone_number(phone_number);
            User.getInstance().getData().setUser_name(name);
            User.getInstance().getData().setUser_email(id_email);
            User.getInstance().getData().setUser_access_key(access_key);
        } else if (result_code == 8888) {
            Log.d("DEBUGYU","User Load Error");
        }
    }
}
