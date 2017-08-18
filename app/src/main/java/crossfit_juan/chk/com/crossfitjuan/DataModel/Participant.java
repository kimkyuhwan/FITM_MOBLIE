package crossfit_juan.chk.com.crossfitjuan.DataModel;

/**
 * Created by erslab-gh on 2017-08-18.
 */

public class Participant {
    private String comment;
    private String access_key;
    private String name;

    public Participant() {}

    public Participant(String comment, String access_key, String name) {
        this.comment = comment;
        this.access_key = access_key;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
