package com.example.dowith;

public class friendItem {
    //친구목록을 구성하는 항목들을 String 변수로 생성
    String friendTitle, friendType, friendTime, friendMemo;

    //생성자
    public friendItem(String friendTitle) {
        this.friendTitle = friendTitle;
        this.friendType = friendType;
    }

    //get 메서드
    public com.example.dowith.friendItem get() { return this; }
    public String getItem_title() { return friendTitle; }
    public String getItem_type() { return friendType; }
}
