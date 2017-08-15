package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Myown on 2017-08-11.
 */

public class RecordActivity extends Activity {

    // 앱 자체에서 사용할 수 있는 레코드 기능
    // 디테일한 디자인 및 기능은 아직 정해지지 않음

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }
}
