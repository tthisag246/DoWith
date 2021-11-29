package com.example.dowith;

//공간 객체 생성
public class studyChatItem {
    //공간을 구성하는 항목들을 String 변수로 생성
    String sChatId, userId, sChatContent, sChatTime;

    //get 메서드
    public studyChatItem get() { return this; }
    public String getItem_id() { return sChatId; }
    public String getItem_u_id() { return userId; }
    public String getItem_content() { return sChatContent; }
    public String getItem_time() { return sChatTime; }

    //set 메서드
    public studyChatItem set() { return this; }
    public void setItem_id(String sChatId) { this.sChatId = sChatId; }
    public void setItem_u_id(String userId) { this.userId = userId; }
    public void setItem_content(String sChatContent) { this.sChatContent = sChatContent; }
    public void setItem_time(String sChatTime) { this.sChatTime = sChatTime; }
}