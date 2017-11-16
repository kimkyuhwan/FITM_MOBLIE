package crossfit_juan.chk.com.crossfitjuan.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossfit_juan.chk.com.crossfitjuan.Common.User;
import crossfit_juan.chk.com.crossfitjuan.R;
import crossfit_juan.chk.com.crossfitjuan.tool.AWSService;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.CERTIFICATION_ADMIN;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PICK_FROM_ALBUM_ACTION;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;
import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.REQUEST_PERMISSION_ACCESS_STORAGE;

public class RankingActivity extends AppCompatActivity {

    Bitmap bm;

    Uri ImageCaptureUrl = null;
    @BindView(R.id.ranking_prev_btn)
    ImageView rankingPrevBtn;
    @BindView(R.id.ranking_text_date)
    TextView rankingTextDate;
    @BindView(R.id.ranking_next_btn)
    ImageView rankingNextBtn;
    @BindView(R.id.ranking_upload_btn)
    ImageView rankingUploadBtn;
    @BindView(R.id.ranking_image)
    PhotoView rankingImage;

    Calendar calendar = Calendar.getInstance();
    @BindView(R.id.ranking_image_delete_btn)
    ImageView rankingImageDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        ButterKnife.bind(this);
        calendar.setTime(new Date());
        rankingTextDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));
        setRankImage(calendar.getTime());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdminBtn();
    }

    void setAdminBtn() {
        if (User.getInstance().getData().getCertification() >= CERTIFICATION_ADMIN) {
            rankingUploadBtn.setVisibility(View.VISIBLE);
            rankingImageDeleteBtn.setVisibility(View.VISIBLE);

        } else {
            rankingUploadBtn.setVisibility(View.INVISIBLE);
            rankingImageDeleteBtn.setVisibility(View.INVISIBLE);
        }
    }

    void setRankImage(final Date date) {
        Thread ImageSetThread = new Thread(new Runnable() {
            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                try {
                    URL url = new URL(PROFILE_PATH + new SimpleDateFormat("yyyyMMdd").format(date) + "_ranking.png");
                    InputStream is = url.openStream();
                    //  Matrix rotateMatrix = new Matrix();
                    //  rotateMatrix.postRotate(270);
                    bm = BitmapFactory.decodeStream(is);
                    //  final Bitmap sideInversionImg = Bitmap.createBitmap(bm, 0, 0,
                    //        bm.getWidth(), bm.getHeight(), rotateMatrix, false);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            rankingImage.setImageBitmap(bm);
                        }
                    });
                    //    tProfileImg.setImageBitmap(resized); //비트맵 객체로 보여주기
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rankingImage.setImageResource(R.drawable.no_rank);
                        }
                    });
                    e.printStackTrace();
                }

            }
        });

        ImageSetThread.start();
    }

    public void TakeRankingImagetoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM_ACTION);
    }

    public String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PICK_FROM_ALBUM_ACTION:
                ImageCaptureUrl = data.getData();
                String realpath = getImagePath(ImageCaptureUrl);
                AWSService.getInstance().upload_rank(new File(realpath), new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
                setRankImage(calendar.getTime());
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_ACCESS_STORAGE:
                int permission_num = 0;
                Log.d("DEUBGYU", String.valueOf(permissions.length));
                for (int i = 0; i < permissions.length; i++) {
                    int grantResult = grantResults[i];
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        permission_num++;
                    }
                }

                if (permission_num == 2) {
                    TakeRankingImagetoAlbum();
                } else {
                    Toast.makeText(getApplicationContext(), "권한을 허용해야 랭킹 이미지 등록 기능을 사용할 수 있습니다", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
    void DeleteCurrentDateFile(){
        Date date=calendar.getTime();
        String filename=new SimpleDateFormat("yyyyMMdd").format(date) + "_ranking";
        AWSService.getInstance().DeleteAmazonS3File(filename);

    }


    @OnClick({R.id.ranking_prev_btn, R.id.ranking_next_btn, R.id.ranking_upload_btn,R.id.ranking_image_delete_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ranking_prev_btn:
                calendar.add(Calendar.DATE, -1);
                rankingTextDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));
                setRankImage(calendar.getTime());
                break;
            case R.id.ranking_next_btn:
                Date today = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                if (!dateFormat.format(calendar.getTime()).equals(dateFormat.format(today))) {
                    calendar.add(Calendar.DATE, 1);
                    rankingTextDate.setText(new SimpleDateFormat("yyyy.MM.dd").format(calendar.getTime()));
                    setRankImage(calendar.getTime());
                }
                break;
            case R.id.ranking_upload_btn:
                int permission_ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permission_ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(RankingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_ACCESS_STORAGE);
                } else {
                    TakeRankingImagetoAlbum();
                }
                break;
            case R.id.ranking_image_delete_btn:
                DeleteCurrentDateFile();
                break;
        }
    }

}
