package crossfit_juan.chk.com.crossfitjuan.tool;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;

/**
 * Created by gyuhwan on 2017. 9. 17..
 */

public class ChatDBHelper extends SQLiteOpenHelper{

    final private String TAG="ChatDataDBHelper";
    long last_idx=0;
    String last_day="";
    public ChatDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM CHATDATA",null);
        cursor.moveToLast();
        cursor.moveToPrevious();
        while(cursor.moveToNext()) {
            last_idx = cursor.getLong(0);
            last_day=cursor.getString(4);
            Log.d(TAG,"last is "+String.valueOf(last_idx));
        }
    }

    public String getLast_day() {
        return last_day;
    }

    public void setLast_day(String last_day) {
        this.last_day = last_day;
    }

    public long getLast_idx() {
        return last_idx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //    Log.d(TAG,"Create!!");
        db.execSQL("CREATE TABLE CHATDATA (_id BIGINT , sender TEXT, message TEXT,time TEXT,date TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void InsertData(ChatData cData){
        long idx_time=cData.getIdx_time();
        String sender=cData.getSender();
        String message=cData.getContent();
        String time=cData.getTime();
        String date=cData.getDate();
        SQLiteDatabase db=getWritableDatabase();
     //   Log.d(TAG,"Insert!!"+db.toString());
        last_day=date;
        db.execSQL("INSERT INTO CHATDATA VALUES("+idx_time+", "+"'"+sender+"', '"+message+"', '"+time+"', '"+date+"');");
        db.close();
    }

    public ArrayList<ChatData> getResult(){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<ChatData> result=new ArrayList<ChatData>();

        Cursor cursor=db.rawQuery("SELECT * FROM CHATDATA",null);
        while(cursor.moveToNext()){
            ChatData a=new ChatData();
           // Log.d("HEllo",cursor.getString(1));
            a.setIdx_time(cursor.getLong(0));
            a.setSender(cursor.getString(1));
            a.setContent(cursor.getString(2));
            a.setTime(cursor.getString(3));
            a.setDate(cursor.getString(4));

            result.add(a);
            Log.d("AAAAAA",a.toString());
        }
//        Log.d(TAG,"Get Result!!");

        db.close();
        return result;
    }

}
