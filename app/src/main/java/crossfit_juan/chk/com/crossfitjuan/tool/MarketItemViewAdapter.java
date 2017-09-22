package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.DataModel.MarketItem;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by erslab-gh on 2017-09-22.
 */

public class MarketItemViewAdapter extends BaseAdapter {
    private ArrayList<MarketItem> listViewItemList = new ArrayList<MarketItem>() ;

    // ListViewAdapter의 생성자
    public MarketItemViewAdapter() {

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
            convertView = inflater.inflate(R.layout.listview_market_item, parent, false);
        }
        ImageView itemImg=(ImageView)convertView.findViewById(R.id.market_item_img);
        ImageView itemLikeImg=(ImageView)convertView.findViewById(R.id.market_item_like_img);
        TextView nameText=(TextView)convertView.findViewById(R.id.market_item_name);
        TextView likeCntText=(TextView)convertView.findViewById(R.id.market_item_like_cnt);
        TextView priceText=(TextView)convertView.findViewById(R.id.market_item_price);
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        TextView nameTextView = (TextView) convertView.findViewById(R.id.participant_name) ;
  //      TextView commentTextView = (TextView) convertView.findViewById(R.id.participant_comment) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MarketItem pp = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        nameText.setText(pp.getTitle());
        likeCntText.setText(String.valueOf(pp.getLike_cnt()));
        priceText.setText(String.valueOf(pp.getPrice()+"원"));
        /*itemImg.setImageBitmap(pp.getItemimg());
        if(pp.isLike()){
            // 꽉찬 하트
        }
        else{
            // 빈 하트
        }*/


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
    public void addItem(MarketItem pp) {
        listViewItemList.add(pp);
    }
}
