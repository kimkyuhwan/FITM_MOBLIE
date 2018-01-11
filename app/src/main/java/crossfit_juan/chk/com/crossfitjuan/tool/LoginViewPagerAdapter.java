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
    String titletextArray[]= {
            "크로스핏의 목적은 무엇인가?",
            "크로스핏은 어디에 좋을까?",
            "크로스핏은 무엇을 할까?",
            "크로스핏 주안으로!"
            };
    String bodytextArray[]={
            "크로스핏은 어느 한 분야에 특화된 피트니스 프로그램이 아닙니다. 10가지 영역의 육체능력을 골고루 극대화하려는 시도입니다. 이 10가지 능력에는 심폐지구력, 최대근력, 유연성, 협응력, 민첩성, 균형감각, 정확성, 파워, 스태미너, 속도가 들어갑니다.",
            "크로스핏은 다양한 근육을 골고루 발달시켜 신체능력을 극대화하고자 하려는 목적을 가지고 있습니다. 일반적인 보디빌딩식 운동이 특정 근육을 집중적으로 발달시키는 것이 목적이라면, 크로스핏은 집중적은 근육 단련보다는 전신 근육을 사용하고 강화시키는 것이 목적입니다.",
            "그리고 이런 운동들이 매일 W.O.D(Workout Of the Day)라는 이름으로 제시됩니다. 또한 단체수업인 W.O.D를 통해서 수업 참가자들과 기록 경쟁도 할 수 있고, 이 과정에서 참가자들과의 커뮤니티도 형성할 수 있습니다.",
            "요즘 제일 핫한 운동인 크로스핏! 과연 크로스핏은 어떤 운동인가? 어떠한 방식으로 운동하는가? 확실한 개념을 잡지 못한 여러분들께 ‘크로스핏 주안’이 크로스핏에 대해서 확실하게 파헤쳐 드립니다!"};
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
        TextView titletext = (TextView)view.findViewById(R.id.viewpager_childtitle);
        TextView bodytext = (TextView)view.findViewById(R.id.viewpager_childbody);
        img.setImageResource(imgArray[position]);
        titletext.setText(titletextArray[position]);
        bodytext.setText(bodytextArray[position]);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
