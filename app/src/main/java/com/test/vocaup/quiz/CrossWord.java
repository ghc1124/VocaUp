package com.test.vocaup.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.vocaup.R;

public class CrossWord extends AppCompatActivity {
    EditText et[] = new EditText[100];
    EditText editText;
    Button ok_btn;

    String test = "test";
    int x1 = 5; //x좌표
    int y1 = 5; //y좌표
    int len = test.length(); //길이
    boolean tst = true; //가로면 true 세로면 false

    Integer[] Rid_editText = {
            R.id.et00,R.id.et01,R.id.et02,R.id.et03,R.id.et04,R.id.et05,R.id.et06,R.id.et07,R.id.et08,R.id.et09,R.id.et10,R.id.et11,R.id.et12
            ,R.id.et13,R.id.et14,R.id.et15,R.id.et16,R.id.et17,R.id.et18,R.id.et19,R.id.et20,R.id.et21,R.id.et22,R.id.et23,R.id.et24,R.id.et25
            ,R.id.et26,R.id.et27,R.id.et28,R.id.et29,R.id.et30,R.id.et31,R.id.et32,R.id.et33,R.id.et34,R.id.et35,R.id.et36,R.id.et37,R.id.et38,R.id.et39,R.id.et40
            ,R.id.et41,R.id.et42,R.id.et43,R.id.et44,R.id.et45,R.id.et46,R.id.et47,R.id.et48,R.id.et49,R.id.et50,R.id.et51,R.id.et52,R.id.et53,R.id.et54,R.id.et55
            ,R.id.et56,R.id.et57,R.id.et58,R.id.et59,R.id.et60,R.id.et61,R.id.et62,R.id.et63,R.id.et64,R.id.et65,R.id.et66,R.id.et67,R.id.et68,R.id.et69,R.id.et70
            ,R.id.et71,R.id.et72,R.id.et73,R.id.et74,R.id.et75,R.id.et76,R.id.et77,R.id.et78,R.id.et79,R.id.et80,R.id.et81,R.id.et82,R.id.et83,R.id.et84,R.id.et85
            ,R.id.et86,R.id.et87,R.id.et88,R.id.et89,R.id.et90,R.id.et91,R.id.et92,R.id.et93,R.id.et94,R.id.et95,R.id.et96,R.id.et97,R.id.et98,R.id.et99
    };


    class BtnOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view){

            String temp="";
            temp=editText.getText().toString();

            if (temp.length() == len){
                for (int i = 1; i < len; i++) {
                    if (tst = true) {
                        et[x1 * 10 + y1 + i].setText("" + temp.charAt(i));
                        et[x1 * 10 + y1 + i].setEnabled(true);
                    } else {
                        et[(x1 + i) * 10 + y1].setText("" + temp.charAt(i));
                        et[(x1 + i) * 10 + y1].setEnabled(true);
                    }
                }
            }
            else
                Toast.makeText(CrossWord.this, "길이가 달라요! ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_word);

        editText = findViewById(R.id.editText1);

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                et[10*i+j] = (EditText) findViewById(Rid_editText[10*i+j]);
            }
        }

        et[55] = findViewById(R.id.et55);

//        ok_btn = (Button)findViewById(R.id.ok_btn);
//        ok_btn.setOnClickListener(BtnOnClickListener);
    }
}