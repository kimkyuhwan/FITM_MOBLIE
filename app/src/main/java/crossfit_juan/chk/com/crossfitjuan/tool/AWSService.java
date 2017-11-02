package crossfit_juan.chk.com.crossfitjuan.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteVersionRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import crossfit_juan.chk.com.crossfitjuan.Common.User;

import static crossfit_juan.chk.com.crossfitjuan.Common.Constants.PROFILE_PATH;

/**
 * Created by gyuhwan on 2017. 9. 26..
 */

public class AWSService {
    private static final String BUCKET_NAME = "fitmbucket";
    private static final String ACCESS_KEY = "AKIAIRVEKU6CRPS2CNSQ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;

    private static AWSService Instance=new AWSService();

    public static AWSService getInstance(){
        return Instance;
    }


    private AWSService() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        amazonS3.setEndpoint("s3.ap-northeast-2.amazonaws.com");


    }

    private void InstanceReload(){
        Instance=null;
        Instance=new AWSService();
    }


    public void upload(File file){
        uploadFileThread A=new uploadFileThread(file);
        A.start();
        try {
            A.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InstanceReload();
    }
    public void upload_rank(File file,String date){
        uploadRankThread A=new uploadRankThread(file,date);
        A.start();
        try {
            A.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InstanceReload();
    }

    public void DeleteProfile(){
        deleteFileThread A=new deleteFileThread();
        A.start();
        try {
            A.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InstanceReload();
    }


    private class uploadFileThread extends Thread{

        private File file;
        uploadFileThread(File file){
            this.file=file;
        }
        public void run() {
            uploadFile(file);
        }

    }

    public void uploadFile(File file) {
        if (amazonS3 != null) {
            try {
                Bitmap smallImg=BitmapFactory.decodeFile(file.getAbsolutePath());
                File file2=createThumbnail(smallImg,file.getParent(),User.getInstance().getData().getUser_email()+".png",240);
                Log.w("DEBUGYU",file2.getAbsolutePath());
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME /*sub directory*/, User.getInstance().getData().getUser_email()+".png", file2);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file
                file2.delete();
            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }


    private class uploadRankThread extends Thread{

        private File file;
        private String date;
        uploadRankThread(File file,String date){
            this.file=file;
            this.date=date;
        }
        public void run() {
            uploadRank(file,date);
        }

    }

    public void uploadRank(File file,String date) {
        if (amazonS3 != null) {
            try {
                Bitmap smallImg=BitmapFactory.decodeFile(file.getAbsolutePath());
                File file2=createThumbnail(smallImg,file.getParent(),User.getInstance().getData().getUser_email()+".png",480);
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME /*sub directory*/, date+"_ranking.png", file2);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file
                file2.delete();
            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }
    private class deleteFileThread extends Thread{

        deleteFileThread(){}
        public void run() {
            deleteProFile();
        }

    }
    public void deleteProFile() {
        if (amazonS3 != null) {
            try {
                amazonS3.deleteObject(new DeleteObjectRequest(BUCKET_NAME /*sub directory*/, User.getInstance().getData().getUser_email()+".png"));
            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }
    public static File createThumbnail(Bitmap bitmap, String strFilePath,
                                       String filename,int Size) {

        File file = new File(strFilePath);

        // If no folders
        if (!file.exists()) {
            file.mkdirs();
            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        File fileCacheItem = new File(strFilePath+ "/" + filename);
        OutputStream out = null;

        try {

            int height=bitmap.getHeight();
            int width=bitmap.getWidth();


            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap = Bitmap.createScaledBitmap(bitmap, Size, height/(width/Size), true);

            if(width<height){
                Matrix matrix=new Matrix();
                matrix.postRotate(270);
                Bitmap rotateBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                rotateBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            }
            else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileCacheItem;
    }


}
