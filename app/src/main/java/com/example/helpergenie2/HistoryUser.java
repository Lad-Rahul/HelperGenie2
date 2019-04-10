package com.example.helpergenie2;

public class HistoryUser {
    private String name;
    private String proffession;
    private String time;

    public HistoryUser(){

    }

    public HistoryUser(String name,String proffession,String time){
        this.name=name;
        this.proffession=proffession;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProffession() {
        return proffession;
    }

    public void setProffession(String proffession) {
        this.proffession = proffession;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
