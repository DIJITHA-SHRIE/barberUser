package com.barberapp.barberuser.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.barberapp.barberuser.R;

import java.util.Random;



public class TextDrawable extends Drawable {
    private TextPaint textPaint;
    private StaticLayout textLayout;
    private Paint background;
    private Paint border;
    private float textLeft,textWidth,textHeight;
    String arrBgColors[]=new String[]{"#27b6e4","#27b6e4"};
    private Context context;
    public TextDrawable(String text,int textSize,Context context1){
        this.context = context1;
        Random random=new Random();
        int pos=random.nextInt(arrBgColors.length-1)+1;
        background=new Paint();
        if(pos>=arrBgColors.length){
            pos=pos-1;
        }
        background.setColor(Color.parseColor(arrBgColors[pos]));
        background.setAntiAlias(true);
        background.setStyle(Paint.Style.FILL);
        border=new Paint();
        border.setColor(context.getResources().getColor(R.color.colorPrimary));
        border.setAntiAlias(true);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(0);

        textPaint=new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textLayout=new StaticLayout(text.substring(0,1).toUpperCase(),textPaint,100, Layout.Alignment.ALIGN_CENTER,1.0f,1.0f,false);
        textLeft = textLayout.getLineLeft(0);
        textWidth = textLayout.getLineWidth(0);
        textHeight = textLayout.getLineBottom(0);

    }
    @Override
    public void draw(Canvas canvas) {
        Rect bound=getBounds();
        int size=bound.width();
        canvas.save();
        canvas.translate(bound.left,bound.top);
        canvas.drawCircle(size/2,size/2,size/2,background);
        RectF rect = new RectF(getBounds());
        rect.inset(2, 2);
        canvas.drawOval(rect, border);
        canvas.translate((size-textWidth)/2-textLeft,(size-textHeight)/2);
        textLayout.draw(canvas);
        canvas.restore();

    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

}
