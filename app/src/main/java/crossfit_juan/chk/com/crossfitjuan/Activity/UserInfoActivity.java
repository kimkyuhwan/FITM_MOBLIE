package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.AWSService;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PICK_FROM_ALBUM_ACTION;

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

    private final String TAG="DEBUG";
    Uri ImageCaptureUrl=null;

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
//                Toast.makeText(getApplicationContext(),"이미지 변경 버튼",Toast.LENGTH_LONG).show();
                TakeImagetoAlbum();
                break;
            case R.id.button:
                Toast.makeText(getApplicationContext(),"기간 변경 버튼",Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void TakeImagetoAlbum(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM_ACTION);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case PICK_FROM_ALBUM_ACTION :
                ImageCaptureUrl=data.getData();
                String realpath=getImagePath(ImageCaptureUrl);
                 AWSService.getInstance().uploadFileThread(new File(realpath));
                break;
        }
    }
    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

}
