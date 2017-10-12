package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.ChatDBHelper;
import crossfit_juan.chk.com.crossfitjuan.tool.ChatViewAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.DB_FILE_NAME;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.Master_Socket_Address;

public class QnaActivity extends AppCompatActivity {


    ChatViewAdapter adapter;

    Socket mSocket;
    ChatDBHelper dbHelper = null;
    @BindView(R.id.chat_msg_list)
    ListView chatMsgList;
    @BindView(R.id.chat_msg_contents)
    EditText chatMsgContents;
    @BindView(R.id.chat_msg_send_Btn)
    ImageButton chatMsgSendBtn;

    ClipboardManager clipboardManager;
    ClipData clipData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        ButterKnife.bind(this);
        dbHelper = new ChatDBHelper(getApplicationContext(), DB_FILE_NAME, null, 1);

        updateChatLog();

        //소켓 연결

        try {
            mSocket = IO.socket(Master_Socket_Address);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            makeRoom();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JoinRoom();
        mSocket.on("send_message", listen_send_message);
        mSocket.on("get_result", listener_get_result);
        // http://yanmari.tistory.com/2 추가 참조할 예정
        clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        chatMsgList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                clipData=ClipData.newPlainText("label",adapter.getMessageContent(i));
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"클립보드에 복사되었습니다",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    void ReadPreviousChatData() {
        ArrayList<ChatData> list = dbHelper.getResult();
        for (int i = 0; i < list.size(); i++) {
            ChatData a = list.get(i);
            adapter.addItem(a);
        }
        adapter.setDateSession();
        chatMsgList.setAdapter(adapter);
        chatMsgList.setSelection(adapter.getCount() - 1);
    }


    public void makeRoom() throws JSONException {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("room_name", User.getInstance().getData().getUser_email());
            send_data.put("latest_idx_time", dbHelper.getLast_idx());
            send_data.put("name", User.getInstance().getData().getUser_name());
            Log.d("DEBUGYU", "last idx : " + String.valueOf(dbHelper.getLast_idx()));
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.MAKE_ROOM_URL, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }

        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        result_data = new JSONObject(result);
        result_code = result_data.getInt("code");
        JSONObject response = result_data.getJSONObject("response");

        if (result_code == 3334) { // 성공 시
            String msgLog = response.getString("message");
            JSONArray msgArr = response.getJSONArray("message_list");
            Log.d("DEBUGYU", response.toString());

            for (int i = 0; i < msgArr.length(); i++) {
                JSONObject hereMsg = msgArr.getJSONObject(i);
                JSONObject msg_time = hereMsg.getJSONObject("message_time");
                ChatData newChat = new ChatData();
                newChat.setSender(hereMsg.getString("sender"));
                newChat.setContent(hereMsg.getString("message"));
                String Time = hereMsg.getString("time");
                newChat.setDateChangeSession(false);
                newChat.setIdx_time(hereMsg.getLong("idx_time"));
                newChat.setDate(msg_time.getString("year") + "년 " + msg_time.getString("month") + "월 " + msg_time.getString("day") + "일");
                int hh=Integer.parseInt(msg_time.getString("hour"));
                String mm=getTwoDemicalString(msg_time.getString("minute"));
                String time="";
                if(hh>=12){
                    hh%=12;
                    if(hh==0) hh=12;
                    time+="오후 "+String.valueOf(hh)+":"+mm;
                }
                else{
                    if(hh==0) hh=12;
                    time+="오전 "+String.valueOf(hh)+":"+mm;
                }
                newChat.setTime(time);
                dbHelper.InsertData(newChat);
            }
            ReadPreviousChatData();
            Log.e("DEBUGYU", "success MakeRoom Log = " + msgLog);
        } else if (result_code == 5800) { // 실패 시
            Log.e("DEBUGYU", "fail MakeRoom");
        }

    }


