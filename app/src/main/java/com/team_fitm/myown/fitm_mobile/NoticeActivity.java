package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Myown on 2017-08-08.
 */

public class NoticeActivity extends Activity {

    // 주안 - 크로스핏에 대한 공지사항을 전달하는 화면

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
