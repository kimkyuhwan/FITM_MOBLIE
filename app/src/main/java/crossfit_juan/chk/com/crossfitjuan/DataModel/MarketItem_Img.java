package crossfit_juan.chk.com.crossfitjuan.DataModel;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

/**
 * Created by gyuhwan on 2017. 9. 30..
 */

public class MarketItem_Img {
    private String img_url;
    private int idx;

    public MarketItem_Img() {
    }

    public MarketItem_Img(String img_url, int idx) {
        this.img_url = img_url;
        this.idx = idx;
    }

    public String getRealImageUrl(){
        return PROFILE_PATH+img_url+String.valueOf(idx)+".png";
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }


    @Override
    public String toString() {
        return "MarketItem_Img{" +
                "img_url='" + img_url + '\'' +
                ", idx=" + idx +
                '}';
    }
}
