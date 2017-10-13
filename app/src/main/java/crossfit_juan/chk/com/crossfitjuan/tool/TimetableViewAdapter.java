package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Classinfo;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_NONE;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_NOT_SELECTED;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_SELECTED;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class TimetableViewAdapter extends BaseAdapter {
    private ArrayList<Classinfo> listViewItemList = new ArrayList<Classinfo>() ;

    Context context;
    String Date;
    // ListViewAdapter의 생성자
    public TimetableViewAdapter(Context context,String Date) {
        this.context=context;
        this.Date=Date;
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_timetable, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.timetable_layout);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timetable_time) ;
        TextView participantsTextView = (TextView) convertView.findViewById(R.id.timetable_participants) ;
        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.timetable_btn);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Classinfo pp = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        timeTextView.setText(pp.getStart_time()+"~"+pp.getFinish_time());
        participantsTextView.setText(pp.getParticipants_size()+" / "+pp.getMax_participant());

        if(pp.getMax_participant() == pp.getParticipants().size()){
            layout.setBackgroundColor(Color.argb(0xff,0x44,0x88,0xff));
            if(pp.getSelected_state()!=RESERVATION_SELECT_STATE_SELECTED)
            pp.setSelected_state(RESERVATION_SELECT_STATE_NOT_SELECTED);
        }

        switch (pp.getSelected_state()){
            case RESERVATION_SELECT_STATE_NONE:
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setBackgroundResource(R.drawable.checked);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DEBUGYU","RESERVATION_SELECT_STATE_NONE");
                        RegisterReservation(pos);
                    }
                });
                break;
            case RESERVATION_SELECT_STATE_SELECTED:
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setBackgroundResource(R.drawable.delete);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DEBUGYU","RESERVATION_SELECT_STATE_SELECTED");
                        CancelReservation(pos);
                    }
                });
                break;

            case RESERVATION_SELECT_STATE_NOT_SELECTED:
                imageButton.setVisibility(View.INVISIBLE);
                break;
        }
        return convertView;
    }

    public void RegisterReservation(int idx) {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("date", Date);
            send_data.put("class_num", idx + 1);
            send_data.put("name", User.getInstance().getData().getUser_name());
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        Log.d("DEBUGYU","SEND DATA : "+send_data.toString());
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_REGISTER_RESERVATION, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        try {
            result_data = new JSONObject(result);
            result_code = result_data.getInt("code");
            JSONObject response = result_data.getJSONObject("response");
            Log.d("DEBUGYU", String.valueOf(result_code));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result_code == 1350) {
            Toast.makeText(context, "등록되었습니다", Toast.LENGTH_LONG).show();
            for(int i=0;i<listViewItemList.size();i++){
                if(i!=idx)
                    listViewItemList.get(i).setSelected_state(RESERVATION_SELECT_STATE_NOT_SELECTED);
                else{
                    listViewItemList.get(i).setSelected_state(RESERVATION_SELECT_STATE_SELECTED);
                    listViewItemList.get(i).addParticipants();
                }
            }
            notifyDataSetChanged();
        } else if (result_code == 1270) {
            Toast.makeText(context, "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
        else{
            Log.d("DEBUGYU","CODE "+result_code);
        }
    }



    public void CancelReservation(int idx) {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("date", Date);
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_CANCEL_RESERVATION, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        try {
            result_data = new JSONObject(result);
            result_code = result_data.getInt("code");
            JSONObject response = result_data.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result_code == 1270) {
            Toast.makeText(context, "취소되었습니다", Toast.LENGTH_LONG).show();
            //scheduleRegisterBtn.setText("등록");
            //selectScheduleBtn.setClickable(!isReserved);
            for(int i=0;i<listViewItemList.size();i++){
                if(listViewItemList.get(i).getMax_participant()==listViewItemList.get(i).getParticipants().size())
                    listViewItemList.get(i).setSelected_state(RESERVATION_SELECT_STATE_NOT_SELECTED);
                else{
                    listViewItemList.get(i).setSelected_state(RESERVATION_SELECT_STATE_NONE);
                }
                if(i==idx){
                    listViewItemList.get(i).subtractParticipants();
                }
            }
            notifyDataSetChanged();
        } else if (result_code == 2170) {
            Toast.makeText(context, "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
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
    public void addItem(Classinfo pp) {
        listViewItemList.add(pp);
    }


}
