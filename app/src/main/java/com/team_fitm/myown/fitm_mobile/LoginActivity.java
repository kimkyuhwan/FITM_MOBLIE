package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.team_fitm.myown.fitm_mobile.Common.Constants;
import com.team_fitm.myown.fitm_mobile.DataModels.UserData;
import com.team_fitm.myown.fitm_mobile.HttpConnection.CustomThread.ReqFirstLoginThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Myown on 2017-08-09.
 */

public class LoginActivity extends Activity {

    private final static String TAG = "LoginActivity";

    // 로그인 인스턴스
    private static OAuthLogin my_oauth_login_instance;
    private static OAuthLogin my_login_module;
    private static Context my_context;

    // 로그인 필요 정보
    private static String OAUTH_CLIENT_ID = "e8zwpIOA1W0xjpHcaqDG";
    private static String OAUTH_CLIENT_SECRET = "H8SGUHsWaH";
    private static String OAUTH_CLIENT_NAME = "FITM_MOBILE";

    // 로그인 이후 토큰 정보
    private String user_name;

    // 정보 요청 스레드, 핸들러
    private HttpThread http_thread;
    private HttpHandler my_handler;

    // 사용자 정보 관련
    private UserData user_data;

    private OAuthLoginButton req_login_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_data = new UserData();
        my_context = this;
        my_handler = new HttpHandler();
        my_login_module = OAuthLogin.getInstance();
        my_login_module.init(
                LoginActivity.this,
                OAUTH_CLIENT_ID,
                OAUTH_CLIENT_SECRET,
                OAUTH_CLIENT_NAME
        );

        req_login_btn = (OAuthLoginButton)findViewById(R.id.req_naver_login_btn);
        req_login_btn.setOAuthLoginHandler(my_login_handler);

        Log.e("test",my_login_module.getState(my_context).toString());
        if(my_login_module.getState(my_context).toString().equals("NEED_LOGIN") || my_login_module.getState(my_context).toString().equals("NEED_REFRESH_TOKEN")){
            my_login_module.startOauthLoginActivity(LoginActivity.this, my_login_handler);
        }
        req_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserProfile(my_login_module.getAccessToken(my_context));
                Log.i("USER", user_data.getUser_name() + " , " + user_data.getUser_email());
                JSONObject send_data = new JSONObject();
                try{
                    send_data.put("id_email", user_data.getUser_email());
                }catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }
                ReqFirstLoginThread thread = new ReqFirstLoginThread(Constants.REQ_LOGIN_URL, send_data);
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
                try{
                    result_data = new JSONObject(result);
                    result_code = result_data.getInt("code");
                    JSONObject response = result_data.getJSONObject("response");
                    user_access_key = response.getString("access_key");
                }catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }
                Log.e("FITM_LOGIN", user_access_key + Integer.toString(result_code));
                if(result_code == 1100){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(result_code == 1101){
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void initData(){
        my_oauth_login_instance = OAuthLogin.getInstance();
        my_oauth_login_instance.init(my_context, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);
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

    private String unicodeToString(String unicode) throws UnsupportedEncodingException {
        return new String (unicode.getBytes("8859_1"),"KSC5601");
    }

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
                //final String username = unicodeToString(nick);
                UserData tmp_user_data = new UserData();
                tmp_user_data.setUser_name(nick);
                tmp_user_data.setUser_email(email);
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
    class HttpHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UserData tmp = (UserData)msg.obj;
            user_data.setUser_name(tmp.getUser_name());
            user_data.setUser_email(tmp.getUser_email());
        }
    }


    // 네이버 로그인 관련 inner class
    static private OAuthLoginHandler my_login_handler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            Log.e("LoginHandler", "run");
            if(b){
                String access_token = my_login_module.getAccessToken(my_context);
                String refresh_token = my_login_module.getRefreshToken(my_context);
                Long expiresAt = my_login_module.getExpiresAt(my_context);
                String tokenType = my_login_module.getTokenType(my_context);

                Log.i("ACCESS_TOKEN", access_token);
                Log.i("REFRESH_TOKEN", refresh_token);
                Log.i("TOKEN_TYPE", tokenType);
                Log.i("EXPRIRESAT", Long.toString(expiresAt));

            }else{
                String error_code = my_oauth_login_instance.getLastErrorCode(my_context).getCode();
                String error_desc = my_oauth_login_instance.getLastErrorDesc(my_context);
                Log.e("ERR", error_code + " , " + error_desc);
                Toast.makeText(my_context, "errorCode : "+ error_code + " , errorDesc : " + error_desc, Toast.LENGTH_LONG).show();
            }
        };
    };

    private class DeleteTokenTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            boolean isSuccessDeleteToken = my_oauth_login_instance.logoutAndDeleteToken(my_context);

            if (!isSuccessDeleteToken) {
                // 서버에서 token 삭제에 실패했어도 클라이언트에 있는 token 은 삭제되어 로그아웃된 상태이다
                // 실패했어도 클라이언트 상에 token 정보가 없기 때문에 추가적으로 해줄 수 있는 것은 없음
                Log.d(TAG, "errorCode:" + my_oauth_login_instance.getLastErrorCode(my_context));
                Log.d(TAG, "errorDesc:" + my_oauth_login_instance.getLastErrorDesc(my_context));
            }

            return null;
        }
    }


    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/getUserProfile.xml";
            String at = my_oauth_login_instance.getAccessToken(my_context);
            return my_oauth_login_instance.requestApi(my_context, at, url);
        }
        protected void onPostExecute(String content) {

        }
    }

    private class RefreshTokenTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return my_oauth_login_instance.refreshAccessToken(my_context);
        }
        protected void onPostExecute(String res) {

        }
    }
}
