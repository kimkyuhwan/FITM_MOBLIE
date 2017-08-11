package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team_fitm.myown.fitm_mobile.Common.Constants;
import com.team_fitm.myown.fitm_mobile.DataModels.UserData;
import com.team_fitm.myown.fitm_mobile.HttpConnection.CustomThread.ReqHTTPJSONThread;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Myown on 2017-08-09.
 */

public class RegisterActivity extends Activity {

    private UserData user_data;

    private EditText edt_yyyy;
    private EditText edt_mm;
    private EditText edt_dd;
    private EditText edt_phone_number;
    private Button btn_req_register;

    private Context my_context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        my_context = this;
        user_data = new UserData();

        Intent intent = getIntent();
        user_data.setUser_access_key(intent.getExtras().getString("access_key"));
        user_data.setUser_email(intent.getExtras().getString("id_email"));
        user_data.setUser_name(intent.getExtras().getString("name"));
        user_data.setUser_gender(intent.getExtras().getString("gender"));

        edt_yyyy = (EditText)findViewById(R.id.edttxt_register_yyyy);
        edt_mm = (EditText)findViewById(R.id.edttxt_register_mm);
        edt_dd = (EditText)findViewById(R.id.edttxt_register_mm);
        edt_phone_number = (EditText)findViewById(R.id.edttxt_register_phone_number);

        btn_req_register = (Button)findViewById(R.id.btn_register_req_register);
        btn_req_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String birthday = edt_yyyy.getText().toString() + edt_mm .getText().toString() + edt_dd.getText().toString();
                String phone_number = edt_phone_number.getText().toString();
                JSONObject send_data = new JSONObject();
                try {
                    send_data.put("access_key", user_data.getUser_access_key());
                    send_data.put("gender", user_data.getUser_gender());
                    send_data.put("birthday", birthday);
                    send_data.put("phone_number",phone_number);
                    send_data.put("name",user_data.getUser_name());
                }catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }
                Log.i("name data", user_data.getUser_name());
                ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REGISTER_MEMBER_URL,send_data);
                thread.start();
                try{
                    thread.join();
                }catch (InterruptedException interex){
                    interex.printStackTrace();
                }
                String result = thread.handler.getMsg();
                JSONObject result_data = null;
                boolean flag = false;

                try{
                    result_data = new JSONObject(result);
                    if(result_data.getInt("code") == 1110){
                        flag = true;
                    }
                }
                catch (JSONException jsonex){
                    jsonex.printStackTrace();
                }

                if(flag){
                    Toast.makeText(my_context, "JUAN CROSS-FIT 가입을 환영합니다..", Toast.LENGTH_LONG);

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                    // 메인 액티비티로 넘길 데이터들 (String)
                    intent.putExtra("access_key", user_data.getUser_access_key());
                    intent.putExtra("name", user_data.getUser_name());
                    intent.putExtra("id_email",user_data.getUser_email());
                    intent.putExtra("gender", user_data.getUser_email());
                    intent.putExtra("phone_number", phone_number);
                    intent.putExtra("birthday", birthday);

                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(my_context, "가입에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG);
                }

            }
        });

    }
}
