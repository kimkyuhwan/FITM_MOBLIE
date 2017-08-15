package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Myown on 2017-08-09.
 */

public class InquiryActivity extends Activity {

    // 1:1 문의 기능(Socket.io를 바탕으로 실시간 채팅) 화면

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
