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
        toDate = getToDate();
        adapter = new TimetableViewAdapter(this,toDate);
        timetableList.setAdapter(adapter);

        try {
            getMyTodayWod();
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
/*
    @OnClick({R.id.select_schedule_Btn, R.id.schedule_Register_Btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_schedule_Btn:
                if (isPossibleToReserve) {
                    // 시간 선택 기능
                    final Dialog d = new Dialog(ReservationActivity.this);
                    d.setContentView(R.layout.time_select_dialog);
                    Button cancelBtn = (Button) d.findViewById(R.id.dialog_cancel_btn);
                    Button doneBtn = (Button) d.findViewById(R.id.dialog_done_btn);
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });


                    final NumberPicker np = (NumberPicker) d.findViewById(R.id.schedule_picker);
                    String A[] = new String[timetable.getNum_of_classes()];
                    for (int i = 0; i < A.length; i++) {
                        A[i] = timetable.getClasses().get(i).getStart_time() + " ~ " + timetable.getClasses().get(i).getFinish_time();
                    }
                    np.setMinValue(0);
                    np.setMaxValue(A.length - 1);
                    np.setDisplayedValues(A);
                    np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    doneBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedSchedule = np.getValue();
                            selectScheduleBtn.setText(timetable.getClasses().get(np.getValue()).getStart_time() + " ~ " + timetable.getClasses().get(np.getValue()).getFinish_time());
                            try {
                                getTodayWod();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            d.dismiss();
                        }
                    });
                    Window window = d.getWindow();
                    window.setGravity(Gravity.BOTTOM);
                    d.show();
                } else {
                    Toast.makeText(getApplicationContext(), "오늘은 예약이 불가능합니다", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.schedule_Register_Btn:
                if (isReserved) {
                    CancelReservation();
                } else if (selectScheduleBtn.getText().toString().equals("클릭!")) {
                    Toast.makeText(getApplicationContext(), "시간을 선택해주세요", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getApplicationContext(), "스케줄 등록 기능 준비중", Toast.LENGTH_LONG).show();
                    final Dialog dR = new Dialog(ReservationActivity.this);
                    dR.setContentView(R.layout.register_reservation_dialog);
                    TextView dRTime = (TextView) dR.findViewById(R.id.dialog_time);
                    final EditText dRcomment = (EditText) dR.findViewById(R.id.dialog_comment);
                    final ImageButton dRcommentClearBtn = (ImageButton) dR.findViewById(R.id.dialog_comment_clear_Btn);
                    Button dRRegisterBtn = (Button) dR.findViewById(R.id.dialog_comment_register_Btn);
                    Button dRCancelBtn = (Button) dR.findViewById(R.id.dialog_comment_cancel_Btn);
                    dRTime.setText(selectScheduleBtn.getText().toString());
                    dRcomment.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            // do nothing
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                dRcommentClearBtn.setVisibility(View.INVISIBLE);
                            } else {
                                dRcommentClearBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            // do nothing
                        }
                    });
                    dRcommentClearBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dRcomment.setText("");
                        }
                    });
                    dRRegisterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tx = dRcomment.getText().toString();
                            RegisterReservation(tx);
                            Log.d("DEBUGYU", tx);
                            dR.dismiss();
                        }
                    });
                    dRCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dR.dismiss();
                        }
                    });
                    dR.show();
                }

                break;
        }
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        User.setHereActivityContext(this);
        User.setHereActivity("Reservation");
    }
}
