package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.DataModel.NoticeData;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by gyuhwan on 2017. 9. 24..
 */

public class NoticeViewAdapter extends BaseAdapter {
    private ArrayList<NoticeData> listViewItemList = new ArrayList<NoticeData>() ;

    // ListViewAdapter의 생성자
    public NoticeViewAdapter() {

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

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        convertView=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_notice, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.notice_title) ;
        TextView dateTextView = (TextView) convertView.findViewById(R.id.notice_date) ;
        TextView contentTexvView = (TextView) convertView.findViewById(R.id.notice_content);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        NoticeData pp = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(pp.getTitle());
        dateTextView.setText(pp.getDate());

        if(pp.isSelected()){
            contentTexvView.setText(pp.getContents());
            ViewGroup.LayoutParams params = contentTexvView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            contentTexvView.setLayoutParams(params);
            Log.d("DEBUGYU","300");
        }
        else{
            contentTexvView.setHeight(0);
            Log.d("DEBUGYU","0");
        }

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

    public NoticeData getNoticeItem(int idx){
        return listViewItemList.get(idx);
    }

    public void setIdxSelected(int idx){
        boolean flag=listViewItemList.get(idx).isSelected();
        listViewItemList.get(idx).setSelected(!flag);
    }

    public long getNotificationIdx(int idx) {
        return listViewItemList.get(idx).getNotification_idx();
    }

    public void setNotificationContent(int idx,String data){
        listViewItemList.get(idx).setContents(data);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(NoticeData pp) {
        listViewItemList.add(pp);
    }


}

