package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by Myown on 2017-08-09.
 */

public class UserData {

    // 어플리케이션의 User의 정보를 저장
    // user_access_key : DB 내에 저장된 document에 대한 접근용 key
    // user_email : 사용자의 이메일
    // user_name : 사용자의 이름
    // user_gender : 사용자의 성별 (M/F)
    // user_phone_number : 사용자의 폰 번호
    private String user_access_key;
    private String user_email;
    private String user_name;
    private String user_gender;
    private String user_phone_number;
    private String user_birtyday;

    private int user_locker_num;
    private String user_lock_start;
    private String user_lock_finish;

    private String user_start_date;
    private String user_finish_date;

    private int certification;
    private int remain_break_day;
    private int rest_state;


    public UserData(){
       this.user_access_key=null;
       this.user_email=null;
       this.user_name=null;
       this.user_gender=null;
       this.user_phone_number=null;
       this.user_birtyday=null;
       this.user_locker_num=-1;
       this.user_lock_start=null;
       this.user_lock_finish=null;
       this.user_start_date=null;
       this.user_finish_date=null;
       this.certification=0;
       this.remain_break_day=0;
       this.rest_state=0;
    }


    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(String user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public String getUser_access_key() {
        return user_access_key;
    }

    public void setUser_access_key(String user_access_key) {
        this.user_access_key = user_access_key;
    }

    public String getUser_birtyday() {
        return user_birtyday;
    }

    public void setUser_birtyday(String user_birtyday) {
        this.user_birtyday = user_birtyday;
    }

    public int getUser_locker_num() {
        return user_locker_num;
    }

    public void setUser_locker_num(int user_locker_num) {
        this.user_locker_num = user_locker_num;
    }

    public String getUser_lock_start() {
        return user_lock_start;
    }

    public void setUser_lock_start(String user_lock_start) {
        this.user_lock_start = user_lock_start;
    }

    public String getUser_lock_finish() {
        return user_lock_finish;
    }

    public void setUser_lock_finish(String user_lock_finish) {
        this.user_lock_finish = user_lock_finish;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public String getUser_start_date() {
        return user_start_date;
    }

    public void setUser_start_date(String user_start_date) {
        this.user_start_date = user_start_date;
    }

    public String getUser_finish_date() {
        return user_finish_date;
    }

    public void setUser_finish_date(String user_finish_date) {
        this.user_finish_date = user_finish_date;
    }

    public int getRemain_break_day() {
        return remain_break_day;
    }

    public void setRemain_break_day(int remain_break_day) {
        this.remain_break_day = remain_break_day;
    }

    public int getRest_state() {
        return rest_state;
    }

    public void setRest_state(int rest_state) {
        this.rest_state = rest_state;
    }

    public String getDataForLog(){
        return getUser_access_key() + " , " + getUser_email() + " , " + getUser_name() + " , " + getUser_gender();
    }
}
