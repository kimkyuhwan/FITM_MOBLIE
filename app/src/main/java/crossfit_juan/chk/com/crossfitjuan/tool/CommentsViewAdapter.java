package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.R;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class CommentsViewAdapter extends BaseAdapter {
    private ArrayList<Participant> listViewItemList = new ArrayList<Participant>() ;

    // ListViewAdapter의 생성자
    public CommentsViewAdapter() {
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
            convertView = inflater.inflate(R.layout.listview_comments, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView nameText = (TextView) convertView.findViewById(R.id.comment_name);
        TextView CommentText = (TextView) convertView.findViewById(R.id.comment_text);
        final Participant pp = listViewItemList.get(position);
        final CircleImageView circleImageView= (CircleImageView)convertView.findViewById(R.id.comment_img);
        // 아이템 내 각 위젯에 데이터 반영
        if (pp.getName() == null) {
            nameText.setText("test");
        } else {
            nameText.setText(pp.getName());
        }
        if (pp.getComment() == null) {
            CommentText.setText("반가워요");
        } else {
            CommentText.setText(pp.getComment());
        }
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try {
                    URL url = new URL(PROFILE_PATH + pp.getId_email() + ".png");
                    InputStream is = url.openStream();
                    final Bitmap  bm = BitmapFactory.decodeStream(is);
                    circleImageView.setImageBitmap(bm);
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch (Exception e) {
                     circleImageView.setImageResource(R.drawable.default_profile);
                    e.printStackTrace();
                }

            }
        });

        ImageSetThread.start();
        try {
            ImageSetThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    void setImage(String email){

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
    public void addItem(Participant pp) {
        listViewItemList.add(pp);
    }


}
