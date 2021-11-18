package com.example.dowith;

//일정 객체 생성
public class listItem {
    //일정을 구성하는 항목들을 String 변수로 생성
    String listTitle, listType, listTime, listMemo;

    //생성자
    public listItem(String listTitle, String listType, String listTime, String listMemo) {
        this.listTitle = listTitle;
        this.listType = listType;
        this.listTime = listTime;
        this.listMemo = listMemo;
    }

    //get 메서드
    public listItem get() { return this; }
    public String getItem_title() { return listTitle; }
    public String getItem_type() { return listType; }
    public String getItem_time() { return listTime; }
    public String getItem_memo() { return listMemo; }
}
