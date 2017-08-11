package com.team_fitm.myown.fitm_mobile;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.team_fitm.myown.fitm_mobile.Common.Constants;
import com.team_fitm.myown.fitm_mobile.DateUtils.DateModule;
import com.team_fitm.myown.fitm_mobile.HttpConnection.CustomThread.ReqHTTPJSONThread;
import com.team_fitm.myown.fitm_mobile.ListViewPackage.Enroll.EnrollListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Myown on 2017-08-11.
 */

public class EnrollActivity extends Activity {

    private final String TAG = "ENROLL";

    private DateModule date;

    private TextView txtv_yyyy;
    private TextView txtv_mm;
    private TextView txtv_dd;
    private TextView txtv_today_wod;

    private ListView list_view;
    private EnrollListViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        txtv_yyyy = (TextView)findViewById(R.id.txtv_enroll_yyyy);
        txtv_mm = (TextView)findViewById(R.id.txtv_enroll_mm);
        txtv_dd = (TextView)findViewById(R.id.txtv_enroll_dd);
        txtv_today_wod = (TextView)findViewById(R.id.txtv_enroll_today_wod);

        list_view = (ListView)findViewById(R.id.listview_enroll_classes);
        adapter = new EnrollListViewAdapter();
        list_view.setAdapter(adapter);

        date = new DateModule();
        String today = date.getKST_YYYY() + date.getKST_MM() + date.getKST_DD();

        txtv_yyyy.setText(date.getKST_YYYY());
        txtv_mm.setText(date.getKST_MM());
        txtv_dd.setText(date.getKST_DD());

        JSONObject send_data = new JSONObject();
        try{
            send_data.put("date",today);
        }catch (JSONException jsonex){
            jsonex.printStackTrace();
        }

        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_TODAY_ENROLL, send_data);
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException interex){
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        Log.d(TAG, result);
        JSONObject result_data = null;
        String today_wod = null;
        try{
            result_data = new JSONObject(result);
            JSONObject response = result_data.getJSONObject("response");
            today_wod = response.getString("today_wod");
            txtv_today_wod.setText(today_wod);
            JSONArray classes_arr = response.getJSONArray("classes");
            for(int i = 0; i < classes_arr.length(); i++){
                JSONObject tmp = classes_arr.getJSONObject(i);
                JSONArray tmp_arr = tmp.getJSONArray("participant");
                adapter.addItem(tmp.getString("class_num"), tmp.getString("start_time"), tmp.getString("finish_time"), tmp.getString("max_participant"),Integer.toString(tmp_arr.length()));
            }
        }catch (JSONException jsonex){
            jsonex.printStackTrace();
        }
    }
}
