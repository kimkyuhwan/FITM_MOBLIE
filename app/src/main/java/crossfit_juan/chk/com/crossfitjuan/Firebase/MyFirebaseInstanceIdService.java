package crossfit_juan.chk.com.crossfitjuan.Firebase;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import crossfit_juan.chk.com.crossfitjuan.Activity.LoginActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.MainActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.RegisterActivity;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;

import static crossfit_juan.chk.com.crossfitjuan.Common.User.getInstance;

/**
 * Created by gyuhwan on 2017. 10. 3..
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    String TAG="DEBUGYU_FIREBASE";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
 //       sendRegistrationToServer(refreshedToken);
    }
    public void RegistrationTokenToServer(){
        if(User.getInstance()==null) return;
        String token = FirebaseInstanceId.getInstance().getToken();
        String access_key = User.getInstance().getData().getUser_access_key();
        if(token==null || access_key==null){
            Log.d("DEBUGYU","RegistrationTokenToServer Null Error");
            return;
        }
        try {
            RegistrationTokenToServerWithEmail(access_key,token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void RegistrationTokenToServerWithEmail(String access_key,String token) throws JSONException {
        JSONObject send_data = new JSONObject();
        try{
            send_data.put("access_key", access_key);
            send_data.put("device_token",token);
        }catch (JSONException jsonex){
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_REGISTER_FIREBASE_TOKEN, send_data);
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException interex){
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = new JSONObject(result);
        int result_code = result_data.getInt("code");
        JSONObject response=result_data.getJSONObject(  "response");
        if(result_code==3011){
            String message=response.getString("message");
            Log.d(TAG,"Register Token : "+token+" access_key : "+access_key+" msg : "+message);
        }
        else if(result_code==5800){
            Log.d(TAG,"Register Token : "+token+" access_key : "+access_key  +" Failed");
        }
    }
}
