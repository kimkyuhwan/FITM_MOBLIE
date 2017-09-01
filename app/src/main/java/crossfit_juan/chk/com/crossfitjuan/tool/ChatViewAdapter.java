package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class ChatViewAdapter extends BaseAdapter {
    private ArrayList<ChatData> listViewItemList = new ArrayList<ChatData>() ;

    // ListViewAdapter의 생성자
    public ChatViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();


        ChatData chatData = listViewItemList.get(position);

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        convertView=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.e("Chat Adapter",chatData.toString());
            Log.e("Chat Adpater","my ID"+User.getInstance().getData().getUser_name());
            if(chatData.isDateChangeSession()){
                convertView = inflater.inflate(R.layout.listview_date_session, parent, false);
                TextView dateInfo=(TextView)convertView.findViewById(R.id.chat_date_session);
                dateInfo.setText(chatData.getDate());
            }
            else {
                TextView contentInfo;
                TextView TimeInfo;
                if(chatData.getSender().equals(User.getInstance().getData().getUser_name())) {
                    convertView = inflater.inflate(R.layout.listview_my_chat, parent, false);
                    LinearLayout linearLayout=(LinearLayout)convertView.findViewById(R.id.my_chat_layout);
                    linearLayout.setGravity(Gravity.RIGHT);
                    contentInfo = (TextView) convertView.findViewById(R.id.chat_my_msg_content) ;
                    TimeInfo = (TextView) convertView.findViewById(R.id.chat_my_msg_time) ;

                }
                else{
                    convertView = inflater.inflate(R.layout.listview_your_chat, parent, false);
                    contentInfo = (TextView) convertView.findViewById(R.id.chat_your_msg_content) ;
                    TimeInfo = (TextView) convertView.findViewById(R.id.chat_your_msg_time) ;
                }

                contentInfo.setText(chatData.getContent());
                TimeInfo.setText(chatData.getTime());
            }
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        // 아이템 내 각 위젯에 데이터 반영

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(ChatData chatData) {
        listViewItemList.add(chatData);
    }


}
