package com.example.smkguide.ListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smkguide.R;
import com.example.smkguide.domain.InfoVO;

import java.util.List;

public class InfoListViewAdapter extends BaseAdapter {
    private Context context;
    private List<InfoVO> list;

    public InfoListViewAdapter(Context context, List<InfoVO> list) {
        this.context=context;
        this.list=list;
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
        View view = View.inflate(context, R.layout.fragment_info_list,null);
        TextView notice = (TextView) view.findViewById(R.id.notice);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);
        notice.setText(list.get(position).getNotice());
        name.setText(list.get(position).getName());
        date.setText(list.get(position).getDate());
        view.setTag(list.get(position).getNotice());
        return view;
    }
}
