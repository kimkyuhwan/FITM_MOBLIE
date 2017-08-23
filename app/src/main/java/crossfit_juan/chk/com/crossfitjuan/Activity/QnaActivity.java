package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.ChatViewAdapter;
import crossfit_juan.chk.com.crossfitjuan.tool.ParticipantViewAdapter;
import io.socket.client.IO;
import io.socket.client.Socket;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.Master_Socket_Address;

public class QnaActivity extends AppCompatActivity {

    @BindView(R.id.chat_msg_list)
    ListView chatMsgList;

    ChatViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);
        ButterKnife.bind(this);

        updateChatLog();

        /*
        // 소켓 연결
        Socket mSocket;
        try {
            mSocket = IO.socket(Master_Socket_Address);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // http://yanmari.tistory.com/2 추가 참조할 예정
        */
    }
    // public ChatData(String sender, String date, String time, String content, boolean isDateChangeSession) {
    void updateChatLog(){
        adapter=null;
        adapter = new ChatViewAdapter();
        adapter.addItem(new ChatData("klight1994","2017. 08. 23","11:00","",true));
        adapter.addItem(new ChatData("klight1994","2017. 08. 23","11:00","테스트입니다",false));
        adapter.addItem(new ChatData("other","2017. 08. 23","11:02","그렇습니까?",false));
        adapter.addItem(new ChatData("klight1994","2017. 08. 23","11:04","Hello World!!!",false));
        adapter.addItem(new ChatData("klight1994","2017. 08. 23","11:06","Hello World!!!!",false));
        adapter.addItem(new ChatData("other","2017. 08. 23","11:08","Good Good!",false));
        Log.d("DEBUGYU",String.valueOf(adapter.getCount()));
        chatMsgList.setAdapter(adapter);
    }

}
