package crossfit_juan.chk.com.crossfitjuan.Firebase;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

import crossfit_juan.chk.com.crossfitjuan.Activity.LoginActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.NoticeActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.QnaActivity;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.R;

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
        Intent intent=null;
        if(type.equals("0")) {
            intent = new Intent(this, NoticeActivity.class);

        }
        else if(type.equals("1") ){
            intent = new Intent(this, QnaActivity.class);

        }
        else{
            Log.d("DEBUGYU","잘못된 타입의 메시지");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.juan_push_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }



}
