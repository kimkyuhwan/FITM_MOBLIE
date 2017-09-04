package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.R;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.UserInfo_Text_Name)
    TextView UserInfoTextName;
    @BindView(R.id.UserInfo_Text_Locker_Number)
    TextView UserInfoTextLockerNumber;
    @BindView(R.id.UserInfo_Text_Duration)
    TextView UserInfoTextDuration;
    @BindView(R.id.UserInfo_Text_Email)
    TextView UserInfoTextEmail;
    @BindView(R.id.UserInfo_Text_Register_Duration)
    TextView UserInfoTextRegisterDuration;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        UserInfoTextEmail.setText(User.getInstance().getData().getUser_email());
        UserInfoTextName.setText(User.getInstance().getData().getUser_name());
        //heello
    }

    @OnClick({R.id.imageButton, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageButton:
                Toast.makeText(getApplicationContext(),"이미지 변경 버튼",Toast.LENGTH_LONG).show();
                break;
            case R.id.button:
                Toast.makeText(getApplicationContext(),"기간 변경 버튼",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
