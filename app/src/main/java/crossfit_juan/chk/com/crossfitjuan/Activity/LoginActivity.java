package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

public class LoginActivity extends AppCompatActivity {
    private OAuthLogin mOAuthLoginModule;
    private OAuthLogin mInstance;
    private String OAUTH_CLIENT_ID = "0TrX_XsSZ5xhftBKYCzN";
    private String OAUTH_CLIENT_SECRET= "GlE9Lv_n6W";
    private String OAUTH_CLIENT_NAME="CrossfitJuan";


    private HttpThread http_thread;
    private HttpHandler my_handler;

    private OAuthLoginButton mOAuthLoginButton;
    private UserData user_data;
    Context context;

    // 통신용 스레드 클래스
    class HttpThread extends Thread{
        private String token;
        private String result;
        public HttpThread(String token){
            this.token = token;
        }
        @Override
        public void run() {
            super.run();
            String header = "Bearer " + token;
            try{
                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization",header);
                int response_code = con.getResponseCode();
                BufferedReader br;
                if(response_code == 200){
                    // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }else{
                    // 에러 발생
                    Log.e("REQ_PROFILE", "error occur");
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String input_line;
                StringBuffer response = new StringBuffer();
                while((input_line = br.readLine()) != null){
                    response.append(input_line);
                }
                br.close();
                result = response.toString();
                Log.i("RESULT", result);
                JSONObject obj = new JSONObject(result);
                JSONObject response_obj = obj.getJSONObject("response");
                String nick = response_obj.getString("nickname");
                String email = response_obj.getString("email");
                String gender = response_obj.getString("gender");
                //final String username = unicodeToString(nick);
                UserData tmp_user_data = new UserData();
                tmp_user_data.setUser_name(nick);
                tmp_user_data.setUser_email(email);
                tmp_user_data.setUser_gender(gender);
                Message msg = Message.obtain();
                msg.obj = tmp_user_data;
                my_handler.handleMessage(msg);
            }catch (MalformedURLException urlex){
                urlex.printStackTrace();
            }catch (IOException ioex){
                ioex.printStackTrace();
            }catch (JSONException jsonex){
                jsonex.printStackTrace();
            }
        }
    }

    // 통신용 핸들러
    class HttpHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UserData tmp = (UserData)msg.obj;
            user_data.setUser_name(tmp.getUser_name());
            user_data.setUser_email(tmp.getUser_email());
            user_data.setUser_gender(tmp.getUser_gender());
        }
    }

    private OAuthLoginHandler my_login_handler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            Log.e("LoginHandler", "run");
            if(b){
                String access_token = mOAuthLoginModule.getAccessToken(context);
                String refresh_token = mOAuthLoginModule.getRefreshToken(context);
                Long expiresAt = mOAuthLoginModule.getExpiresAt(context);
                String tokenType = mOAuthLoginModule.getTokenType(context);

                Log.i("ACCESS_TOKEN", access_token);
                Log.i("REFRESH_TOKEN", refresh_token);
                Log.i("TOKEN_TYPE", tokenType);
                Log.i("EXPRIRESAT", Long.toString(expiresAt));

            }else{
                String error_code = mInstance.getLastErrorCode(context).getCode();
                String error_desc = mInstance.getLastErrorDesc(context);
                Log.e("ERR", error_code + " , " + error_desc);
                Toast.makeText(context, "errorCode : "+ error_code + " , errorDesc : " + error_desc, Toast.LENGTH_LONG).show();
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=this;
        user_data = new UserData();
        my_handler = new HttpHandler();
        Initalize_OAuthLogin();
        if(mOAuthLoginModule.getState(context).toString().equals("NEED_LOGIN") || mOAuthLoginModule.getState(context).toString().equals("NEED_REFRESH_TOKEN")){
            mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, my_login_handler);
        }
        mOAuthLoginButton=(OAuthLoginButton)findViewById(R.id.NaverLoginBtn);
        mOAuthLoginButton.setOAuthLoginHandler(my_login_handler);
        mOAuthLoginButton.setBgResourceId(R.drawable.naver_login_btn_img);
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserProfile(mOAuthLoginModule.getAccessToken(context));
                Log.i("USER", user_data.getUser_name() + " , " + user_data.getUser_email());
                JSONObject send_data = new JSONObject();
                try{
                    send_data.put("id_email", user_data.getUser_email());
                }catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }
                ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_LOGIN_URL, send_data);
                thread.start();
                try{
                    thread.join();
                }catch (InterruptedException interex){
                    interex.printStackTrace();
                }
                String result = thread.handler.getMsg();
                JSONObject result_data = null;
                String user_access_key = null;
                int result_code = 0;
                int isRegistered=0;
                String var_email="";
                String var_name="";
                String var_phone="";
                String var_gender="";
                String var_birthday="";
                try{
                    result_data = new JSONObject(result);
                    result_code = result_data.getInt("code");
                    JSONObject response = result_data.getJSONObject("response");
                    user_access_key = response.getString("access_key");
                    user_data.setUser_access_key(user_access_key);
                    Log.d("DEBUGYU",response.toString());
                    isRegistered=response.getInt("check_register");
                    var_name=response.getString("name");
                    var_email=response.getString("id_email");
                    var_gender=response.getString("gender");
                    var_phone=response.getString("phone_number");
                    var_birthday=response.getString("birthday");

                }catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }
                Log.e("FITM_LOGIN", user_data.getDataForLog()+"#"+String.valueOf(result_code));
                if(isRegistered==1){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                   /* intent.putExtra("access_key", user_data.getUser_access_key());
                    intent.putExtra("id_email", user_data.getUser_email());
                    intent.putExtra("name", user_data.getUser_name());
                    intent.putExtra("gender", user_data.getUser_gender());
                    intent.putExtra("phone_number", "");*/
                    user_data.setUser_email(var_email);
                    user_data.setUser_name(var_name);
                    user_data.setUser_gender(var_gender);
                    user_data.setUser_phone_number(var_phone);
                    user_data.setUser_birtyday(var_birthday);
                    User.getInstance().setUser(user_data);
                    Log.e("DEBUGYU",user_data.getDataForLog());
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtra("access_key", user_data.getUser_access_key());
                    intent.putExtra("id_email", user_data.getUser_email());
                 //   intent.putExtra("name", user_data.getUser_name());
                    intent.putExtra("gender", user_data.getUser_gender());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void getUserProfile(String token){
        http_thread = new HttpThread(token);
        http_thread.start();
        try{
            http_thread.join();
        }catch (InterruptedException intrex){
            intrex.printStackTrace();
        }
    }


    void Initalize_OAuthLogin(){
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );
    }

}
