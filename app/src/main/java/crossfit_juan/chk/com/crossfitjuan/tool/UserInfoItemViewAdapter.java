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
            TextView titleText=(TextView)convertView.findViewById(R.id.user_info_item_title);
            TextView bodyText=(TextView)convertView.findViewById(R.id.user_info_item_body);
            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        TextView nameTextView = (TextView) convertView.findViewById(R.id.participant_name) ;
            //      TextView commentTextView = (TextView) convertView.findViewById(R.id.participant_comment) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            UserInfoData pp = listViewItemList.get(position);
            // 아이템 내 각 위젯에 데이터 반영
            titleText.setText(pp.getTitle());
            bodyText.setText(pp.getBody());
            bodyText.setHint(pp.getHint());
            //String price=setPriceString(pp.getPrice());
        }


        return convertView;
    }

    public void changebodyText(int idx,String body,String hint){
        listViewItemList.get(idx).setBody(body);
        listViewItemList.get(idx).setHint(hint);
        notifyDataSetChanged();
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
