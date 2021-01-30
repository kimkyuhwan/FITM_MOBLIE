package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    boolean isPossibleToReserve = false;
    int selectedSchedule = -1;

    public int count=0;

    Time_Table timetable;
    Classinfo cinfo;
    TimetableViewAdapter adapter;

    String toDate;
    @BindView(R.id.timetable_List)
    ListView timetableList;
    @BindView(R.id.commentBtn)
    ImageButton commentBtn;
    @BindView(R.id.commentText)
    public TextView commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);

    }

    public void ViewParticipantsCancelList(int idx) {
        Log.d("DEBUGYU", "VIEW PARTICIPATNS");
        final Dialog dR = new Dialog(this);
        dR.setContentView(R.layout.view_participants_cancel_dialog);
        TextView dRtime = (TextView) dR.findViewById(R.id.participant_dialog_time);
        ListView dRlist = (ListView) dR.findViewById(R.id.participant_dialog_list);
        Button dRCancelBtn = (Button) dR.findViewById(R.id.participant_dialog_cancel_Btn);
        dRtime.setText(adapter.getClassInfo(idx).getStart_time() + "~" + adapter.getClassInfo(idx).getFinish_time());
        ParticipantsViewAdapter participantsViewAdapter = new ParticipantsViewAdapter();
        for (int i = 0; i < adapter.getClassInfo(idx).getParticipants().size(); i++) {
            participantsViewAdapter.addItem(adapter.getClassInfo(idx).getParticipants().get(i));
            Log.d("DEBUGYU", "VIEW PARTICIPATNS" + i + " : " + adapter.getClassInfo(idx).getParticipants().get(i).toString());
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

    public void ViewParticipantsList(final int idx) {
        Log.d("DEBUGYU", "VIEW PARTICIPATNS");
        final Dialog dR = new Dialog(this);
        dR.setContentView(R.layout.view_participants_dialog);
        TextView dRtime = (TextView) dR.findViewById(R.id.participant_dialog_time);
        ListView dRlist = (ListView) dR.findViewById(R.id.participant_dialog_list);
        Button dRCancelBtn = (Button) dR.findViewById(R.id.participant_dialog_cancel_Btn);
        Button dRReserveBtn = (Button) dR.findViewById(R.id.participant_dialog_reserve_Btn);
        dRtime.setText(adapter.getClassInfo(idx).getStart_time() + "~" + adapter.getClassInfo(idx).getFinish_time());
        ParticipantsViewAdapter participantsViewAdapter = new ParticipantsViewAdapter();
        for (int i = 0; i < adapter.getClassInfo(idx).getParticipants().size(); i++) {
            participantsViewAdapter.addItem(adapter.getClassInfo(idx).getParticipants().get(i));
            Log.d("DEBUGYU", "VIEW PARTICIPATNS" + i + " : " + adapter.getClassInfo(idx).getParticipants().get(i).toString());
        }
        dRlist.setAdapter(participantsViewAdapter);
        dRCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dR.dismiss();
            }
        });
        dRReserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog commentDR = new Dialog(ReservationActivity.this);
                commentDR.setContentView(R.layout.register_reservation_dialog);
                TextView cdRtime = (TextView)commentDR.findViewById(R.id.dialog_time);
                final EditText cdText = (EditText)commentDR.findViewById(R.id.dialog_comment);
                final ImageButton cdCancel=(ImageButton)commentDR.findViewById(R.id.dialog_comment_clear_Btn);

                cdRtime.setText(adapter.getClassInfo(idx).getStart_time() + "~" + adapter.getClassInfo(idx).getFinish_time());
                cdText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(i>0)
                            cdCancel.setVisibility(View.VISIBLE);
                        else
                            cdCancel.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                cdCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cdText.setText("");
                    }
                });
                Button cdRCancelBtn = (Button) commentDR.findViewById(R.id.dialog_comment_cancel_Btn);
                Button cdRReserveBtn = (Button) commentDR.findViewById(R.id.dialog_comment_register_Btn);
                cdRCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        commentDR.dismiss();
                    }
                });
                cdRReserveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RegisterReservation(idx,cdText.getText().toString());
                        commentDR.dismiss();
                        dR.dismiss();

                    }
                });

                commentDR.show();
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
            for (int i = 0; i < timetable.getClasses().size(); i++) {
                int state = RESERVATION_SELECT_STATE_NONE;
                if (timetable.getClasses().get(i).getClass_num() == cinfo.getClass_num()) {
                    state = RESERVATION_SELECT_STATE_SELECTED;
                } else if (cinfo.getClass_num() != -1) {
                    state = RESERVATION_SELECT_STATE_NOT_SELECTED;
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
            commentText.setVisibility(View.VISIBLE);
            commentBtn.setVisibility(View.VISIBLE);
            getComments();
            commentText.setText("댓글("+count+")");
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
        adapter = new TimetableViewAdapter(this, toDate);
        timetableList.setAdapter(adapter);
        adapter.setReserved(false);
        timetableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.isReserved() || adapter.isFullClass(i))
                    ViewParticipantsCancelList(i);
                else
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
            adapter.setReserved(true);
            selectedSchedule = cinfo.getClass_num() - 1;
            Log.e("DEBUGYU", "success" + timetable.toString());
        } else if (result_code == 2170) { // 실패 시
            Log.e("DEBUGYU", "fail");
        }
        getTodayWod();
    }

    public void getComments() throws JSONException {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("date", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_RES_GET_COMMENTS, send_data);
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
        Log.d("DEBUGYU_COMMENT",result_data.toString());
        JSONArray response = result_data.getJSONArray("response");
        count=0;
        if (result_code == 9999) { // 성공 시
            Log.d("DEBUGYU", response.toString()+"Length : "+response.length());
            count=result_data.getInt("count");
            // ReadPreviousChatData();
        } else if (result_code == 8888) { // 실패 시
            Log.e("DEBUGYU", "fail MakeRoom");
        }
    }

    public void RegisterReservation(int idx,String comment) {
        Log.d("DEBUGYU",comment);
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("date", toDate);
            send_data.put("class_num", idx + 1);
            send_data.put("name", User.getInstance().getData().getUser_name());
            send_data.put("id_email", User.getInstance().getData().getUser_email());
            send_data.put("comments",comment);
            send_data.put("user_gender",User.getInstance().getData().getUser_gender());
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        Log.d("DEBUGYU", "SEND DATA : " + send_data.toString());
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
            Toast.makeText(this, "등록되었습니다", Toast.LENGTH_LONG).show();
            adapter.setReserved(true);
            for (int i = 0; i < adapter.getCount(); i++) {
                if (i != idx)
                    adapter.getClassInfo(i).setSelected_state(RESERVATION_SELECT_STATE_NOT_SELECTED);
                else {
                    Participant me;
                    me = new Participant(User.getInstance().getData().getUser_access_key(), User.getInstance().getData().getUser_name());
                    adapter.getClassInfo(i).setSelected_state(RESERVATION_SELECT_STATE_SELECTED);
                    adapter.getClassInfo(i).getParticipants().add(me);
                    adapter.getClassInfo(i).addParticipants();
                }
            }
            count++;
            commentText.setText("댓글("+count+")");
            adapter.notifyDataSetChanged();
        } else if (result_code == 1270) {
            Toast.makeText(this, "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }else if (result_code == 1280) {
            Toast.makeText(this, "정원이 초과되었습니다.", Toast.LENGTH_LONG).show();
            try {
                getMyTodayWod();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.d("DEBUGYU", "CODE " + result_code);
        }
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

    @OnClick({R.id.commentBtn, R.id.commentText})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.commentBtn:
            case R.id.commentText:
                    if(count!=0){
                        Intent itC=new Intent(this,CommentActivity.class);
                        startActivity(itC);
                        }
                    else
                        Toast.makeText(this,"댓글이 없습니다",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
