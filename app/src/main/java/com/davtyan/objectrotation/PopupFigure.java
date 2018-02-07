package com.davtyan.objectrotation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by COMP on 05.02.2018.
 */

public class PopupFigure extends PopupWindow implements View.OnClickListener {

    private Context context;
    private View view;


    public PopupFigure(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_figure, null);

        view.findViewById(R.id.draw_point).setOnClickListener(this);
        view.findViewById(R.id.draw_line).setOnClickListener(this);
        view.findViewById(R.id.draw_circle).setOnClickListener(this);
        view.findViewById(R.id.draw_rect).setOnClickListener(this);

        this.setContentView(this.view);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.draw_point:
                MainActivity.EXTRA = "draw_point";
                dismiss();
                break;
            case R.id.draw_line:
                MainActivity.EXTRA = "draw_line";
                dismiss();
                break;
            case R.id.draw_circle:
                MainActivity.EXTRA = "draw_circle";
                dismiss();
                break;
            case R.id.draw_rect:
                MainActivity.EXTRA = "draw_rect";
                dismiss();
                break;
        }
    }
}
