package com.test.vocaup.DO;

import java.io.Serializable;
import java.util.ArrayList;

public class Problem implements Serializable {
    private ArrayList<String> select = new ArrayList<String>();
    private int answer;
    private int choice;
    private String show;
    private String sentence;
    private int level;

    public Problem(){

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
    public String getSentence(){return sentence;}
    public int getLevel(){return level;}

    public void setSelect(ArrayList<String> select) {
        this.select = select;
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }
    public void setChoice(int choice){this.choice = choice;}
    public void setShow(String show) {
        this.show = show;
    }
    public void setLevel(int level){this.level = level;}

    public void setSentence(String sentence) { this.sentence = sentence;}
    public void addSelect(String select){
        this.select.add(select);
    }

}
