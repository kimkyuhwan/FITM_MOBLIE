package crossfit_juan.chk.com.crossfitjuan.DataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class Classinfo {
    private int class_num;
    private String start_time;
    private String finish_time;
    private String _id;
    private int max_participant;
    private Vector<Participant> participants;
    private int participants_size;
    private int selected_state;
    public Classinfo() {
        participants=new Vector<Participant>();
        class_num=-1;
    }

    public Classinfo(int class_num, String start_time, String finish_time, String _id, int max_participant, Vector<Participant> participants) {
        this.class_num = class_num;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this._id = _id;
        this.max_participant = max_participant;
        this.participants = participants;
        participants_size=participants.size();
    }

    void addParticipant (JSONObject participant){
        Participant p=new Participant();
        try {
            p.setAccess_key(participant.getString("access_key"));
            p.setComment(participant.getString("comments"));
            p.setName(participant.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        participants.add(p);
    }

    @Override
    public String toString() {
        String part="";
        for(int i=0;i<participants.size()-1;i++){
            part+=participants.get(i)+", ";
        }
        if(!participants.isEmpty()) {
            part += participants.get(participants.size() - 1);
        }
        return "Classinfo{" +
                "class_num=" + class_num +
                ", start_time='" + start_time + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", _id='" + _id + '\'' +
                ", max_participant=" + max_participant +
                ", participants=" + part+
                '}';
    }

    public int getClass_num() {
        return class_num;
    }

    public void setClass_num(int class_num) {
        this.class_num = class_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Vector<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Vector<Participant> participants) {
        this.participants = participants;
    }

    public int getMax_participant() {
        return max_participant;
    }

    public void setMax_participant(int max_participant) {
        this.max_participant = max_participant;
    }

    public int getSelected_state() {
        return selected_state;
    }

    public void setSelected_state(int selected_state) {
        this.selected_state = selected_state;
    }

    public int getParticipants_size(){
        return participants_size;
    }
    public void addParticipants(){
        participants_size++;
    }
    public void subtractParticipants(){
        participants_size--;
    }
}
