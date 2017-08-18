package crossfit_juan.chk.com.crossfitjuan.DataModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class Time_Table {
    String date;
    String today_wod_name;
    String today_wod_content;
    int num_of_classes;
    Vector<Classinfo> classes;

    @Override
    public String toString() {
        String cls="";
        for(int i=0;i<classes.size()-1;i++){
            cls+=classes.get(i).toString()+", ";
        }
        if(!classes.isEmpty()) {
            cls += classes.get(classes.size() - 1).toString();
        }
        return "Time_Table{" +
                "date='" + date + '\'' +
                ", today_wod_name='" + today_wod_name + '\'' +
                ", today_wod_content='" + today_wod_content + '\'' +
                ", num_of_classes=" + num_of_classes +
                ", classes=" + cls+
                '}';
    }

    public Time_Table() {
        classes=new Vector<Classinfo>();
    }

    public Time_Table(String date, String today_wod_name, String today_wod_content, int num_of_classes, Vector<Classinfo> classes) {
        this.date = date;
        this.today_wod_name = today_wod_name;
        this.today_wod_content = today_wod_content;
        this.num_of_classes = num_of_classes;
        this.classes = classes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToday_wod_name() {
        return today_wod_name;
    }

    public void setToday_wod_name(String today_wod_name) {
        this.today_wod_name = today_wod_name;
    }

    public String getToday_wod_content() {
        return today_wod_content;
    }

    public void setToday_wod_content(String today_wod_content) {
        this.today_wod_content = today_wod_content;
    }

    public int getNum_of_classes() {
        return num_of_classes;
    }

    public void setNum_of_classes(int num_of_classes) {
        this.num_of_classes = num_of_classes;
    }

    public Vector<Classinfo> getClasses() {
        return classes;
    }

    public void setClasses(Vector<Classinfo> classes) {
        this.classes = classes;
    }

    public void addClasses(JSONObject data) throws JSONException {
        Classinfo info=new Classinfo();
        info.set_id(data.getString("_id"));
        info.setClass_num(data.getInt("class_num"));
        info.setStart_time(data.getString("start_time"));
        info.setFinish_time(data.getString("finish_time"));
        info.setMax_participant(data.getInt("max_participant"));
        JSONArray participants=data.getJSONArray("participant");
        for(int i=0;i<participants.length();i++){
            info.addParticipant(participants.getJSONObject(i));
       }
        classes.add(info);
        Log.d("DEBUGYU",info.toString());
    }

}
