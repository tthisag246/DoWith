package com.example.dowith;

//일정 객체 생성
public class listItem {
    //일정을 구성하는 항목들을 String 변수로 생성
    String listID, listTitle, listType, listTime, listMemo;
    Boolean listDone;

    //get 메서드
    public listItem get() { return this; }
    public String getItem_ID() { return listID; }
    public String getItem_title() { return listTitle; }
    public String getItem_type() { return listType; }
    public String getItem_time() { return listTime; }
    public String getItem_memo() { return listMemo; }
    public Boolean getItem_done() { return listDone; }

    //set 메서드
    public void setItem_ID(String ID) { this.listID = ID; }
    public void setItem_title(String title) { this.listTitle = title; }
    public void setItem_type(String type) { this.listType = type; }
    public void setItem_time(String time) { this.listTime = time; }
    public void setItem_memo(String memo) { this.listMemo = memo; }
    public void setItem_done(Boolean done) { this.listDone = done; }
}
