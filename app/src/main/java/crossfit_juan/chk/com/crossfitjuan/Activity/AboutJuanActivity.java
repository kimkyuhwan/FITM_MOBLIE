package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossfit_juan.chk.com.crossfitjuan.R;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CROSSFITJUAN_ADDRESS_GOOGLE;

public class AboutJuanActivity extends AppCompatActivity {

    @BindView(R.id.aboutJuan_scroll)
    ScrollView aboutJuanScroll;
    @BindView(R.id.aboutJuan_scroll_linear)
    LinearLayout aboutJuanScrollLinear;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutjuan);
        ButterKnife.bind(this);
        setScrollViewContent();
    }

    void setScrollViewContent() {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(CROSSFITJUAN_ADDRESS_GOOGLE);

        ImageView imageView = new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(R.drawable.about_juan);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃 크기에 이미지를 맞춘다

        aboutJuanScrollLinear.addView(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
