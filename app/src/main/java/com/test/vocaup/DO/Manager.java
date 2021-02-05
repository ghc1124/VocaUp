package com.test.vocaup.DO;

public class Manager {
    private String Token; // 사용자 토큰
    private int Level = 0; // 레벨
    private double Exp = 0.f; // 경험치
    private String UserWord; // 나만의 단어
    private String Act; // 활동 체크
    private String ProblemSet; // 풀었던 문제 Set

    public Manager(String token) {
        Token = token;
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

    public double getExp() {
        return Exp;
    }

    public void setExp(double exp) {
        Exp = exp;
    }

    public String getUserWord() {
        return UserWord;
    }

    public void setUserWord(String userWord) {
        UserWord = userWord;
    }

    public String getAct() {
        return Act;
    }

    public void setAct(String act) {
        Act = act;
    }

    public String getProblemSet() {
        return ProblemSet;
    }

    public void setProblemSet(String problemSet) {
        ProblemSet = problemSet;
    }
}
