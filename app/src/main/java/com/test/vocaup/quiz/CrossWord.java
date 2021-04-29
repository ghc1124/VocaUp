package com.test.vocaup.quiz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.R;

public class CrossWord extends AppCompatActivity {

    Button button[] = new Button[100];

    String test = "test";
    int x1 = 5;
    int y1 = 5;
    int len = test.length();
    boolean tst = true;

    Integer[] Rid_button = {
      R.id.bt00,R.id.bt01,,R.id.bt02,R.id.bt03,R.id.bt04,R.id.bt05,R.id.bt06,R.id.bt07,R.id.bt08,R.id.bt09,R.id.bt10,R.id.bt11,R.id.bt12
            ,R.id.bt13,R.id.bt14,R.id.bt15,R.id.bt16,R.id.bt17,R.id.bt18,R.id.bt19,R.id.bt20,R.id.bt21,R.id.bt22,R.id.bt23,R.id.bt24,R.id.bt25
            ,R.id.bt26,R.id.bt27,R.id.bt28,R.id.bt29,R.id.bt30,R.id.bt31,R.id.bt32,R.id.bt33,R.id.bt34,R.id.bt35,R.id.bt36,R.id.bt37,R.id.bt38,R.id.bt39,R.id.bt40
            ,R.id.bt41,R.id.bt42,R.id.bt43,R.id.bt44,R.id.bt45,R.id.bt46,R.id.bt47,R.id.bt48,R.id.bt49,R.id.bt50,R.id.bt51,R.id.bt52,R.id.bt53,R.id.bt54,R.id.bt55
            ,R.id.bt56,R.id.bt57,R.id.bt58,R.id.bt59,R.id.bt60,R.id.bt61,R.id.bt62,R.id.bt63,R.id.bt64,R.id.bt65,R.id.bt66,R.id.bt67,R.id.bt68,R.id.bt69,R.id.bt70
            ,R.id.bt71,R.id.bt72,R.id.bt73,R.id.bt74,R.id.bt75,R.id.bt76,R.id.bt77,R.id.bt78,R.id.bt79,R.id.bt80,R.id.bt81,R.id.bt82,R.id.bt83,R.id.bt84,R.id.bt85
            ,R.id.bt86,R.id.bt87,R.id.bt88,R.id.bt89,R.id.bt90,R.id.bt91,R.id.bt92,R.id.bt93,R.id.bt94,R.id.bt95,R.id.bt96,R.id.bt97,R.id.bt98,R.id.bt99
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_word);

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                button[10*i+j] = (Button)findViewById(Rid_button[10*i+j]);
            }
        }


        button[x1*10 + y1].setText(test.charAt(0));
        for(int i=0; i<len ;i++){
            if (tst = true){
                button[x1*10 + y1 + i + 1].setText(test.charAt(i+1));
            }
            else {
                button[(x1+i+1)*10+y1].setText(test.charAt(i+1));
            }
        }
    }
}



