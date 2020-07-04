package com.yassinetaboubi.android.magazines;

public class OurData {
    String username;
    String score;
    public OurData (){

    }
    public OurData (String username,String score){
        this.username=username;
        this.score=score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }
}
