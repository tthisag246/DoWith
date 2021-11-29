package com.example.dowith;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//커스텀 리스트뷰 studyChatAdaptr 생성
public class studyChatAdapter extends BaseAdapter {
    Context con;
    int layout;
    ArrayList<studyChatItem> ar;
    LayoutInflater inflater;

    //생성자
    public studyChatAdapter(Context con, int layout, ArrayList<studyChatItem> ar) {
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

        TextView chatItemContent = (TextView) view.findViewById(R.id.chatItemContent);
        TextView chatItemTime = (TextView) view.findViewById(R.id.chatItemTime);

        chatItemContent.setText(ar.get(i).getItem_content());
        chatItemTime.setText(ar.get(i).getItem_time());

        return view;
    }
}