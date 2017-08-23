package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by erslab-gh on 2017-08-23.
 */

public class ChatData {

    private String Sender;
    private String Date;
    private String Time;
    private String content;
    private boolean isDateChangeSession;

    public ChatData() {
    }

    public ChatData(String sender, String date, String time, String content, boolean isDateChangeSession) {
        Sender = sender;
        Date = date;
        Time = time;
        this.content = content;
        this.isDateChangeSession = isDateChangeSession;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDateChangeSession() {
        return isDateChangeSession;
    }

    public void setDateChangeSession(boolean dateChangeSession) {
        isDateChangeSession = dateChangeSession;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "Sender : '" + Sender + '\'' +
                "content : '" + content + '\'' +
                ", Date='" + Date + ':' +
                ", Time='" + Time +
                '}';
    }
}
