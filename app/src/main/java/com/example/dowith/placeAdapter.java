package com.example.dowith;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// 커스텀 리스트뷰 클래스 선언
public class placeAdapter extends BaseAdapter {
    // 필드 선언
    Context con;
    int layout;
    ArrayList<placeItem> ar;
    LayoutInflater inflater;

    // 생성자
    public placeAdapter(Context con, int layout, ArrayList<placeItem> ar) {
        this.con = con;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.ar = ar;
    }

    // 접근자
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

        TextView placeItemTitle = (TextView) view.findViewById(R.id.placeItemTitle);
        TextView placeItemType = (TextView) view.findViewById(R.id.placeItemType);

        placeItemTitle.setText(ar.get(i).getItem_name());
        placeItemType.setText(ar.get(i).getItem_cate());

        return view;
    }
}