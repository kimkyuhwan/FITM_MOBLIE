package crossfit_juan.chk.com.crossfitjuan.DataModel;

import android.media.Image;

/**
 * Created by erslab-gh on 2017-09-22.
 */

public class MarketItem {
    Image itemimg;
    String title;
    long price;
    int like_cnt;
    boolean isLike;

    public MarketItem() {
        itemimg=null;
        title="초기화 에러";
        price=0;
        like_cnt=0;
        isLike=false;
    }

    public MarketItem(Image itemimg, String title, long price, int like_cnt, boolean isLike) {
        this.itemimg = itemimg;
        this.title = title;
        this.price = price;
        this.like_cnt = like_cnt;
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "MarketItem{" +
                "itemimg=" + itemimg +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", like_cnt=" + like_cnt +
                ", isLike=" + isLike +
                '}';
    }

    public Image getItemimg() {
        return itemimg;
    }

    public void setItemimg(Image itemimg) {
        this.itemimg = itemimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
