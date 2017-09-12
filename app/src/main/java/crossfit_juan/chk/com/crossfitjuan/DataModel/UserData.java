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

    public UserData(){
        this.user_email = null;
        this.user_gender = null;
        this.user_phone_number = null;
        this.user_name = null;
        this.user_birtyday=null;
    }

    public UserData(String email, String name, String gender, String phone_number,String user_birtyday){
        this.user_name = name;
        this.user_email = email;
        this.user_gender = gender;
        this.user_phone_number = phone_number;
        this.user_birtyday=user_birtyday;
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

    public String getDataForLog(){
        return getUser_access_key() + " , " + getUser_email() + " , " + getUser_name() + " , " + getUser_gender();
    }
}
