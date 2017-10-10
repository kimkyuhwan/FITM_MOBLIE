package crossfit_juan.chk.com.crossfitjuan.tool;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import crossfit_juan.chk.com.crossfitjuan.R;

/**
 * Created by gyuhwan on 2017. 10. 10..
 */

public class LoginViewPagerAdapter extends PagerAdapter {

    LayoutInflater inflater;

    int imgArray[]={R.drawable.login_image01,R.drawable.login_image02,R.drawable.login_image03,R.drawable.login_image04};
    String textArray[]={"hello","it's me","where are you","come on"};
    public LoginViewPagerAdapter(LayoutInflater layoutInflater) {
        this.inflater=layoutInflater;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;
        view= inflater.inflate(R.layout.viewpager_childview, null);

        ImageView img= (ImageView)view.findViewById(R.id.viewpager_childimage);
        TextView text = (TextView)view.findViewById(R.id.viewpager_childitext);

        img.setImageResource(imgArray[position]);
        text.setText(textArray[position]);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
