package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Classinfo;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Time_Table;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.ParticipantsViewAdapter;
import crossfit_juan.chk.com.crossfitjuan.tool.TimetableViewAdapter;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_NONE;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_NOT_SELECTED;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.RESERVATION_SELECT_STATE_SELECTED;

public class ReservationActivity extends AppCompatActivity {


    @BindView(R.id.textView_date)
    TextView textViewDate;
    @BindView(R.id.textView_title)
    TextView textViewTitle;
    @BindView(R.id.textView_contents)
    TextView textViewContents;


    boolean isReserved = false;
    boolean isPossibleToReserve = false;
    int selectedSchedule = -1;

    Time_Table timetable;
    Classinfo cinfo;
    TimetableViewAdapter adapter;

    String toDate;
    @BindView(R.id.timetable_List)
    ListView timetableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);

    }
    public void ViewParticipantsList(int idx){
        Log.d("DEBUGYU","VIEW PARTICIPATNS");
        final Dialog dR = new Dialog(this);
        dR.setContentView(R.layout.view_participants_dialog);
        TextView dRtime = (TextView) dR.findViewById(R.id.participant_dialog_time);
        ListView dRlist = (ListView) dR.findViewById(R.id.participant_dialog_list);
        Button dRCancelBtn = (Button) dR.findViewById(R.id.participant_dialog_cancel_Btn);
        dRtime.setText(adapter.getClassInfo(idx).getStart_time()+"~"+adapter.getClassInfo(idx).getFinish_time());
        ParticipantsViewAdapter participantsViewAdapter=new ParticipantsViewAdapter();
        for(int i=0;i<adapter.getClassInfo(idx).getParticipants().size();i++){
            participantsViewAdapter.addItem(adapter.getClassInfo(idx).getParticipants().get(i));
            Log.d("DEBUGYU","VIEW PARTICIPATNS"+i+" : "+adapter.getClassInfo(idx).getParticipants().get(i).toString());
        }
        dRlist.setAdapter(participantsViewAdapter);
        dRCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dR.dismiss();
            }
        });
        dR.show();
    }

    public String getToDate() {
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        String to = transFormat.format(from);
        return to;
    }

    public void getTodayWod() throws JSONException {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("date", toDate);
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_DATE_SCHEDULE_CHECK, send_data);
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

        if (result_code == 1150) { // 성공 시
            timetable = new Time_Table();
            Log.d("DEBUGYU", response.toString());

            timetable.setDate(response.getString("date"));
            timetable.setToday_wod_name(response.getString("today_wod_name"));
            timetable.setToday_wod_content(response.getString("today_wod_content"));
            timetable.setNum_of_classes(response.getInt("num_of_classes"));
            JSONArray A = response.getJSONArray("classes");
            for (int i = 0; i < A.length(); i++) {
                timetable.addClasses(A.getJSONObject(i));
            }
            for(int i=0;i<timetable.getClasses().size();i++){
                int state=RESERVATION_SELECT_STATE_NONE;
                if(timetable.getClasses().get(i).getClass_num()==cinfo.getClass_num()){
                    state=RESERVATION_SELECT_STATE_SELECTED;
                }
                else if(cinfo.getClass_num()!=-1){
                    state=RESERVATION_SELECT_STATE_NOT_SELECTED;
                }
                timetable.getClasses().get(i).setSelected_state(state);
                adapter.addItem(timetable.getClasses().get(i));
            }
            adapter.notifyDataSetChanged();
            String date = timetable.getDate();
            String dd = dataFormatGen(date);
            Log.d("DDDDDD", dd);
            textViewDate.setText(dd);
            textViewTitle.setText(timetable.getToday_wod_name());
            textViewContents.setText(timetable.getToday_wod_content());
            isPossibleToReserve = true;

            Log.e("DEBUGYU", "success" + timetable.toString());
        } else if (result_code == 2170) { // 실패 시
            Log.e("DEBUGYU", "fail");
            String dd = dataFormatGen(toDate);
            textViewDate.setText(dd);
            textViewTitle.setText("오늘은 쉽니다");
            textViewContents.setText("");
            isPossibleToReserve = false;
        }
        if (selectedSchedule != -1) {
//            getParticipantsInfo(selectedSchedule);
        }
    }

    public String dataFormatGen(String date) {
        String dd = date.subSequence(0, 4) + ". " + date.subSequence(4, 6) + ". " + date.subSequence(6, 8);
        return dd;
    }

    public void getMyTodayWod() throws JSONException {
        toDate = getToDate();
        adapter = new TimetableViewAdapter(this,toDate);
        timetableList.setAdapter(adapter);
        timetableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewParticipantsList(i);
            }
        });
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("date", toDate);
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_USER_DATE_SCHEDULE_CHECK, send_data);
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
        cinfo = new Classinfo();

        if (result_code == 1170) { // 성공 시
            timetable = new Time_Table();

            timetable.setDate(response.getString("date"));
            timetable.setToday_wod_name(response.getString("today_wod_name"));
            timetable.setToday_wod_content(response.getString("today_wod_content"));
            cinfo.setClass_num(response.getInt("class_num"));
            cinfo.setStart_time(response.getString("start_time"));
            cinfo.setFinish_time(response.getString("finish_time"));
            isReserved = true;
            selectedSchedule = cinfo.getClass_num() - 1;

            Log.e("DEBUGYU", "success" + timetable.toString());
        } else if (result_code == 2170) { // 실패 시
            Log.e("DEBUGYU", "fail");

        }
        getTodayWod();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        super.onResume();
        User.setHereActivityContext(this);
        User.setHereActivity("Reservation");
        try {
            getMyTodayWod();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
