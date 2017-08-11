package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.team_fitm.myown.fitm_mobile.DataModels.UserData;

/**
 * Created by Myown on 2017-08-08.
 */

public class MyProfileActivity extends Activity {

    private UserData user_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        user_data = new UserData();
        Intent intent = getIntent();
        user_data.setUser_access_key(intent.getExtras().getString("access_key"));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
