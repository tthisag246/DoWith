package com.example.dowith;

public class studyitem {
    //공간을 구성하는 항목들을 String 변수로 생성
    String studyId, studyName, studyDesc;

    //get 메서드
    public studyitem get() { return this; }
    public String getItem_title() { return studyName; }
    public String getItem_desc() { return studyDesc; }

    //set 메서드
    public studyitem set() { return this; }
    public void setItem_id(String studyId) { this.studyId = studyId; }
    public void setItem_name(String studyName) { this.studyName = studyName; }
    public void setItem_desc(String studyDesc) { this.studyDesc = studyDesc; }
}