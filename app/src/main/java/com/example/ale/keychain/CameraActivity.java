package com.example.ale.keychain;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int ACTIVITY_SELECT_IMAGE = 1234;
    private ImageView imageCamera;
    private ImageView imageGallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        this.imageCamera = (ImageView)this.findViewById(R.id.imageCamera);
        this.imageGallery = (ImageView)findViewById(R.id.imageGallery);
        Button cameraButton = (Button) this.findViewById(R.id.buttonCamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button galleryButton = (Button) this.findViewById(R.id.buttonGallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageCamera.setImageBitmap(photo);
        }else if ( requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK ){
            Uri selectedImage = data.getData();
            imageGallery.setImageURI(selectedImage);

        }
    }


}
