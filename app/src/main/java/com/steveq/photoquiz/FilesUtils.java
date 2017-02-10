package com.steveq.photoquiz;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.steveq.photoquiz.database.DatabaseManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesUtils {

    private static final String TAG = FilesUtils.class.getSimpleName();
    private Context mContext;
    private String fileType = ".jpg";

    public FilesUtils(Context context) {
        mContext = context;
    }

    public Uri getOutputUri(long id) throws IllegalStateException{
        File outputDir = null;
        if(isSavingAllowed(id)){
            outputDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/PhotoQuiz");

            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File photoFile = null;
            try {
                photoFile = File.createTempFile(getUniqueFileName(), fileType, outputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.fromFile(photoFile);
        } else if(!isSavingAllowed(id)) {
            outputDir = mContext.getCacheDir();

            File photoFile = null;
            try {
                photoFile = File.createTempFile(getUniqueFileName(), fileType, outputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.fromFile(photoFile);
        } else {
            throw new IllegalStateException();
        }
    }

    private String getUniqueFileName(){
        String fileName = "IMG_";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return fileName + timeStamp;
    }

    private boolean isSavingAllowed(long id){
        return DatabaseManager.getInstance(mContext).isSaving(id);
    }

    private boolean isExternalStorageAvailable(){
        String state = Environment.getExternalStorageState();
        if(state == Environment.MEDIA_MOUNTED){
            return true;
        } else {
            return false;
        }
    }
}
