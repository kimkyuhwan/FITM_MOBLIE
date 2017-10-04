package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.UserData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by Myown on 2017-08-09.
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.register_name)
    EditText registerName;
    @BindView(R.id.register_birthday_year)
    EditText registerBirthdayYear;
    @BindView(R.id.register_birthday_month)
    EditText registerBirthdayMonth;
    @BindView(R.id.register_birthday_day)
    EditText registerBirthdayDay;
    @BindView(R.id.register_phone_00)
    EditText registerPhone00;
    @BindView(R.id.register_phone_01)
    EditText registerPhone01;
    @BindView(R.id.register_phone_02)
    EditText registerPhone02;
    @BindView(R.id.req_registerBtn)
    Button reqRegisterBtn;

    // 회원 등록화면
    // user_data : 사용자의 정보를 저장하는 객체

    // 네이버 아이디로 로그인 Api를 이용해서
    // 사용자의 email과 이름, 성별을 받아오고
    // 나머지 정보(생년월일, 폰 번호)는 사용자에게 직접 입력받아 서버로 전송함

    private UserData user_data;

    private Context my_context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        my_context = this;
        user_data = new UserData();

        Intent intent = getIntent();
        user_data.setUser_access_key(intent.getExtras().getString("access_key"));
        user_data.setUser_email(intent.getExtras().getString("id_email"));
        //   user_data.setUser_name(intent.getExtras().getString("name"));
        user_data.setUser_gender(intent.getExtras().getString("gender"));
        makeEditTextListenersrcTodst(registerBirthdayYear,4,registerBirthdayMonth);
        makeEditTextListenersrcTodst(registerBirthdayMonth,2,registerBirthdayDay);
        makeEditTextListenersrcTodst(registerBirthdayDay,2,registerPhone00);
        makeEditTextListenersrcTodst(registerPhone00,3,registerPhone01);
        makeEditTextListenersrcTodst(registerPhone01,4,registerPhone02);

    }

    void makeEditTextListenersrcTodst(final EditText src, final int length, final EditText dst){
        src.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==length){dst.requestFocus();}
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @OnClick(R.id.req_registerBtn)
    public void onViewClicked() {
        register();
    }

    public void register() {
        String birthday = registerBirthdayYear.getText().toString() + registerBirthdayMonth.getText().toString() + registerBirthdayDay.getText().toString();
        boolean isCorrectPhone = false;

        if (registerPhone00.getText().toString().length() >= 3 && registerPhone01.getText().toString().length() >= 3 && registerPhone02.getText().toString().length() >= 4) {
            isCorrectPhone = true;
            String phone_number = registerPhone00.getText().toString() + "-" + registerPhone01.getText().toString() + "-" + registerPhone02.getText().toString();
            String name = registerName.getText().toString();
            if (!birthday.equals("") && isCorrectPhone && !name.equals("")) {
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
    }


}
