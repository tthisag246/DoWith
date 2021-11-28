package com.example.dowith;

//공간 객체 생성
public class placeItem {
    //공간을 구성하는 항목들을 String 변수로 생성
    String placeId, placeName, placeDesc, pCateId;

    //get 메서드
    public placeItem get() { return this; }
    public String getItem_id() { return placeId; }
    public String getItem_name() { return placeName; }
    public String getItem_desc() { return placeDesc; }
    public String getItem_cate() { return pCateId; }

    //set 메서드
    public placeItem set() { return this; }
    public void setItem_id(String placeId) { this.placeId = placeId; }
    public void setItem_name(String placeName) { this.placeName = placeName; }
    public void setItem_desc(String placeDesc) { this.placeDesc = placeDesc; }
    public void setItem_cate(String pCateId) { this.pCateId = pCateId; }
}