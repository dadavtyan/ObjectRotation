package com.davtyan.objectrotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA = "";
    private LinearLayout lineTab;
    private MySurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        surfaceView = findViewById(R.id.my_dragView);
        lineTab = findViewById(R.id.tab);
        findViewById(R.id.select_figure).setOnClickListener(this);
        findViewById(R.id.select_text).setOnClickListener(this);
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
        }
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
