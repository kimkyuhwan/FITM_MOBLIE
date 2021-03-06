package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.DataModel.MarketItem;
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
       // convertView=null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_market_item, parent, false);
            ImageView itemImg=(ImageView)convertView.findViewById(R.id.market_item_img);
            ImageView itemLikeImg=(ImageView)convertView.findViewById(R.id.market_item_like_img);
            TextView nameText=(TextView)convertView.findViewById(R.id.market_item_name);
            TextView likeCntText=(TextView)convertView.findViewById(R.id.market_item_like_cnt);
            TextView priceText=(TextView)convertView.findViewById(R.id.market_item_price);
            TextView likeText=(TextView)convertView.findViewById(R.id.market_item_like_text);
            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
//        TextView nameTextView = (TextView) convertView.findViewById(R.id.participant_name) ;
            //      TextView commentTextView = (TextView) convertView.findViewById(R.id.participant_comment) ;

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            MarketItem pp = listViewItemList.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            nameText.setText(pp.getTitle());
            likeCntText.setText(String.valueOf(pp.getLike_cnt()));
            //String price=setPriceString(pp.getPrice());
            priceText.setText(pp.getPrice());
            if(pp.isLike()){
                // 꽉찬 하트
                itemLikeImg.setImageResource(R.drawable.like);
                Log.d("DEBUGYU","Like!");
                likeText.setText("신청완료");
            }
            else {
                // 빈 하트
                itemLikeImg.setImageResource(R.drawable.unlike);
                Log.d("DEBUGYU","unLike!");
                likeText.setText("");
//            itemLikeImg.setImage;
            }

            getImageFromS3(pp.getReadImageUrl(),itemImg);
        }
        else{
            ImageView itemLikeImg=(ImageView)convertView.findViewById(R.id.market_item_like_img);
            TextView nameText=(TextView)convertView.findViewById(R.id.market_item_name);
            TextView likeCntText=(TextView)convertView.findViewById(R.id.market_item_like_cnt);
            TextView priceText=(TextView)convertView.findViewById(R.id.market_item_price);
            TextView likeText=(TextView)convertView.findViewById(R.id.market_item_like_text);
            MarketItem pp = listViewItemList.get(position);
            // 아이템 내 각 위젯에 데이터 반영
            nameText.setText(pp.getTitle());
            Log.d("DEBUGYU","LikeCntText"+pp.getLike_cnt());
            likeCntText.setText(String.valueOf(pp.getLike_cnt()));
            //String price=setPriceString(pp.getPrice());
            priceText.setText(pp.getPrice());
            if(pp.isLike()){
                // 꽉찬 하트
                itemLikeImg.setImageResource(R.drawable.like);
                Log.d("DEBUGYU","Like!");
                likeText.setText("신청완료");
            }
            else {
                // 빈 하트
                itemLikeImg.setImageResource(R.drawable.unlike);
                Log.d("DEBUGYU","unLike!");
                likeText.setText("");
//            itemLikeImg.setImage;
            }
        }



        return convertView;
    }

    public Object getListViewItemList(){
        return listViewItemList.clone();
    }

    public void setListViewItemList(ArrayList<MarketItem> list){
        listViewItemList.clear();
        for(int i=0;i<list.size();i++){
            addItem(list.get(i));
        }
    }
    void getImageFromS3(final String img_url, final ImageView imageView) {
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try{

                    URL url = new URL(img_url);
                    Log.d("DEBUGYU", url.toString());
                    InputStream is = url.openStream();
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    final Bitmap resized = Bitmap.createScaledBitmap(bm, 128, 128, true);
                    imageView.setImageBitmap(resized);
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        ImageSetThread.start();
    }


    public MarketItem getMarketItem(int position){
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

    public String getItemName(int position){
        return listViewItemList.get(position).getName();
    }
    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(MarketItem pp) {
        listViewItemList.add(pp);
    }
}
