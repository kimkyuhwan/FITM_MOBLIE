package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by gyuhwan on 2017. 10. 9..
 */

public class UserInfoData {

    String title;
    String body;
    String hint;

    public UserInfoData() {
    }

    public UserInfoData(String title, String body, String hint) {
        this.title = title;
        this.body = body;
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

}
