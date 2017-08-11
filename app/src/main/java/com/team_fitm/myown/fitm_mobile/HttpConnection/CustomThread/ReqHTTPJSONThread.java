package com.team_fitm.myown.fitm_mobile.HttpConnection.CustomThread;

import android.os.Message;

import com.team_fitm.myown.fitm_mobile.HttpConnection.CustomHandler;
import com.team_fitm.myown.fitm_mobile.HttpConnection.SendJSONHttp;

import org.json.JSONObject;

/**
 * Created by Myown on 2017-08-09.
 */

public class ReqFirstLoginThread  extends Thread{

    private JSONObject json_data;
    private String send_url;
    private String conn_result;

    public CustomHandler handler;

    public ReqFirstLoginThread(String send_url, JSONObject json_data){
        this.json_data = json_data;
        this.send_url = send_url;
        this.conn_result = null;
        this.handler = new CustomHandler();
    }

    @Override
    public void run() {
        SendJSONHttp http = new SendJSONHttp();
        this.conn_result = http.sendJSONObj(this.send_url, this.json_data);
        Message msg = this.handler.obtainMessage();
        msg.obj = (Object)this.conn_result;
        this.handler.handleMessage(msg);
    }
}
