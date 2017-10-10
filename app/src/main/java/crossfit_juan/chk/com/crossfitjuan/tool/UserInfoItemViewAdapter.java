package crossfit_juan.chk.com.crossfitjuan.tool;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.Activity.ReservationActivity;
import crossfit_juan.chk.com.crossfitjuan.Activity.UserInfoActivity;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.MarketItem;
import crossfit_juan.chk.com.crossfitjuan.DataModel.UserInfoData;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by erslab-gh on 2017-09-22.
 */

public class UserInfoItemViewAdapter extends BaseAdapter {
    private ArrayList<UserInfoData> listViewItemList = new ArrayList<UserInfoData>() ;

    Context context;
    // ListViewAdapter의 생성자
    public UserInfoItemViewAdapter(Context context) {
        this.context=context;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        convertView=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_userinfo_item, parent, false);
            ImageButton itemImg=(ImageButton)convertView.findViewById(R.id.user_info_item_imageBtn);
            TextView titleText=(TextView)convertView.findViewById(R.id.user_info_item_title);
            TextView bodyText=(TextView)convertView.findViewById(R.id.user_info_item_body);
            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        TextView nameTextView = (TextView) convertView.findViewById(R.id.participant_name) ;
            //      TextView commentTextView = (TextView) convertView.findViewById(R.id.participant_comment) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            UserInfoData pp = listViewItemList.get(position);
            itemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rest();
                }
            });
            // 아이템 내 각 위젯에 데이터 반영
            titleText.setText(pp.getTitle());
            bodyText.setText(pp.getBody());
            bodyText.setHint(pp.getHint());
            if(pp.isBtnVisible()){
                itemImg.setVisibility(View.VISIBLE);
            }
            //String price=setPriceString(pp.getPrice());
        }


        return convertView;
    }

    void rest(){
        final Dialog dR = new Dialog(context);
        dR.setContentView(R.layout.register_rest_dialog);
        TextView dRTitle=(TextView)dR.findViewById(R.id.rest_dialog_title);
        final EditText dRcomment=(EditText)dR.findViewById(R.id.rest_dialog_comment);
        final ImageButton dRcommentClearBtn=(ImageButton)dR.findViewById(R.id.rest_dialog_comment_clear_Btn);
        Button dRRegisterBtn=(Button)dR.findViewById(R.id.rest_dialog_comment_register_Btn);
        Button dRCancelBtn=(Button)dR.findViewById(R.id.rest_dialog_comment_cancel_Btn);
        dRcomment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    dRcommentClearBtn.setVisibility(View.INVISIBLE);
                }
                else{
                    dRcommentClearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
        String title=dRTitle.getText().toString();
        title+="\n(남은 휴회 일수는 "+User.getInstance().getData().getRemain_break_day()+"일 입니다)";
        dRTitle.setText(title);
        dRcommentClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dRcomment.setText("");
            }
        });
        dRRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tx=dRcomment.getText().toString();
                try {
                    RegisterRest(tx);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUGYU",tx);
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
    void RegisterRest(String body) throws JSONException {
        JSONObject send_data = new JSONObject();
        try{
            send_data.put("access_key", User.getInstance().getData().getUser_access_key());
            send_data.put("comments",body);
        }catch (JSONException jsonex){
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_SET_REMAIN_DAY, send_data);
        thread.start();
        try{
            thread.join();
        }catch (InterruptedException interex){
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = new JSONObject(result);
        int result_code = result_data.getInt("code");
       // JSONObject response=result_data.getJSONObject(  "response");
        if(result_code==8888){
            Log.d("DEBUGYU","신청되었습니다");
        }
        else if(result_code==5800){
            Log.d("DEBUGYU","신청에 실패했습니다");
        }
    }


    public UserInfoData getMarketItem(int position){
        return listViewItemList.get(position);
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
    public void addItem(UserInfoData pp) {
        listViewItemList.add(pp);
    }

}
