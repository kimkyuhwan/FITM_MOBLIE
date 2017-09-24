package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by gyuhwan on 2017. 9. 24..
 */

public class NoticeData {

    long notification_idx;
    String title;
    String contents;
    String date;
    String time;
    boolean isSelected;


    public NoticeData() {
        title="test";
        contents="hello";
        date="20101010";
        isSelected=false;
    }


    public NoticeData(long notification_idx, String title, String contents, String date, String time, boolean isSelected) {
        this.notification_idx = notification_idx;
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.time = time;
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "NoticeData{" +
                "notification_idx=" + notification_idx +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    // Get,Set Function


    public long getNotification_idx() {
        return notification_idx;
    }

    public void setNotification_idx(long notification_idx) {
        this.notification_idx = notification_idx;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
