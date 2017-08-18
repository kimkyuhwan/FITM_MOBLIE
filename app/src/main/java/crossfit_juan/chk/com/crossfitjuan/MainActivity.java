package crossfit_juan.chk.com.crossfitjuan;

import android.content.Intent;
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
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navView.setNavigationItemSelectedListener(this);
    }

    @OnClick({R.id.menu_btn, R.id.linear_record, R.id.linear_reservation, R.id.linear_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_btn:
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                break;
            case R.id.linear_record:
                Toast.makeText(getApplicationContext(), "RECORD 추가 예정입나다", Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(it);
                break;
            case R.id.linear_reservation:
           //
                //     Toast.makeText(getApplicationContext(), "RESERVATION 추가 예정입나다", Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(it);
                break;
            case R.id.linear_notice:
                Toast.makeText(getApplicationContext(), "NOTICE 추가 예정입나다", Toast.LENGTH_SHORT).show();
                it = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(it);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_item_my_Info) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p1", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_Notice) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p2", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_QnA) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p3", Toast.LENGTH_LONG).show();
        } else if (id == R.id.navigation_item_About) {
            Toast.makeText(getApplicationContext(), "준비중입니다 p4", Toast.LENGTH_LONG).show();
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
}