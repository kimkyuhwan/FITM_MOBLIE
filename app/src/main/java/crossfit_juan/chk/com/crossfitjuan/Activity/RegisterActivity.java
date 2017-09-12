package crossfit_juan.chk.com.crossfitjuan.Activity;

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

import org.json.JSONException;
import org.json.JSONObject;

import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by Myown on 2017-08-09.
 */

public class RegisterActivity extends Activity {

    // 회원 등록화면
    // user_data : 사용자의 정보를 저장하는 객체

    // 네이버 아이디로 로그인 Api를 이용해서
    // 사용자의 email과 이름, 성별을 받아오고
    // 나머지 정보(생년월일, 폰 번호)는 사용자에게 직접 입력받아 서버로 전송함

    private UserData user_data;

    private EditText edt_yyyy;
    private EditText edt_mm;
    private EditText edt_dd;
    private EditText edt_phone_number;
    private EditText edt_name;
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
     //   user_data.setUser_name(intent.getExtras().getString("name"));
        user_data.setUser_gender(intent.getExtras().getString("gender"));

        edt_yyyy = (EditText)findViewById(R.id.edttxt_register_yyyy);
        edt_mm = (EditText)findViewById(R.id.edttxt_register_mm);
        edt_dd = (EditText)findViewById(R.id.edttxt_register_mm);
        edt_name= (EditText)findViewById(R.id.edttxt_register_name);
        edt_phone_number = (EditText)findViewById(R.id.edttxt_register_phone_number);

        btn_req_register = (Button)findViewById(R.id.btn_register_req_register);
        btn_req_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String birthday = edt_yyyy.getText().toString() + edt_mm .getText().toString() + edt_dd.getText().toString();
                String phone_number = edt_phone_number.getText().toString();
                String name = edt_name.getText().toString();
                if(!birthday.equals("") && !phone_number.equals("") && !name.equals("")) {
                    JSONObject send_data = new JSONObject();
                    try {
                        user_data.setUser_name(name);
                        send_data.put("access_key", user_data.getUser_access_key());
                        send_data.put("gender", user_data.getUser_gender());
                        send_data.put("birthday", birthday);
                        send_data.put("phone_number", phone_number);
                        send_data.put("name", user_data.getUser_name());
                    } catch (JSONException jsonex) {
                        jsonex.printStackTrace();
                    }
                    Log.i("name data", user_data.getUser_name());
                    ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REGISTER_MEMBER_URL, send_data);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException interex) {
                        interex.printStackTrace();
                    }
                    String result = thread.handler.getMsg();
                    JSONObject result_data = null;
                    boolean flag = false;

                    try {
                        result_data = new JSONObject(result);
                        if (result_data.getInt("code") == 1110) {
                            flag = true;
                        }
                    } catch (JSONException jsonex) {
                        jsonex.printStackTrace();
                    }

                    if (flag) {
                        Toast.makeText(my_context, "JUAN CROSS-FIT 가입을 환영합니다..", Toast.LENGTH_LONG);

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                        // 메인 액티비티로 넘길 데이터들 (String)
                        user_data.setUser_phone_number(phone_number);
                        user_data.setUser_birtyday(birthday);
                        User.getInstance().setUser(user_data);
                        intent.putExtra("access_key", User.getInstance().getData().getUser_access_key());
                        intent.putExtra("name", User.getInstance().getData().getUser_name());
                        intent.putExtra("id_email", User.getInstance().getData().getUser_email());
                        intent.putExtra("gender", User.getInstance().getData().getUser_gender());
                        intent.putExtra("phone_number", User.getInstance().getData().getUser_phone_number());
                        intent.putExtra("birthday", User.getInstance().getData().getUser_birtyday());

                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(my_context, "가입에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG);
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"가입을 완료해주세요",Toast.LENGTH_LONG).show();
    }
}
