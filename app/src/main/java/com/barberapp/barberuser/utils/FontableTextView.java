package com.barberapp.barberuser.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class FontableTextView extends android.support.v7.widget.AppCompatTextView {
    public FontableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontableTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "textfontable.ttf");
            setTypeface(tf);
        }
    }

}
