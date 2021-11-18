package com.example.dowith;

//공간 객체 생성
public class placeItem {
    //공간을 구성하는 항목들을 String 변수로 생성
    String placeTitle, placeType, placeTime, placeMemo;

    //생성자
    public placeItem(String placeTitle, String placeType) {
        this.placeTitle = placeTitle;
        this.placeType = placeType;
    }

    //get 메서드
    public placeItem get() { return this; }
    public String getItem_title() { return placeTitle; }
    public String getItem_type() { return placeType; }
}