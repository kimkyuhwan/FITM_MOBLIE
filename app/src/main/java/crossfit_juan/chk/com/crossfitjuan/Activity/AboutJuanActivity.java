package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossfit_juan.chk.com.crossfitjuan.R;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_ADDRESS_GOOGLE;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.REQUEST_PERMISSION_ACCESS_STORAGE;

public class AboutJuanActivity extends AppCompatActivity {


    @BindView(R.id.crossfit_imgview)
    ImageView crossfitImgview;
    @BindView(R.id.aboutJuan_scroll_linear)
    LinearLayout aboutJuanScrollLinear;
    @BindView(R.id.aboutJuan_scroll)
    ScrollView aboutJuanScroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutjuan);
        ButterKnife.bind(this);
        setScrollViewContent();
    }

    void setScrollViewContent() {

        //webview.getSettings().setJavaScriptEnabled(true);
        //webview.loadUrl(CROSSFITJUAN_ADDRESS_GOOGLE);


        crossfitImgview.setAdjustViewBounds(true);
        crossfitImgview.setImageResource(R.drawable.about_juan);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        crossfitImgview.setLayoutParams(lp);
        crossfitImgview.setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃 크기에 이미지를 맞춘다

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.crossfit_googlemap);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng CrossfitJuan = new LatLng(37.463798, 126.680629);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CrossfitJuan, 17));

                googleMap.addMarker(new MarkerOptions()
                        .title("크로스핏 주안")
                        .position(CrossfitJuan));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse(CROSSFITJUAN_ADDRESS_GOOGLE));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                });

            }
        });
        Button callBtn = new Button(this);
        callBtn.setText("연락하기");
        callBtn.setTextSize(16);
        callBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        callBtn.setBackgroundColor(Color.argb(0xff,0,0,0));
        callBtn.setTextColor(Color.argb(0xff,0xff,0xff,0xff));
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calling();
            }
        });
        aboutJuanScrollLinear.addView(callBtn);
    }

    public void calling() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0324299997"));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
