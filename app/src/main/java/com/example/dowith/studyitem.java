package com.example.dowith;

public class studyitem {
    String studyId, studyName, studyDesc;

    public studyitem get() { return this; }
    public String getItem_title() { return studyName; }

    public studyitem set() { return this; }
    public void setItem_id(String studyId) { this.studyId = studyId; }
    public void setItem_name(String studyName) { this.studyName = studyName; }
    public void setItem_desc(String studyDesc) { this.studyDesc = studyDesc; }
}
