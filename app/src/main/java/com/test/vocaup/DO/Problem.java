package com.test.vocaup.DO;

import java.io.Serializable;
import java.util.ArrayList;

public class Problem implements Serializable {
    private ArrayList<String> select=new ArrayList<String>();
    private ArrayList<Integer> answer_array=new ArrayList<Integer>();
    private ArrayList<String> show_array=new ArrayList<String>();

    private int answer;
    private int choice;
    private String show;
    private String sentence;
    private boolean check;

    public Problem(){
        check=false;
    }
    public int getChoice(){return choice;}
    public String getSelect(int i) {
        return select.get(i);
    }
    public int getAnswer_array(int i) {
        return answer_array.get(i);
    }
    public String getShow_array(int i) {
        return show_array.get(i);
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

    public void setSelect(ArrayList<String> select) {
        this.select = select;
    }
    public void setAnswer_array(ArrayList<Integer> answer_array) { this.answer_array = answer_array; }
    public void setShow_array(ArrayList<String> show_array) {
        this.show_array = show_array;
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
    public void setSentence(String sentence) { this.sentence = sentence;}

    public boolean isCheck() {
        return check;
    }
    public void addSelect(String select){
        this.select.add(select);
    }
    public void addAnswer_array(int answer_array){
        this.answer_array.add(answer_array);
    }
    public void addShow_array(String addShow_array){
        this.select.add(addShow_array);
    }
}
