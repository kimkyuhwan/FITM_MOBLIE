package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by erslab-gh on 2017-08-23.
 */

public class ChatData {

    private String Sender;
    private String Date;
    private String Time;
    private String content;
    private long idx_time;
    private boolean isDateChangeSession;

    public ChatData() {
    }

    public ChatData(String sender, String date, String time, String content, long idx_time, boolean isDateChangeSession) {
        Sender = sender;
        Date = date;
        Time = time;
        this.content = content;
        this.idx_time = idx_time;
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

    public long getIdx_time() {
        return idx_time;
    }

    public void setIdx_time(long idx_time) {
        this.idx_time = idx_time;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "Sender='" + Sender + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", content='" + content + '\'' +
                ", idx_time=" + idx_time +
                ", isDateChangeSession=" + isDateChangeSession +
                '}';
    }
}
