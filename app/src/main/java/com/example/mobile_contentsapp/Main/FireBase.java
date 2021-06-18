package com.example.mobile_contentsapp.Main;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.content.ContentValues.TAG;

public class FireBase {
    private static FireBase instance;

    public FireBase() {
    }

    public static FireBase getInstance(){
        if (instance == null){
            instance = new FireBase();
        }
        return instance;
    }

    public static void firebaseDownlode(Context context, String name, ImageView image){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
        StorageReference storageReference = storage.getReference();
        if (!name.isEmpty()){
            storageReference.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    public static void firebaseDownlode(Context context, String name, ImageButton image){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
        StorageReference storageReference = storage.getReference();
        if (!name.isEmpty()){
            storageReference.child(name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context).load(uri).into(image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }

    public static String firebaseUpload(Context context, Uri uri){
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mobile-contents-812ea.appspot.com");
        String imgname = "";
        if(uri != null){
            String uuid = UUID.randomUUID().toString();
            imgname = "images/"+uuid;
            StorageReference storageReference = storage.getReferenceFromUrl("gs://mobile-contents-812ea.appspot.com").child(imgname);

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "업로드에 실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return imgname;
    }
}
