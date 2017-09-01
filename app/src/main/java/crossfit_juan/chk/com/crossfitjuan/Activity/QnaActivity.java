package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
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
//        mSocket.on("받아올 이벤트 명", listen_start_person);

        // http://yanmari.tistory.com/2 추가 참조할 예정

    }

    // public ChatData(String sender, String date, String time, String content, boolean isDateChangeSession) {
    void updateChatLog() {
        adapter = null;
        adapter = new ChatViewAdapter();
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "", true));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "테스트입니다", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:02", "그렇습니까?", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:06", "Hello World!!!!", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:08", "Good Good!", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 24", "11:00", "", true));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:00", "테스트입니다", false));
        adapter.addItem(new ChatData("other", "2017. 08. 23", "11:02", "그렇습니까?", false));
        adapter.addItem(new ChatData("klight1994", "2017. 08. 23", "11:04", "Hello World!!!", false));
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
            obj.put("image", 3);
            mSocket.emit("connect_start", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener listen_start_person = new Emitter.Listener() {

        public void call(Object... args) {
            final JSONObject obj = (JSONObject)args[0];

            //서버에서 보낸 JSON객체를 사용할 수 있습니다.

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //이곳에 ui 관련 작업을 할 수 있습니다.


                }
            });
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
 //       mSocket.off();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mSocket.off();
    }
}
