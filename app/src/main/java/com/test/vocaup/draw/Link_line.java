package com.test.vocaup.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Link_line extends View {
    int [] x;
    int [] y;
    int []answer;
    public Link_line(Context context) {
        super(context);
        x= new int[8];
        y= new int[8];
        answer = new  int[4];
        for(int i=0;i<8;i++){
            x[i]=(i/4)*50;
            y[i]=(i%4)*50;
        }
        answer[0] = answer[1] = answer[2] = answer[3] = -1;
    }
    public void update(int[] tmp_answer){
        for(int i=0;i<4;i++){
            answer[i]=tmp_answer[i];
        }
        invalidate();
    }
    protected void onDraw(Canvas canvas){
        Paint paint =new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

        for(int i=0;i<4;i++){
            if(answer[i]!=-1){
                canvas.drawLine(x[i],y[i],x[4+answer[i]],y[4+answer[i]],paint);
            }
        }
    }


}
