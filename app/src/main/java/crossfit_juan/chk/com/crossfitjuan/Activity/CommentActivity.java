package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import crossfit_juan.chk.com.crossfitjuan.DataModel.Participant;
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
        adapter=new CommentsViewAdapter();
        adapter.addItem(new Participant("aa","김규환"));
        adapter.addItem(new Participant("aa","김찬희"));
        adapter.addItem(new Participant("aa","이광용"));
        adapter.addItem(new Participant("aa","박령민"));
        adapter.addItem(new Participant("aa","박성호"));
        listComment.setAdapter(adapter);
    }
}
