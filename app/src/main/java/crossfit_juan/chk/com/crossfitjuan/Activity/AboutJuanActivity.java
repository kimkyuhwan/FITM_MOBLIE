package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import crossfit_juan.chk.com.crossfitjuan.R;

public class AboutJuanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutjuan);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
