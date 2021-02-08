package com.test.vocaup.DO;

import java.io.Serializable;
import java.util.ArrayList;

public class Problem implements Serializable {
    private ArrayList<String> select=new ArrayList<String>();
    private int answer;
    private int choice;
    private String show;
    private boolean check;

    public Problem(){
        check=false;
    }
    public int getChoice(){return choice;}
    public String getSelect(int i) {
        return select.get(i);
    }
    public int getSelectSize(){
        return select.size();
    }

    public int getAnswer() {
        return answer;
    }

    public String getShow() {
        return show;
    }

    public boolean isCheck() {
        return check;
    }

    public void setSelect(ArrayList<String> select) {
        this.select = select;
    }
    public void addSelect(String select){
        this.select.add(select);
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }
    public void setChoice(int choice){this.choice = choice;}
    public void setShow(String show) {
        this.show = show;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
