package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

public class MarketItemActivity extends AppCompatActivity {
    String item_name = "";
    @BindView(R.id.market_item_act_title_bar)
    TextView marketItemActTitleBar;
    @BindView(R.id.market_item_act_scroll)
    ScrollView marketItemActScroll;
    @BindView(R.id.market_item_act_remain)
    TextView marketItemActRemain;
    @BindView(R.id.market_item_act_registerBtn)
    Button marketItemActRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        ButterKnife.bind(this);
        Intent it = getIntent();
        item_name = it.getExtras().getString("name");
        Log.d("DEBUGYU",item_name);
    }

    public void LoadItemInfo() throws JSONException {
        JSONObject send_data = new JSONObject();

        send_data.put("name", item_name);
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_MARKET_ITEM_INFO, send_data);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException interex) {
            interex.printStackTrace();
        }
        String result = thread.handler.getMsg();
        JSONObject result_data = null;
        JSONObject response = null;
        int result_code = 0;
        try {
            result_data = new JSONObject(result);
            result_code = result_data.getInt("code");
            response = result_data.getJSONObject("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result_code == 9999) {

            String content = response.getString("in_content");
            JSONArray image_list = response.getJSONArray("image_list");
            for (int i = 0; i < image_list.length(); i++) {

            }
        } else if (result_code == 5800) {
            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.market_item_act_registerBtn)
    public void onViewClicked() {
    }
}
