package com.example.smkguide.Adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;

import java.util.ArrayList;

public class CommentListViewAdapter  extends BaseAdapter {
    Context context;
    ArrayList<CommentVO> list;
    public CommentListViewAdapter(Context context, ArrayList<CommentVO>list){
        this.context = context;
        this.list = list;
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
        View view = View.inflate(context, R.layout.comment_listview_item,null);
        TextView content = (TextView) view.findViewById(R.id.tvContent);
        TextView date = (TextView) view.findViewById(R.id.tvDate);
        TextView email = (TextView) view.findViewById(R.id.tvEmail);
        content.setText(list.get(position).getContent());
        date.setText(list.get(position).getCdate());
        email.setText(list.get(position).getMember().getEmail());
        return view;
    }
}
