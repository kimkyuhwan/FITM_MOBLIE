package com.team_fitm.myown.fitm_mobile.DataModels;

/**
 * Created by Myown on 2017-08-11.
 */

public class EnrollListViewItem {
    private String class_num;
    private String start_time;
    private String finish_time;
    private String time;
    private String max_participant;
    private String cur_participant;

    public EnrollListViewItem(){
        // default
    }

    public EnrollListViewItem(String class_num, String start_time, String finish_time, String max_participant, String cur_participant){
        this.class_num = class_num;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.time = this.start_time + " ~ " +this.finish_time;
        this.max_participant = max_participant;
        this.cur_participant = cur_participant;
    }

    public String getClass_num() {
        return class_num;
    }

    public void setClass_num(String class_num) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMax_participant() {
        return max_participant;
    }

    public void setMax_participant(String max_participant) {
        this.max_participant = max_participant;
    }

    public String getCur_participant() {
        return cur_participant;
    }

    public void setCur_participant(String cur_participant) {
        this.cur_participant = cur_participant;
    }
}
