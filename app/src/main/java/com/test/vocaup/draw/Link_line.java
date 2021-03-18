package com.test.vocaup.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Link_line extends View {
    int [] x = new int[8];
    int [] y = new int[8];
    int []answer = new int[4];
    public Link_line(Context context, AttributeSet attrs){
        super(context,attrs);
        for(int i=0;i<8;i++){
            x[i]=(i/4)*300;
            y[i]=(i%4)*300+100;
        }
        answer[0] = answer[1] = answer[2] = answer[3] = -1;
    }

    public Link_line(Context context) {
        super(context);
        for(int i=0;i<8;i++){
            x[i]=(i/4)*300;
            y[i]=(i%4)*300+100;
        }
        answer[0] = answer[1] = answer[2] = answer[3] = -1;
    }

    public void setX(int[] x) {
        this.x = x;
    }

    public void setY(int[] y) {
        this.y = y;
    }

    public void update(int[] check_answer){
        for(int i=0;i<4;i++){
            answer[i]=check_answer[i];
        }
        invalidate();
    }
    protected void onDraw(Canvas canvas){
        Paint paint =new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        for(int i=0;i<answer.length;i++){
            if(answer[i]!=-1){
                canvas.drawLine(x[i],y[i],x[4+answer[i]],y[4+answer[i]],paint);
            }
        }
    }


}
