package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.AWSService;
import crossfit_juan.chk.com.crossfitjuan.tool.ChatDBHelper;
import crossfit_juan.chk.com.crossfitjuan.tool.CircleImageView;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.DB_FILE_NAME;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PICK_FROM_ALBUM_ACTION;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

public class UserInfoActivity extends AppCompatActivity {



    private final String TAG = "DEBUG";
    Uri ImageCaptureUrl = null;
    Bitmap bm, resized;

    @BindView(R.id.user_info_profile_image)
    CircleImageView userInfoProfileImage;
    @BindView(R.id.user_info_photoAlbum)
    CircleImageView userInfoPhotoAlbum;
    @BindView(R.id.user_info_name)
    TextView userInfoName;
    @BindView(R.id.user_info_email)
    TextView userInfoEmail;
    @BindView(R.id.user_info_phone)
    TextView userInfoPhone;
    @BindView(R.id.user_info_locker_num)
    TextView userInfoLockerNum;
    @BindView(R.id.user_info_period)
    TextView userInfoPeriod;
    @BindView(R.id.user_info_restBtn)
    ImageButton userInfoRestBtn;
    @BindView(R.id.user_info_sign_out)
    Button userInfoSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        userInfoEmail.setText(User.getInstance().getData().getUser_email());
        userInfoName.setText(User.getInstance().getData().getUser_name());
        userInfoPhone.setText(User.getInstance().getData().getUser_phone_number());
        //   UserInfoTextEmail.setText(User.getInstance().getData().getUser_email());
        //   UserInfoTextName.setText(User.getInstance().getData().getUser_name());
        //heello
    }

    /*  @OnClick({R.id.imageButton, R.id.button})
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
      }*/
    public void TakeImagetoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM_ACTION);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM_ACTION:
                ImageCaptureUrl = data.getData();
                String realpath = getImagePath(ImageCaptureUrl);
                AWSService.getInstance().upload(new File(realpath));
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

    @OnClick({R.id.user_info_photoAlbum, R.id.user_info_restBtn, R.id.user_info_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_info_photoAlbum:
                TakeImagetoAlbum();
                SetProfileImage();
                break;
            case R.id.user_info_restBtn:
                Toast.makeText(getApplicationContext(),"휴회버튼",Toast.LENGTH_LONG).show();
                break;
            case R.id.user_info_sign_out:
                DeleteUser();
//                Toast.makeText(getApplicationContext(),"회원탈퇴 버튼",Toast.LENGTH_LONG).show();
                break;
        }
    }
    void SetProfileImage(){
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try{
                    URL url = new URL(PROFILE_PATH+ User.getInstance().getData().getUser_email()+".png");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    resized = Bitmap.createScaledBitmap(bm, 128, 128, true);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            userInfoProfileImage.setImageBitmap(resized);
                        }
                    });
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        ImageSetThread.start();
    }

    void DeleteUser(){
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("id_email", User.getInstance().getData().getUser_email());
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_DELETE_USER, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        try {
            result_data = new JSONObject(result);
            result_code = result_data.getInt("code");
            JSONObject response = result_data.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result_code==9999){
            Toast.makeText(getApplicationContext(),"아이디가 삭제되었습니다",Toast.LENGTH_LONG).show();
            ChatDBHelper dbHelper=new ChatDBHelper(getApplicationContext(),DB_FILE_NAME,null,1);
            dbHelper.DeleteDataBase();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
        else if(result_code==8888){
            Toast.makeText(getApplicationContext(),"잘못된 요청입니다",Toast.LENGTH_LONG).show();
        }

    }


}
