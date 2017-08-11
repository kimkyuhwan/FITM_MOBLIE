package com.team_fitm.myown.fitm_mobile.ListViewPackage.Enroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team_fitm.myown.fitm_mobile.DataModels.EnrollListViewItem;
import com.team_fitm.myown.fitm_mobile.R;

import java.util.ArrayList;

/**
 * Created by Myown on 2017-08-11.
 */

public class EnrollListViewAdapter extends BaseAdapter {

    private ArrayList<EnrollListViewItem> listViewItems = new ArrayList<EnrollListViewItem>();

    public EnrollListViewAdapter(){

    }

    @Override
    public int getCount() {
        return listViewItems.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;
        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.enroll_listview_item, viewGroup, false);
        }

        TextView class_num = (TextView)view.findViewById(R.id.txtv_enroll_list_item_class_num);
        TextView time = (TextView)view.findViewById(R.id.txtv_enroll_list_item_time);
        TextView max_participant = (TextView)view.findViewById(R.id.txtv_enroll_list_item_max_participant);
        TextView cur_participant = (TextView)view.findViewById(R.id.txtv_enroll_list_item_cur_participant);

        EnrollListViewItem item = listViewItems.get(pos);
        class_num.setText(item.getClass_num());
        time.setText(item.getTime());
        max_participant.setText(item.getMax_participant());
        cur_participant.setText(item.getCur_participant());

        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    public void addItem(String class_num, String start_time, String finish_time, String max, String cur){
        EnrollListViewItem item = new EnrollListViewItem(class_num, start_time, finish_time, max, cur);
        listViewItems.add(item);
    }
}
