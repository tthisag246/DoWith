package com.example.dowith;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class studyjoinadapter extends BaseAdapter {
    Context con;
    int layout;
    ArrayList<studyjoinitem> ar;
    LayoutInflater inflater;

    //생성자
    public studyjoinadapter(Context con, int layout, ArrayList<studyjoinitem> ar) {
        this.con = con;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.ar = ar;
    }

    //get 메서드
    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return ar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }

        TextView studyTitle = (TextView) view.findViewById(R.id.studyItemTitle);

        studyTitle.setText(ar.get(i).getItem_title());

        return view;
    }
}
