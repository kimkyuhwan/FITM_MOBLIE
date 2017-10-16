package crossfit_juan.chk.com.crossfitjuan.Firebase;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;

import java.util.List;

import crossfit_juan.chk.com.crossfitjuan.Activity.LoginActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.MainActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.NoticeActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.QnaActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.ReservationActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.UserInfoActivity;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.R;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PUSH_NOTICE_ACTIVITY;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PUSH_QNA_ACTIVITY;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PUSH_REST_ACCEPT;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PUSH_REST_REJECT;

/**
 * Created by gyuhwan on 2017. 10. 3..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // message 받았을때
        Log.d("DEBUGYU",remoteMessage.getData().toString());
        receiveNotification(remoteMessage);
    }
    void receiveNotification(RemoteMessage remoteMessage){
        String type=remoteMessage.getData().get("notification_type");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        if(type.equals(PUSH_NOTICE_ACTIVITY)) {
            getNoticeDialog(body);
        }
        else if(type.equals(PUSH_QNA_ACTIVITY) ){
            getQnADialog(body);
        }
        else if(type.equals(PUSH_REST_ACCEPT) || type.equals(PUSH_REST_REJECT)){
            getUserInfoDialog(title,body);
        }
        else{
            Log.d("DEBUGYU","잘못된 타입의 메시지");
        }
        return;
    }
    void getNoticeDialog(final String NoticeTitle){
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (User.getHereActivity().equals("Notice")) {
                    try {
                        Toast.makeText(User.getHereActivityContext(),"공지사항이 올라왔습니다",Toast.LENGTH_SHORT).show();
                        ((NoticeActivity) (User.getHereActivityContext())).ReadNoticeList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    final Dialog dR = new Dialog(User.getHereActivityContext());

                    dR.setContentView(R.layout.move_to_notice_dialog);
                    TextView dRNoticeTitle = (TextView) dR.findViewById(R.id.move_to_notice_dialog_title);
                    Button dROkBtn = (Button) dR.findViewById(R.id.move_to_notice_dialog_check_Btn);
                    Button dRCancelBtn = (Button) dR.findViewById(R.id.move_to_notice_dialog_close_Btn);
                    dRNoticeTitle.setText(NoticeTitle);
                    dRCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dR.dismiss();
                        }
                    });
                    dROkBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            dR.dismiss();
                        }
                    });
                    dR.show();
                }
            }
        });

    }

    void getQnADialog(final String contents) {
        if(User.getHereActivity().equals("Qna")){
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dR = new Dialog(User.getHereActivityContext());
                dR.setContentView(R.layout.move_to_qna_dialog);
                TextView dRQnaContents = (TextView) dR.findViewById(R.id.move_to_qna_dialog_title);
                Button dROkBtn = (Button) dR.findViewById(R.id.move_to_qna_dialog_send_Btn);
                Button dRCancelBtn = (Button) dR.findViewById(R.id.move_to_qna_dialog_close_Btn);
                dRQnaContents.setText(contents);
                dRCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dR.dismiss();
                    }
                });
                dROkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), QnaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        dR.dismiss();
                    }
                });
                dR.show();
            }
        });
    }

    void getUserInfoDialog(final String title, final String body) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (User.getHereActivity().equals("UserInfo")) {
                        Toast.makeText(User.getHereActivityContext(),"휴회 신청 결과가 나왔습니다",Toast.LENGTH_SHORT).show();
                        ((UserInfoActivity) (User.getHereActivityContext())).LoadUserInfo();
                } else {

                    final Dialog dR = new Dialog(User.getHereActivityContext());

                    dR.setContentView(R.layout.move_to_notice_dialog);
                    TextView dRtitle = (TextView)dR.findViewById(R.id.move_to_notice_dialog_tap);
                    TextView dRBody = (TextView) dR.findViewById(R.id.move_to_notice_dialog_title);
                    Button dROkBtn = (Button) dR.findViewById(R.id.move_to_notice_dialog_check_Btn);
                    Button dRCancelBtn = (Button) dR.findViewById(R.id.move_to_notice_dialog_close_Btn);
                    dRtitle.setText(title);
                    dRBody.setText(body);
                    dRCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dR.dismiss();
                        }
                    });
                    dROkBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            dR.dismiss();
                        }
                    });
                    dR.show();
                }
            }
        });
    }

}
