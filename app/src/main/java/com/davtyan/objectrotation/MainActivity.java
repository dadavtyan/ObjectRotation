package com.davtyan.objectrotation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA = "";
    private LinearLayout lineTab;
    private final int GALLERY_RESULT = 1;
    private MySurfaceView mySurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        lineTab = findViewById(R.id.tab);
        mySurfaceView = findViewById(R.id.my_dragView);
        findViewById(R.id.select_figure).setOnClickListener(this);
        findViewById(R.id.select_text).setOnClickListener(this);
        findViewById(R.id.select_image).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_figure:
                showFigure();
                break;
            case R.id.select_text:
                showEditText();
                break;
            case R.id.select_image:
                startMediaStore();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_RESULT:
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        if (bitmap != null){
                            EXTRA = "select_image";
                            mySurfaceView.setBitmap(bitmap);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else {
            Toast.makeText(this, "Image not loaded.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMediaStore() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_RESULT);
    }

    private void showFigure() {
        int[] location = new int[2];
        lineTab.getLocationOnScreen(location);
        PopupFigure popupFigure = new PopupFigure(this);
        popupFigure.showAtLocation(lineTab, Gravity.NO_GRAVITY, location[0], location[1] + popupFigure.getHeight());
    }

    private void showEditText() {
        int[] location = new int[2];
        lineTab.getLocationOnScreen(location);
        TextPopupView textPopupView = new TextPopupView(this);
        textPopupView.showAtLocation(lineTab, Gravity.NO_GRAVITY, location[0], location[1] + textPopupView.getHeight());
    }

}
