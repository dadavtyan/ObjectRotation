package com.davtyan.objectrotation;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

/**
 * Created by COMP on 05.02.2018.
 */

public class TextPopupView extends PopupWindow implements View.OnClickListener {

    private MainActivity context;
    private View view;
    private EditText editText;
    private String text;


    public TextPopupView(MainActivity context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_text, null);

        intiData(view);

        this.setContentView(this.view);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
    }

    private void intiData(View view) {
        editText = view.findViewById(R.id.edit_text);
        view.findViewById(R.id.chanel).setOnClickListener(this);
        view.findViewById(R.id.add_text).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chanel:
                dismiss();
                break;
            case R.id.add_text:
                text = editText.getText().toString();
                MySurfaceView.TEXT = text;
                MyDrawView.TEXT = text;
                MainActivity.EXTRA = "draw_text";
                dismiss();
                break;

        }
    }

}
