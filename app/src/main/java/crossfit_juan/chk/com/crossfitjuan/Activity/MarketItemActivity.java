package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.Constants;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.DataModel.MarketItem;
import crossfit_juan.chk.com.crossfitjuan.DataModel.MarketItem_Img;
import crossfit_juan.chk.com.crossfitjuan.HttpConnection.CustomThread.ReqHTTPJSONThread;
import crossfit_juan.chk.com.crossfitjuan.R;

public class MarketItemActivity extends AppCompatActivity {
    String item_name = "";
    String item_title = "";
    String item_price = "";
    int item_remained = 0;
    boolean isAlreadyBuy=false;

    @BindView(R.id.market_item_act_title_bar)
    TextView marketItemActTitleBar;
    @BindView(R.id.market_item_act_linear)
    LinearLayout marketItemActLinear;
    @BindView(R.id.market_item_act_scroll)
    ScrollView marketItemActScroll;
    @BindView(R.id.market_item_act_remain)
    TextView marketItemActRemain;
    @BindView(R.id.market_item_act_price)
    TextView marketItemActPrice;
    @BindView(R.id.market_item_act_buyBtn)
    Button marketItemActBuyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        ButterKnife.bind(this);
        Intent it = getIntent();

        item_name = it.getExtras().getString("name");
        item_title = it.getExtras().getString("title");
        item_price = it.getExtras().getString("price");
        item_remained = it.getExtras().getInt("remained");
        isAlreadyBuy = it.getExtras().getBoolean("isLike");

        marketItemActRemain.setText(String.valueOf(item_remained));
        marketItemActPrice.setText(item_price);
        marketItemActTitleBar.setText(item_title);

        if(isAlreadyBuy){
            marketItemActBuyBtn.setText("신청 취소");
        }
        else{
            marketItemActBuyBtn.setText("구매 신청");
        }
        try {
            LoadItemInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                JSONObject newItem = image_list.getJSONObject(i);
                MarketItem_Img newItemImg = new MarketItem_Img();
                String Itemidx = newItem.getString("image");
                newItemImg.setIdx(Itemidx);

                try {

                    marketItemActLinear.addView(getImageFromServer(newItemImg));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            marketItemActLinear.addView(getTextViewContent(content));
        } else if (result_code == 5800) {
            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
    }
    public void RequestPurchaseItem(String comment) throws JSONException {
        JSONObject send_data = new JSONObject();

        send_data.put("item", item_name);
        send_data.put("id_email", User.getInstance().getData().getUser_email());
        send_data.put("comment",comment);
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_MARKET_ITEM_PURCHASE, send_data);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result_code == 9999) {
            Toast.makeText(getApplicationContext(), "구매 신청 되었습니다", Toast.LENGTH_LONG).show();
            isAlreadyBuy=true;
            marketItemActBuyBtn.setText("신청 취소");
            item_remained--;
            marketItemActRemain.setText(String.valueOf(item_remained));
        } else if (result_code == 8888) {
            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
    }

    public void CancelPurchaseItem() throws JSONException {
        JSONObject send_data = new JSONObject();

        send_data.put("item", item_name);
        send_data.put("id_email", User.getInstance().getData().getUser_email());
        ReqHTTPJSONThread thread = new ReqHTTPJSONThread(Constants.REQ_MARKET_ITEM_PURCHASE_CANCEL, send_data);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result_code == 9999) {
            Toast.makeText(getApplicationContext(), "신청 취소 되었습니다", Toast.LENGTH_LONG).show();
            isAlreadyBuy=false;
            marketItemActBuyBtn.setText("구매 신청");
            item_remained++;
            marketItemActRemain.setText(String.valueOf(item_remained));
        } else if (result_code == 7777) {
            Toast.makeText(getApplicationContext(), "잘못된 요청입니다", Toast.LENGTH_LONG).show();
        }
    }



    ImageView getImageFromServer(final MarketItem_Img itemImg) throws InterruptedException {
        final ImageView ret = new ImageView(this);
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try {
                    URL url = new URL(itemImg.getIdx());
                    Log.d("DEBUGYU", url.toString());
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    //final Bitmap resized = Bitmap.createScaledBitmap(bm, 128, 128, true);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            ret.setImageBitmap(bm);
                        }
                    });
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        ImageSetThread.start();

        ImageSetThread.join();
        ret.setAdjustViewBounds(true);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 20, 10);
        lp.gravity= Gravity.CENTER_HORIZONTAL;
        ret.setLayoutParams(lp);
        ret.setScaleType(ImageView.ScaleType.FIT_XY); // 레이아웃 크기에 이미지를 맞춘다


        return ret;
    }

    TextView getTextViewContent(String content){
        TextView ret=new TextView(this);
        ret.setText(content);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 10, 20, 10);
        ret.setTextSize(16);
        ret.setLayoutParams(lp);
        return ret;
    }

    @OnClick(R.id.market_item_act_buyBtn)
    public void onViewClicked() {
        // 구매 기능
        if (isAlreadyBuy) {
            try {
                CancelPurchaseItem();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            RequestPurchaseDialog();
        }
    }

    public void RequestPurchaseDialog(){
        final Dialog dR = new Dialog(MarketItemActivity.this);
        dR.setContentView(R.layout.register_marketitem_dialog);
        final EditText dRcomment=(EditText)dR.findViewById(R.id.market_dialog_comment);
        final ImageButton dRcommentClearBtn=(ImageButton)dR.findViewById(R.id.market_dialog_comment_clear_Btn);
        Button dRRegisterBtn=(Button)dR.findViewById(R.id.market_dialog_comment_register_Btn);
        Button dRCancelBtn=(Button)dR.findViewById(R.id.market_dialog_comment_cancel_Btn);
        dRcomment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    dRcommentClearBtn.setVisibility(View.INVISIBLE);
                }
                else{
                    dRcommentClearBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
        dRcommentClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dRcomment.setText("");
            }
        });
        dRRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tx=dRcomment.getText().toString();
                try {
                    RequestPurchaseItem(tx);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("DEBUGYU",tx);
                dR.dismiss();
            }
        });
        dRCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dR.dismiss();
            }
        });
        dR.show();
    }
}
