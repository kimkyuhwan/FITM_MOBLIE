package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.DataModel.NoticeData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.NoticeViewAdapter;

/**
 * Created by Myown on 2017-08-08.
 */

public class NoticeActivity extends Activity {

    // 주안 - 크로스핏에 대한 공지사항을 전달하는 화면
    private ListView listView;
    private NoticeViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        listView=(ListView)findViewById(R.id.notice_list);
        adapter=new NoticeViewAdapter();

        try {
            ReadNoticeList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.setIdxSelected(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    void ReadNoticeList() throws JSONException {
        JSONObject send_data = new JSONObject();
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_GET_NOTICE, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        JSONArray response=null;
        int result_code = 0;
        try {
            result_data = new JSONObject(result);
            result_code = result_data.getInt("code");
            response = result_data.getJSONArray("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result_code==3888){

            Log.d("DEBUGYU",response.toString());

            for(int i=0;i<response.length();i++){
                JSONObject hereNotice=response.getJSONObject(i);
                JSONObject msg_time=hereNotice.getJSONObject("time_obj");
                String noti_title=hereNotice.getString("title");
                String noti_body=hereNotice.getString("body");
                long noti_idx=hereNotice.getLong("notification_idx");

                String Time=getTwoDemicalString(msg_time.getString("h"))+getTwoDemicalString(msg_time.getString("m"));
                String Date=msg_time.getString("yy")+"/"+getTwoDemicalString(msg_time.getString("mm"))+"/"+getTwoDemicalString(msg_time.getString("dd"));
                NoticeData newNotice=new NoticeData(noti_idx,noti_title,noti_body,Date,Time,false);
                adapter.addItem(newNotice);
            }
            adapter.notifyDataSetChanged();
        }
        else if(result_code==5800){
            Toast.makeText(getApplicationContext(),"잘못된 요청입니다",Toast.LENGTH_LONG).show();
        }


    }

    public String getTwoDemicalString(String data){
        if(data.length()==1){
            data='0'+data;
        }
        return data;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
