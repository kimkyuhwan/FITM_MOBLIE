package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.ChatData;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.CommentsViewAdapter;

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.list_comment)
    ListView listComment;
    CommentsViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        try {
            getComments();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getComments() throws JSONException {
        JSONObject send_data = new JSONObject();
        try {
            send_data.put("date", new SimpleDateFormat("yyyyMMdd").format(new Date()));
        } catch (JSONException jsonex) {
            jsonex.printStackTrace();
        }
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_RES_GET_COMMENTS, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }

        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        int result_code = 0;
        result_data = new JSONObject(result);
        result_code = result_data.getInt("code");
        Log.d("DEBUGYU",result_data.toString());
        JSONArray response = result_data.getJSONArray("response");

        if (result_code == 9999) { // 성공 시
            Log.d("DEBUGYU", response.toString()+"Length : "+response.length());
            adapter=new CommentsViewAdapter();
            for (int i = 0; i < response.length(); i++) {
                Log.d("DEBUGYU",toString());
                JSONObject here=response.getJSONObject(i);
                String hereEmail = here.getString("id_email");
                String hereName = here.getString("name");
                String hereComments = here.getString("comments");
                if(hereComments==null)
                    adapter.addItem(new Participant(hereName,hereEmail,"반가워요"));
                else
                    adapter.addItem(new Participant(hereName,hereEmail,hereComments));
            }
            listComment.setAdapter(adapter);
            // ReadPreviousChatData();
        } else if (result_code == 8888) { // 실패 시
            Log.e("DEBUGYU", "fail MakeRoom");
        }

    }
}