    // public ChatData(String sender, String date, String time, String content, boolean isDateChangeSession) {
    void updateChatLog() {
        adapter = null;
        adapter = new ChatViewAdapter();

        // 키보드 누르면 화면 올라가는거 item 개수가 10개 미만일때 절반보다 적으므로 올릴 필요 X 그 이상이면 올려줌.
        if (adapter.getCount() >= 10) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        Log.d("DEBUGYU", String.valueOf(adapter.getCount()));
        chatMsgList.setAdapter(adapter);
    }

    @OnClick(R.id.chat_msg_send_Btn)
    public void onViewClicked() {
        if (!chatMsgContents.getText().toString().equals("")) {
            JSONObject obj = new JSONObject();
            //    String

            try {
                obj.put("access_key", User.getInstance().getData().getUser_access_key());
                obj.put("room_name", User.getInstance().getData().getUser_email());
                obj.put("sender", User.getInstance().getData().getUser_name());
                String Msg = chatMsgContents.getText().toString();
                obj.put("message", Msg);
                mSocket.emit("send_message", obj);
                chatMsgContents.setText("");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void JoinRoom() {
        JSONObject obj = new JSONObject();
        //    String
        try {
            obj.put("access_key", User.getInstance().getData().getUser_access_key());
            obj.put("room_name", User.getInstance().getData().getUser_email());
            mSocket.emit("join_room", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener listen_send_message = new Emitter.Listener() {

        public void call(Object... args) {
            final JSONObject obj = (JSONObject) args[0];
            JSONObject msg_time = null;
            try {
                msg_time = obj.getJSONObject("message_time");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("DEBUGYU", obj.toString());
            final JSONObject finalMsg_time = msg_time;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ChatData newChat = new ChatData();
                    try {
                        newChat.setSender(obj.getString("sender"));
                        newChat.setContent(obj.getString("message"));
                        newChat.setDateChangeSession(false);
                        newChat.setIdx_time(obj.getLong("idx_time"));
                        newChat.setDate(finalMsg_time.getString("year") + "년 " + finalMsg_time.getString("month") + "월 " + finalMsg_time.getString("day") + "일");
                        int hh=Integer.parseInt(finalMsg_time.getString("hour"));
                        String mm=getTwoDemicalString(finalMsg_time.getString("minute"));
                        String time="";
                        if(hh>=12){
                            hh%=12;
                            if(hh==0) hh=12;
                            time+="오후 "+String.valueOf(hh)+":"+mm;
                        }
                        else{
                            if(hh==0) hh=12;
                            time+="오전 "+String.valueOf(hh)+":"+mm;
                        }
                        newChat.setTime(time);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("DEBUGYU", "Send_Message getLast : " + dbHelper.getLast_day());
                    Log.d("DEBUGYU", "Send Message newChat Date : " + newChat.getDate());
                    if (!newChat.getDate().equals(dbHelper.getLast_day())) {
                        ChatData Todays = new ChatData();
                        Todays.setDate("오늘");
                        Todays.setDateChangeSession(true);
                        adapter.addItem(Todays);
                    }
                    dbHelper.InsertData(newChat);

                    adapter.addItem(newChat);
                    chatMsgList.setAdapter(adapter);
                    chatMsgList.setSelection(adapter.getCount() - 1);
                    //이곳에 ui 관련 작업을 할 수 있습니다.

                }
            });
        }
    };

    private Emitter.Listener listener_get_result = new Emitter.Listener() {

        public void call(Object... args) {
            final JSONObject obj = (JSONObject) args[0];
            //서버에서 보낸 JSON객체를 사용할 수 있습니다.
            String result_msg = "hi";
            try {
                result_msg = obj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("DEBUGYU", result_msg);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //이곳에 ui 관련 작업을 할 수 있습니다.

                }
            });
        }
    };

    // 액티비티 종료시 Socket 통신 종료

    public String getTwoDemicalString(String data) {
        if (data.length() == 1) {
            data = '0' + data;
        }
        return data;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off();
        mSocket.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        User.setHereActivityContext(this);
        User.setHereActivity("Qna");
    }
}
