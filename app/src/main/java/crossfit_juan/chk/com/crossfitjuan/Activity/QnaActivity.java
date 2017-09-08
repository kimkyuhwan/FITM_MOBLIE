package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Time_Table;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.ChatViewAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.Master_Socket_Address;

public class QnaActivity extends AppCompatActivity {

    @BindView(R.id.chat_msg_list)
    ListView chatMsgList;

    ChatViewAdapter adapter;
    @BindView(R.id.chat_msg_contents)
    EditText chatMsgContents;
    @BindView(R.id.chat_msg_send_Btn)
    Button chatMsgSendBtn;
    Socket mSocket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        ButterKnife.bind(this);

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
        mSocket.on("get_result",listener_get_result);
        // http://yanmari.tistory.com/2 추가 참조할 예정

    }

    public void makeRoom() throws JSONException{
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("room_name", User.getInstance().getData().getUser_email());
            send_data.put("latest_idx_time", 0);
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



            Log.e("DEBUGYU", "success MakeRoom");
        } else if (result_code == 5800) { // 실패 시
            Log.e("DEBUGYU", "fail MakeRoom");
        }

    }


    // public ChatData(String sender, String date, String time, String content, boolean isDateChangeSession) {
    void updateChatLog() {
        adapter = null;
        adapter = new ChatViewAdapter();
        // 서버에서 채팅 가져오는 거 대신 dummy
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "", true));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "테스트입니다", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:02", "그렇습니까?", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:06", "Hello World!!!!", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:08", "Good Good!", false));
        adapter.addItem(new ChatData("klight1994", "오늘", "11:00", "", true));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "테스트입니다", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:02", "그렇습니까?", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));


        // 키보드 누르면 화면 올라가는거 item 개수가 10개 미만일때 절반보다 적으므로 올릴 필요 X 그 이상이면 올려줌.
        if (adapter.getCount() >= 10) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        Log.d("DEBUGYU", String.valueOf(adapter.getCount()));
        chatMsgList.setAdapter(adapter);
    }

    @OnClick(R.id.chat_msg_send_Btn)
    public void onViewClicked() {
        JSONObject obj = new JSONObject();
    //    String
        try {
            obj.put("access_key", User.getInstance().getData().getUser_access_key());
            obj.put("room_name",User.getInstance().getData().getUser_email());
            obj.put("sender",User.getInstance().getData().getUser_name());
            String Msg=chatMsgContents.getText().toString();
            obj.put("message",Msg);
            mSocket.emit("send_message", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void JoinRoom(){
        JSONObject obj = new JSONObject();
        //    String
        try {
            obj.put("access_key", User.getInstance().getData().getUser_access_key());
            obj.put("room_name",User.getInstance().getData().getUser_email());
            mSocket.emit("join_room", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener listen_send_message = new Emitter.Listener() {

        public void call(Object... args) {
            final JSONObject obj = (JSONObject)args[0];
            String name="";
            String content="";
            //서버에서 보낸 JSON객체를 사용할 수 있습니다.
            try {
                name=obj.getString("sender");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                content=obj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String finalName = name;
            final String finalContent = content;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //이곳에 ui 관련 작업을 할 수 있습니다.
                    ChatData newChat=new ChatData();
                    newChat.setSender(finalName);
                    newChat.setContent(finalContent);
                    newChat.setDateChangeSession(false);
                    newChat.setDate("20170905");
                    newChat.setTime("08:20");
                    adapter.addItem(newChat);
                    chatMsgList.setAdapter(adapter);
                }
            });
        }
    };

    private Emitter.Listener listener_get_result = new Emitter.Listener() {

        public void call(Object... args) {
            final JSONObject obj = (JSONObject)args[0];

            //서버에서 보낸 JSON객체를 사용할 수 있습니다.
            String result_msg="hi";
            try {
                result_msg = obj.getString("message");
                //                JSONObject response = result_data.getJSONObj/ect("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("DEBUGYU",result_msg);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //이곳에 ui 관련 작업을 할 수 있습니다.


                }
            });
        }
    };

    // 액티비티 종료시 Socket 통신 종료


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off();
        mSocket.close();
    }
}
