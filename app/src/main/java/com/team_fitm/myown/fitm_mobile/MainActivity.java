package com.team_fitm.myown.fitm_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.team_fitm.myown.fitm_mobile.DataModels.UserData;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // 메인 화면
    // 네비게이션 드로어(햄버거 메뉴) : 내 정보, 1:1 문의, 공지사항, About 주안, 페이스북, 네이버 카페, 인스타그램
    // 메인 메뉴 : 수강신청, 레코드, etc
    // 액션바 메뉴 : Settings

    private Menu menu;
    private UserData user_data;

    private TextView navi_header_name;
    private TextView navi_header_id_email;

    private Button btn_enroll;
    private Button btn_record;
    private Button btn_etc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user_data = new UserData();
        Intent intent = getIntent();

        user_data.setUser_access_key(intent.getExtras().getString("access_key"));
        user_data.setUser_name(intent.getExtras().getString("name"));
        user_data.setUser_email(intent.getExtras().getString("id_email"));
        user_data.setUser_gender(intent.getExtras().getString("gender"));
        user_data.setUser_phone_number(intent.getExtras().getString("phone_number"));

        // 메인 화면 버튼 초기화, 이벤트 리스너 등록
        btn_enroll = (Button)findViewById(R.id.btn_main_enroll);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnrollActivity.class);
                intent.putExtra("access_key", user_data.getUser_access_key());
                intent.putExtra("name", user_data.getUser_name());
                intent.putExtra("id_email", user_data.getUser_email());
                startActivity(intent);
            }
        });
        btn_record = (Button)findViewById(R.id.btn_main_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                intent.putExtra("access_key", user_data.getUser_access_key());
                intent.putExtra("name", user_data.getUser_name());
                intent.putExtra("id_email", user_data.getUser_email());
                startActivity(intent);
            }
        });
        btn_etc = (Button)findViewById(R.id.btn_main_etc);
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EtcActivity.class);
                intent.putExtra("access_key", user_data.getUser_access_key());
                intent.putExtra("name", user_data.getUser_name());
                intent.putExtra("id_email", user_data.getUser_email());
                startActivity(intent);
            }
        });

        // 플로팅 액션 버튼은
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null); // 네비게이션 뷰의 메뉴의 아이콘을 null 로 교체 (정확히 더 알아봐야함)
        menu = navigationView.getMenu();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        navi_header_name = (TextView)findViewById(R.id.navi_header_name);
        navi_header_id_email = (TextView)findViewById(R.id.navi_header_id_email);
        navi_header_name.setText(user_data.getUser_name());
        navi_header_id_email.setText(user_data.getUser_email());
        ImageView header_profile_pic = (ImageView)findViewById(R.id.navi_header_profile_imgv);
        header_profile_pic.setBackground(getDrawable(R.drawable.koko));
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navi_my_profile) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, MyProfileActivity.class);
            intent.putExtra("access_key", user_data.getUser_access_key());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.navi_notice) {
            Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.navi_inquiry) {
            Intent intent = new Intent(MainActivity.this, InquiryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.navi_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else if (id == R.id.navi_naver_cafe) {
            // 웹 뷰로 처리할 예정

        } else if (id == R.id.navi_facebook_page) {
            // 웹 뷰로 처리할? 예정

        } else if(id == R.id.navi_instagram){
            // 앱 연동 형태로 처리할 예정
        }

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
