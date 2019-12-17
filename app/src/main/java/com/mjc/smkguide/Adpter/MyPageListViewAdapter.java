package com.mjc.smkguide.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.CommentVO;
import com.mjc.smkguide.domain.GradeVO;
import com.mjc.smkguide.domain.SmokelogVO;

import java.util.ArrayList;

public class MyPageListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList list;
    String keyword;
    public MyPageListViewAdapter(Context context, ArrayList list,String keyword){
        this.context = context;
        this.list = list;
        this.keyword = keyword;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.mypage_listview_item,null);
        if(keyword.equals("smokelog")){
            TextView tobaccoName = view.findViewById(R.id.tvMy1);
            SmokelogVO vo = (SmokelogVO)list.get(position);
            tobaccoName.setText(vo.getTobacco().getTobaccoName());
            TextView cdate = view.findViewById(R.id.tvMy2);
            cdate.setText(vo.getCdate());
        }
        if(keyword.equals("comment")){
            TextView tobaccoName = view.findViewById(R.id.tvMy1);
            CommentVO vo = (CommentVO) list.get(position);
            tobaccoName.setText(vo.getTobacco().getTobaccoName());
            TextView cdate = view.findViewById(R.id.tvMy2);
            cdate.setText(vo.getCdate());
            cdate.setTextColor(Color.LTGRAY);
            TextView content = view.findViewById(R.id.tvMy3);
            content.setText(vo.getContent());
            content.setVisibility(View.VISIBLE);
        }
        if(keyword.equals("grade")){
            TextView tobaccoName = view.findViewById(R.id.tvMy1);
            GradeVO vo = (GradeVO)list.get(position);
            tobaccoName.setText(vo.getTobacco().getTobaccoName());
            TextView score = view.findViewById(R.id.tvMy2);
            score.setText(String.valueOf(vo.getScore()));
        }
        return view;
    }
}
