package com.team_fitm.myown.fitm_mobile.DateUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Created by Myown on 2017-08-11.
 */

public class DateModule {

    // 현재 시간 확인용 클래스 & 메소드

    private String UTC;
    private String KST;
    private String KST_YYYY;
    private String KST_MM;
    private String KST_DD;

    public DateModule(){
        this.UTC = null;
        this.KST = null;
        setUTC();
        setKST();
    }

    public void setUTC(){
        DateFormat df = DateFormat.getDateInstance();
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = df.format(new Date());
        this.UTC = gmtTime;
    }

    public String getUTC(){
        return this.UTC;
    }

    public void setKST(){
        DateFormat df = DateFormat.getDateInstance();
        df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String gmtTime = df.format(new Date());
        this.KST = gmtTime;
        gmtTime = gmtTime.replaceAll(" " , "");
        StringTokenizer st = new StringTokenizer(gmtTime, ".");
        this.KST_YYYY = st.nextToken();
        this.KST_MM = st.nextToken();
        this.KST_DD = st.nextToken();
        if(this.KST_MM.length() == 1){
            String tmp = "0";
            tmp += this.KST_MM;
            this.KST_MM = tmp;
        }
    }

    public void setCurrentTime(){
        this.setKST();
        this.setUTC();
    }

    public String getKST(){
        return this.KST;
    }

    public String getKST_YYYY(){
        return this.KST_YYYY;
    }

    public String getKST_MM(){
        return this.KST_MM;
    }

    public String getKST_DD(){
        return this.KST_DD;
    }

}
