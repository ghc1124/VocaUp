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
    int []answer = new  int[4];
    public Link_line(Context context, AttributeSet attrs){
        super(context);
    }

    public Link_line(Context context) {
        super(context);
        for(int i=0;i<8;i++){
            x[i]=(i/4)*50;
            y[i]=(i%4)*50;
        }
        answer[0] = answer[1] = answer[2] = answer[3] = -1;
        System.out.println("이상하네"+answer[0]+answer[1]+answer[2]+answer[3]);
    }
//    public void setting(int num0,int num1,int num2,int num3){
//        answer[0]=num0;
//        answer[1]=num1;
//        answer[2]=num2;
//        answer[3]=num3;
//    }
    public void update(int a,int b){
        System.out.println("abc"+a+b);
        //invalidate();
    }
    protected void onDraw(Canvas canvas){
        Paint paint =new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        System.out.println("에라이 :" + answer.length);
        for(int i=0;i<answer.length;i++){
            if(answer[i]!=-1){
                canvas.drawLine(x[i],y[i],x[4+answer[i]],y[4+answer[i]],paint);
            }
        }
    }


}
