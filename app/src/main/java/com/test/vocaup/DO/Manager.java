package com.test.vocaup.DO;

import java.io.Serializable;

public class Manager implements Serializable {
    private String Token; // 사용자 토큰
    private int Level = 1; // 레벨
    private String UserWord; // 나만의 단어
    private String ProblemSet; // 풀었던 문제 Set
    private int blank_spelling = -1; // 빈칸 채우기
    private int mean_spelling = -1; // 뜻 보고 단어 맞추기
    private int spelling_mean = -1; // 단어 보고 뜻 맞추기
    private int spelling_mean_link = -1; // 단어 뜻 연결하기
    private int spelling_sort = -1; // 빈칸 채우기
    private int pron_mean = -1; // 발음 듣고 뜻 맞추기
    private int recap = -1; // 복습

    public Manager(String token) {
        Token = token;
    }

    public Boolean allClear() {
        if(blank_spelling == 1 &&
            mean_spelling == 1 &&
            spelling_mean == 1 &&
            spelling_mean_link == 1 &&
            spelling_sort == 1 &&
            pron_mean == 1 &&
            recap == 5)

            return true;
        else
            return false;
    }

    public void reset() {
        blank_spelling = 0;
        mean_spelling = 0;
        spelling_mean = 0;
        spelling_mean_link = 0;
        spelling_sort = 0;
        pron_mean = 0;
        recap = 0;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getUserWord() {
        return UserWord;
    }

    public void setUserWord(String userWord) {
        UserWord = userWord;
    }

    public String getProblemSet() {
        return ProblemSet;
    }

    public void setProblemSet(String problemSet) {
        ProblemSet = problemSet;
    }

    public int getBlank_spelling() {
        return blank_spelling;
    }

    public void setBlank_spelling(int blank_spelling) {
        this.blank_spelling = blank_spelling;
    }

    public int getMean_spelling() {
        return mean_spelling;
    }

    public void setMean_spelling(int mean_spelling) {
        this.mean_spelling = mean_spelling;
    }

    public int getSpelling_mean() {
        return spelling_mean;
    }

    public void setSpelling_mean(int spelling_mean) {
        this.spelling_mean = spelling_mean;
    }

    public int getSpelling_mean_link() {
        return spelling_mean_link;
    }

    public void setSpelling_mean_link(int spelling_mean_link) {
        this.spelling_mean_link = spelling_mean_link;
    }

    public int getSpelling_sort() {
        return spelling_sort;
    }

    public void setSpelling_sort(int spelling_sort) {
        this.spelling_sort = spelling_sort;
    }

    public int getPron_mean() {
        return pron_mean;
    }

    public void setPron_mean(int pron_mean) {
        this.pron_mean = pron_mean;
    }

    public int getRecap() {
        return recap;
    }

    public void setRecap(int recap) {
        this.recap = recap;
    }
}
