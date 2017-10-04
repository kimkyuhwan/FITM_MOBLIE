package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.Firebase.MyFirebaseInstanceIdService;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.CircleImageView;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Intent it;
    @BindView(R.id.menu_btn)
    ImageButton menuBtn;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear_record)
    LinearLayout linearRecord;
    @BindView(R.id.linear_reservation)
    LinearLayout linearReservation;
    @BindView(R.id.linear_notice)
    LinearLayout linearNotice;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    @BindView(R.id.btn_notice)
    ImageButton noticeBtn;
    @BindView(R.id.btn_ranking)
    ImageButton rankBtn;
    @BindView(R.id.btn_reserve)
    ImageButton reserveBtn;
    Bitmap bm, resized;
    CircleImageView tProfileImg;
    // macbook git push_commit test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyFirebaseInstanceIdService tokenRegisterService=new MyFirebaseInstanceIdService();
        tokenRegisterService.RegistrationTokenToServer();
        View navHeaderView=navView.getHeaderView(0);
        navView.setNavigationItemSelectedListener(this);

        TextView tname=(TextView)navHeaderView.findViewById(R.id.nav_name);
        TextView temail=(TextView)navHeaderView.findViewById(R.id.nav_email);
        tProfileImg=(CircleImageView)navHeaderView.findViewById(R.id.profile_image);
        tname.setText(User.getInstance().getData().getUser_name());
        temail.setText(User.getInstance().getData().getUser_email());
    }

    void SetProfileImage(){
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try{
                    URL url = new URL(PROFILE_PATH+User.getInstance().getData().getUser_email()+".png");
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
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        ImageSetThread.start();

    }

    @OnClick({R.id.menu_btn, R.id.linear_record, R.id.linear_reservation, R.id.linear_notice,R.id.btn_reserve,R.id.btn_ranking,R.id.btn_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_btn:
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.linear_record:
            case R.id.btn_ranking:
                Toast.makeText(getApplicationContext(), "튜토리얼 추가 예정입나다", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linear_reservation:
            case R.id.btn_reserve:
           //
                //     Toast.makeText(getApplicationContext(), "RESERVATION 추가 예정입나다", Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(it);
                break;
            case R.id.linear_notice:
            case R.id.btn_notice:
//                Toast.makeText(getApplicationContext(), "NOTICE 추가 예정입나다", Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity.this, MarketActivity.class);
                startActivity(it);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.navigation_item_my_Info) {
            Intent it=new Intent(MainActivity.this,UserInfoActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_Notice) {
            Intent it=new Intent(MainActivity.this,NoticeActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_QnA) {
            Intent it=new Intent(MainActivity.this,QnaActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_About) {
            Intent it=new Intent(MainActivity.this,AboutJuanActivity.class);
            startActivity(it);
            drawer.closeDrawer(GravityCompat.END);
        } else if (id == R.id.navigation_item_Cafe) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p5", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_Facebook) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p6", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_Instagram) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p7", Toast.LENGTH_LONG).show();
        }
        return true;
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
    public void onResume(){
        super.onResume();
        SetProfileImage();
    }
}