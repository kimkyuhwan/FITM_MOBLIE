package crossfit_juan.chk.com.crossfitjuan.tool;

import android.os.Bundle;
import android.os.Message;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

import crossfit_juan.chk.com.crossfitjuan.Common.User;

/**
 * Created by gyuhwan on 2017. 9. 26..
 */

public class AWSService {
    private static final String BUCKET_NAME = "fitmbucket";
    private static final String ACCESS_KEY = "AKIAIRVEKU6CRPS2CNSQ";
    private static final String SECRET_KEY = "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";
    private AmazonS3 amazonS3;

    private static final AWSService Instance=new AWSService();

    public static AWSService getInstance(){
        return Instance;
    }


    private AWSService() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        amazonS3 = new AmazonS3Client(awsCredentials);
        amazonS3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        amazonS3.setEndpoint("s3.ap-northeast-2.amazonaws.com");


    }

    public void uploadFileThread(final File file){
        new Thread() {
            public void run() {
                uploadFile(file);
            }
        }.start();


    }


    public void uploadFile(File file) {
        if (amazonS3 != null) {
            try {
                PutObjectRequest putObjectRequest =
                        new PutObjectRequest(BUCKET_NAME /*sub directory*/, User.getInstance().getData().getUser_email()+".png", file);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead); // file permission
                amazonS3.putObject(putObjectRequest); // upload file

            } catch (AmazonServiceException ase) {
                ase.printStackTrace();
            } finally {
                amazonS3 = null;
            }
        }
    }


}
