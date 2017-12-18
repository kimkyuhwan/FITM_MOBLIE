package crossfit_juan.chk.com.crossfitjuan.Common;

/**
 * Created by Myown on 2017-08-09.
 */

public class Constants {

    // 상수 변수, URL 등을 정리

    // 네이버 로그인 상수 변수
    public static final String OAUTH_CLIENT_ID = "0TrX_XsSZ5xhftBKYCzN";
    public static final String OAUTH_CLIENT_SECRET= "GlE9Lv_n6W";
    public static final String OAUTH_CLIENT_NAME="CrossfitJuan";

    // 권한 접근 상수
    public static final int COMMAND_CERTIFICATION_RESERVATION=0x01;
    public static final int COMMAND_CERTIFICATION_MARKET=0x02;
    public static final int COMMAND_CERTIFICATION_SHOW_RANK=0x03;
    public static final int CERTIFICATION_RESERVATION=0x02;
    public static final int CERTIFICATION_MARKET=0x01;
    public static final int CERTIFICATION_REST=0x02;
    public static final int CERTIFICATION_SHOW_RANK=0x01;
    public static final int CERTIFICATION_ADMIN=0x03;


    // 휴회 기능 상태 변수
    public static final int REST_STATE_NONE=0;
    public static final int REST_STATE_REMAIN=1;
    public static final int REST_STATE_PENDING=2;
    public static final int REST_STATE_DONE=3;
    public static final int REST_STATE_FAILED=4;

    // 예약 상태 변수
    public static final int RESERVATION_SELECT_STATE_NONE = 0;
    public static final int RESERVATION_SELECT_STATE_SELECTED = 1;
    public static final int RESERVATION_SELECT_STATE_NOT_SELECTED = 2;


    // 상수 변수
    public static final String Master_Socket_Address = "http://52.78.197.131:25478";
    public static final String DB_FILE_NAME="CrossFitJuan.db";
    public static final String CROSSFITJUAN_ADDRESS_GOOGLE="https://www.google.co.kr/maps/place/%EC%9D%B8%EC%B2%9C%EA%B4%91%EC%97%AD%EC%8B%9C+%EB%82%A8%EA%B5%AC+%EC%A3%BC%EC%95%88%EB%A1%9C+100";
    public static final int REQUEST_PERMISSION_ACCESS_STORAGE =0x80;
    public static final String PUSH_NOTICE_ACTIVITY="0";
    public static final String PUSH_TODAY_WOD="1";
    public static final String PUSH_QNA_ACTIVITY="2";
    public static final String PUSH_REST_ACCEPT="3";
    public static final String PUSH_REST_REJECT="4";
    public static final String PUSH_ADVERTISE="5";


    // 유저 정보 상수 변수
    public static final int USER_INFO_INDEX_REST_PERIOD=1;
    public static final int USER_INFO_INDEX_LOGOUT=7;
    public static final int USER_INFO_INDEX_SIGNOUT=8;

    // 커뮤니티 주소
    public static final String CROSSFITJUAN_NAVER_CAFE_URL="http://cafe.naver.com/cfja";
    public static final String CROSSFITJUAN_FACEBOOK_URL="https://www.facebook.com/crossfit.juan";
    public static final String CROSSFITJUAN_NAVER_CAFE_CALL_URL="navercafe://cafe?cafeUrl="+CROSSFITJUAN_NAVER_CAFE_URL+"&appId=crossfit_juan.chk.com.crossfitjuan";
    public static final String CROSSFITJUAN_INSTAGRAM_URL="https://www.instagram.com/crossfit.juan/";

    public static final String APP_ID="crossfit_juan.chk.com.crossfitjuan";
    public static final String CAFE_URL="cfja";


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

    public static final String REQ_SET_REMAIN_DAY="http://52.78.197.131:25478/set_remain_day";
    public static final String REQ_REGISTER_FIREBASE_TOKEN="http://52.78.197.131:25478/udt_device_token";
    public static final String REQ_GET_REMAIN_DAY="http://52.78.197.131:25478/get_remain_day";
    public static final String REQ_GET_USER_DATA_STOM="http://52.78.197.131:25478/get_member_data_stom";
    public static final String REQ_CANCEL_REMAIN_DAY="http://52.78.197.131:25478/cancel_remain_day";

    public static final String REQ_RES_GET_COMMENTS="http://52.78.197.131:25478/res_get_comments";
    public static final int PICK_FROM_ALBUM_ACTION=0x80;

    public static final String PROFILE_PATH="https://s3.ap-northeast-2.amazonaws.com/fitmbucket/";

}
