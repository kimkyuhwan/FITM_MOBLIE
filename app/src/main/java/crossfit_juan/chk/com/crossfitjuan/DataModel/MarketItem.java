package crossfit_juan.chk.com.crossfitjuan.DataModel;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.ArrayList;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

/**
 * Created by erslab-gh on 2017-09-22.
 */

public class MarketItem {

    String item_id;
    String date;
    String contents;
    String name;

    String img_url; // JSON에선 main_image으로 판별
    int remained_num_of_item;
    String title;
    String price;

    ArrayList<MarketItem_Img> image_list;

    int like_cnt;
    boolean isLike;

    public MarketItem() {
        image_list=new ArrayList<MarketItem_Img>();
    }

    public void addItemImage(MarketItem_Img item){
        image_list.add(item);
    }

    public void buyItem(){
        remained_num_of_item--;
    }

    public String getReadImageUrl(){
        return PROFILE_PATH+img_url+".png";
    }

    // Default get, set Function



    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getRemained_num_of_item() {
        return remained_num_of_item;
    }

    public void setRemained_num_of_item(int remained_num_of_item) {
        this.remained_num_of_item = remained_num_of_item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public ArrayList<MarketItem_Img> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<MarketItem_Img> image_list) {
        this.image_list = image_list;
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
    // End

    @Override
    public String toString() {
        return "MarketItem{" +
                "item_id='" + item_id + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                ", img_url='" + img_url + '\'' +
                ", remained_num_of_item=" + remained_num_of_item +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", image_list size=" + image_list.size() +
                ", like_cnt=" + like_cnt +
                ", isLike=" + isLike +
                '}';
    }
}
