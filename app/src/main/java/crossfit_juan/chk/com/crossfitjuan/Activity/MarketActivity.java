package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import crossfit_juan.chk.com.crossfitjuan.Fragment.SellFragment;
import crossfit_juan.chk.com.crossfitjuan.R;

public class MarketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        FragmentManager fm=getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment fr=new SellFragment();
        fragmentTransaction.add(R.id.viewpager_empty,fr);
        fragmentTransaction.commit();

    }
}
