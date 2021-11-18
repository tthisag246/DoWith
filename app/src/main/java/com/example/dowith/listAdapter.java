package com.example.dowith;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//커스텀 리스트뷰 listAdaptr 생성
public class listAdapter extends BaseAdapter {
    Context con;
    int layout;
    ArrayList<listItem> ar;
    LayoutInflater inflater;

    //생성자
    public listAdapter(Context con, int layout, ArrayList<listItem> ar) {
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

        TextView listItemTitle = (TextView) view.findViewById(R.id.listItemTitle);
        TextView listItemType = (TextView) view.findViewById(R.id.listItemType);
        TextView listItemTime = (TextView) view.findViewById(R.id.listItemTime);
        TextView listItemMemo = (TextView) view.findViewById(R.id.listItemMemo);

        listItemTitle.setText(ar.get(i).getItem_title());
        listItemType.setText(ar.get(i).getItem_type());
        listItemTime.setText(ar.get(i).getItem_time());
        listItemMemo.setText(ar.get(i).getItem_memo());

        return view;
    }
}
