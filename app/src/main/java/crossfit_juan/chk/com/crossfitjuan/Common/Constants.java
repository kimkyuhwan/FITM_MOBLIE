package crossfit_juan.chk.com.crossfitjuan.Common;

/**
 * Created by Myown on 2017-08-09.
 */

public class Constants {

    // 상수 변수, URL 등을 정리

    // 상수 변수
    public static final String Master_Socket_Address = "http://52.78.197.131:25478";
    public static final String DB_FILE_NAME="CrossFitJuan.db";
    public static final int REQUEST_PERMISSION_ACCESS_STORAGE =0x80;

    // URL
    public static final String REQ_LOGIN_URL = "http://52.78.197.131:25478/check_email";
    public static final String REGISTER_MEMBER_URL = "http://52.78.197.131:25478/register_member_data_stom";
    public static final String REQ_TODAY_ENROLL = "http://52.78.197.131:25478/res_check_class_by_date_m";
    public static final String REQ_DATE_SCHEDULE_CHECK ="http://52.78.197.131:25478/res_check_class_by_date_m";
    public static final String REQ_USER_DATE_SCHEDULE_CHECK ="http://52.78.197.131:25478/res_check_class_by_key_m";
    public static final String REQ_CANCEL_RESERVATION="http://52.78.197.131:25478/res_cancel_classes_m";
    public static final String REQ_REGISTER_RESERVATION="http://52.78.197.131:25478/res_enroll_classes_m";
    public static final String REQ_GET_NOTICE="http://52.78.197.131:25478/read_notification/list";
    public static final String REQ_GET_NOTICE_CONTENT="http://52.78.197.131:25478/read_notification/item";
    public static final String REQ_DELETE_USER="http://52.78.197.131:25478/delete_user";
    public static final String MAKE_ROOM_URL = "http://52.78.197.131:25478/sync_message_log";
    public static final String REQ_MARKET_ITEM_LIST="http://52.78.197.131:25478/list_item";
    public static final String REQ_MARKET_ITEM_INFO="http://52.78.197.131:25478/info_item";
    public static final String REQ_MARKET_ITEM_PURCHASE="http://52.78.197.131:25478/add_item";
    public static final String REQ_MARKET_ITEM_PURCHASE_CANCEL="http://52.78.197.131:25478/sub_item";

    public static final int PICK_FROM_ALBUM_ACTION=0x80;

    public static final String PROFILE_PATH="https://s3.ap-northeast-2.amazonaws.com/fitmbucket/";

}
