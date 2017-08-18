package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.URISyntaxException;

import crossfit_juan.chk.com.crossfitjuan.R;
import io.socket.client.IO;
import io.socket.client.Socket;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.Master_Socket_Address;

public class QnaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qna);

        // 소켓 연결
        Socket mSocket;
        try {
            mSocket= IO.socket(Master_Socket_Address);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // http://yanmari.tistory.com/2 추가 참조할 예정
    }
}
