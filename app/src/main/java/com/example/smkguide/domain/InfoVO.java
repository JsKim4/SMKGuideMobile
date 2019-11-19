package com.example.smkguide.domain;

public class InfoVO {
    String notice;
    String content;
    String name;
    String date;

    public InfoVO(String notice, String name, String date) {
        this.notice = notice;
        this.content = content;
        this.name = name;
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice=notice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }
}
