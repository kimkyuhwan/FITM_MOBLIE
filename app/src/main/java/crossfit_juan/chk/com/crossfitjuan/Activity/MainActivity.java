package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ssomai.android.scalablelayout.ScalableLayout;

import org.json.JSONException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.Firebase.MyFirebaseInstanceIdService;
import crossfit_juan.chk.com.crossfitjuan.Firebase.MyFirebaseMessagingService;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.AWSService;
import crossfit_juan.chk.com.crossfitjuan.tool.CircleImageView;
import crossfit_juan.chk.com.crossfitjuan.tool.NaverCafe;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.APP_ID;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CAFE_URL;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_ADMIN;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_MARKET;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_RESERVATION;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_REST;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_SHOW_RANK;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.COMMAND_CERTIFICATION_MARKET;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.COMMAND_CERTIFICATION_RESERVATION;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.COMMAND_CERTIFICATION_SHOW_RANK;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_FACEBOOK_URL;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_INSTAGRAM_URL;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_NAVER_CAFE_CALL_URL;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_NAVER_CAFE_URL;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PICK_FROM_ALBUM_ACTION;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.REQUEST_PERMISSION_ACCESS_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Intent it;
    @BindView(R.id.menu_btn)
    ImageButton menuBtn;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;



    Bitmap bm, resized;
    CircleImageView tProfileImg;
    @BindView(R.id.main_background)
    ScalableLayout mainBackground;
    @BindView(R.id.main_scalable1)
    ScalableLayout mainScalable1;
    @BindView(R.id.main_scalable2)
    ScalableLayout mainScalable2;
    @BindView(R.id.main_scalable3)
    ScalableLayout mainScalable3;
    @BindView(R.id.btn_tutorial)
    ImageButton btnTutorial;
    @BindView(R.id.btn_reserve)
    ImageButton btnReserve;
    @BindView(R.id.btn_market)
    ImageButton btnMarket;

    // macbook git push_commit test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    void SetProfileImage() {

        // TODO 프로필 이미지 S3 => 로컬 서버
        String gender=User.getInstance().getData().getUser_gender();
        switch (gender){
            case "M":
                tProfileImg.setImageResource(R.drawable.default_profile_man);
                break;
            default:
                tProfileImg.setImageResource(R.drawable.default_profile_women);
                break;
        }
        /*
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try {
                    URL url = new URL(PROFILE_PATH + User.getInstance().getData().getUser_email() + ".png");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    resized = Bitmap.createScaledBitmap(bm, 128, 128, true);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            tProfileImg.setImageBitmap(resized);
                        }
                    });
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch (Exception e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tProfileImg.setImageResource(R.drawable.default_profile);
                        }
                    });
                    e.printStackTrace();
                }

            }
        });

        ImageSetThread.start();
        */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.navigation_item_my_Info) {
            Intent it = new Intent(MainActivity.this, UserInfoActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_QnA) {
            // TODO 1:1문의 기능
            Toast.makeText(getApplicationContext(),"추후 지원 예정입니다",Toast.LENGTH_LONG).show();
          /*  Intent it = new Intent(MainActivity.this, QnaActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);*/
        } else if (id == R.id.navigation_market) {
            // TODO 공동구매 기능
            Toast.makeText(getApplicationContext(),"추후 지원 예정입니다",Toast.LENGTH_LONG).show();
            /*
            if(checkthePermission(COMMAND_CERTIFICATION_MARKET)) {
                it = new Intent(MainActivity.this, MarketActivity.class);
                startActivity(it);
            }
            else{
                Toast.makeText(getApplicationContext(),"공동구매는 준회원부터 가능합니다",Toast.LENGTH_LONG).show();
            }
            drawer.closeDrawer(GravityCompat.END);*/
        }
        else if (id == R.id.navigation_item_About) {
            Intent it = new Intent(MainActivity.this, AboutJuanActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_Cafe) {
            new NaverCafe(MainActivity.this, APP_ID).cafe(CAFE_URL);
        } else if (id == R.id.navigation_item_Facebook) {
            startActivity(getOpenFacebookIntent(this,CROSSFITJUAN_FACEBOOK_URL));
        /*
          */  //Toast.makeText(getApplicationContext(), "준비중입니다 p6", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_Instagram) {
            Intent i=new Intent(Intent.ACTION_VIEW);
            Uri u = Uri.parse(CROSSFITJUAN_INSTAGRAM_URL);
            i.setData(u);
            startActivity(i);
        }
        return true;
    }

    public static Intent getOpenFacebookIntent(Context context,String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW,uri);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        View navHeaderView = navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);

        TextView tname = (TextView) navHeaderView.findViewById(R.id.nav_name);
        TextView temail = (TextView) navHeaderView.findViewById(R.id.nav_email);
        tProfileImg = (CircleImageView) navHeaderView.findViewById(R.id.profile_image);
        tname.setText(User.getInstance().getData().getUser_name());
        temail.setText(User.getInstance().getData().getUser_email());

        SetProfileImage();
        User.setHereActivityContext(this);
        User.setHereActivity("Main");
        try {
            User.LoadStomUserInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyFirebaseInstanceIdService tokenRegisterService = new MyFirebaseInstanceIdService();
        tokenRegisterService.RegistrationTokenToServer();

    }
    @OnClick({R.id.menu_btn, R.id.btn_tutorial, R.id.main_scalable1, R.id.btn_reserve, R.id.main_scalable2, R.id.btn_market, R.id.main_scalable3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_btn:
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.btn_tutorial:
            case R.id.main_scalable1:
                it = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(it);
                break;
            case R.id.btn_reserve:
            case R.id.main_scalable2:
                if(checkthePermission(COMMAND_CERTIFICATION_RESERVATION)) {
                    it = new Intent(MainActivity.this, ReservationActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(),"예약은 정회원부터 가능합니다",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_market:
            case R.id.main_scalable3:
                // TODO 순위보기 기능
                Toast.makeText(getApplicationContext(),"추후 지원 예정입니다",Toast.LENGTH_LONG).show();
                /*
                if(checkthePermission(COMMAND_CERTIFICATION_SHOW_RANK)) {
                    it = new Intent(MainActivity.this, RankingActivity.class);
                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(),"순위보기는 준회원부터 가능합니다",Toast.LENGTH_LONG).show();
                }*/
                break;
        }
    }

    boolean checkthePermission(int command){
        switch (command){
            case COMMAND_CERTIFICATION_RESERVATION:
                if(User.getInstance().getData().getCertification()>=CERTIFICATION_RESERVATION){
                    return true;
                }
                break;
            case COMMAND_CERTIFICATION_MARKET:
                if(User.getInstance().getData().getCertification()>=CERTIFICATION_MARKET){
                    return true;
                }
                break;
            case COMMAND_CERTIFICATION_SHOW_RANK:
                if(User.getInstance().getData().getCertification()>=CERTIFICATION_SHOW_RANK){
                    return true;
                }
        }
        return false;
    }




}