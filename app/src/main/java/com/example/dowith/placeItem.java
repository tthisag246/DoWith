package com.example.dowith;

// 공간 아이템 클래스 선언
public class placeItem {
    // 필드 선언
    String placeId, placeName, placeDesc, pCateId;

    // 접근자
    public placeItem get() { return this; }
    public String getItem_id() { return placeId; }
    public String getItem_name() { return placeName; }
    public String getItem_desc() { return placeDesc; }
    public String getItem_cate() { return pCateId; }

    // 설정자
    public placeItem set() { return this; }
    public void setItem_id(String placeId) { this.placeId = placeId; }
    public void setItem_name(String placeName) { this.placeName = placeName; }
    public void setItem_desc(String placeDesc) { this.placeDesc = placeDesc; }
    public void setItem_cate(String pCateId) { this.pCateId = pCateId; }
}