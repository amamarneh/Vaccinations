package com.amarnehsoft.vaccinations.controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.database.db2.DBHelper2;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alaam on 2/11/2018.
 */

public class DBController {
    private DBListener listener;
    private static   String outFileNameDB = Environment.getExternalStorageDirectory()+"/"+ DBHelper2.DATABASE_NAME +".db";
    private ProgressDialog progress;

    public DBController() {
        listener = null;
    }

    public    void ExportDB(Context mContext){
//        if(mMODE == MODE_PUBLIC) {
            progress = new ProgressDialog(mContext);
            progress.setTitle(mContext.getString(R.string.exporting));
            progress.show();
//        }
        Uri file = Uri.fromFile(mContext.getDatabasePath(DBHelper2.DATABASE_NAME));
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        String key = DBHelper2.DATABASE_NAME;
        String location = "dbs/" + key;
        StorageReference riversRef = storageRef.child(location);

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Log.d("Amarneh","Exported!");

                        Map<String,Object> map = new HashMap<>();
                        map.put("version", "1");
                        map.put("date", ServerValue.TIMESTAMP);
                        map.put("id",taskSnapshot.getMetadata().getName());

                        FirebaseDatabase.getInstance().getReference().child("db").setValue(map);


                        if(progress != null)
                            progress.dismiss();
//
//                        if(mMODE == MODE_PUBLIC)
//                            Alerts.MyAlert(mContext,"Done","Exporting succeed").show();



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
        exception.printStackTrace();
                        if(progress != null)
                            progress.dismiss();
                    }
                });

    }

    public  void check(final Context context , final DBListener listener){
        this.listener=listener;
        progress = new ProgressDialog(context);
        progress.setTitle(context.getString(R.string.importing));
        progress.setCancelable(false);
        progress.show();

        final SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("db").child("date");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = dataSnapshot.getValue().toString();

                String oldDate = sp.getString("db_date","");
                if(!date.equals(oldDate)){
                    ImportDB(context);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("db_date",date);
                    editor.apply();
                }else{
                    if(progress != null)
                        progress.dismiss();

                    if(listener != null)
                        listener.onComplete(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(progress != null)
                    progress.dismiss();

                if(listener != null)
                    listener.onComplete(false);
            }
        });
    }

    public  void ImportDB( final Context mContext) {
//        if(busy)
//            return;
//        busy = true;
//        if(mMODE == MODE_PUBLIC) {

//        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("db").child("id");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String id = dataSnapshot.getValue().toString();

                    DownloadDB(id, mContext);
                } catch (Exception e) {

                    e.printStackTrace();
                    if(listener != null)
                        listener.onComplete(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(progress != null)
                    progress.dismiss();

                if(listener != null)
                    listener.onComplete(false);
            }
        });
    }

    private  void DownloadDB(String id, final Context mContext){
        File localFile = new File(outFileNameDB);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("dbs/"+id);
        storageReference.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        try {
                            MoveDB(mContext);
                        }catch (Exception e){e.printStackTrace();
                            if(progress != null)
                                progress.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if(progress != null)
                    progress.dismiss();

                if(listener != null)
                    listener.onComplete(false);
            }
        });
    }
    private  void MoveDB(Context mContext) throws IOException {
        copy(new File(outFileNameDB),mContext.getDatabasePath(DBHelper2.DATABASE_NAME));
    }
    private  void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst,false);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.flush();
                Log.d("Amarneh","imported");


            } finally {
                out.close();
            }
        } finally {
            if(progress != null)
                progress.dismiss();
            in.close();

            if(listener != null)
                listener.onComplete(true);
            else
                Log.d("tag","null listener");

        }
    }
    public interface DBListener{
        void onComplete(boolean success);
    }

}
