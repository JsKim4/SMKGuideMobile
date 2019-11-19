package com.example.smkguide.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smkguide.R;
import com.example.smkguide.domain.ComponentVO;

import java.util.ArrayList;

public class ComponentSpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<ComponentVO> list;
    LayoutInflater inflater;
    public ComponentSpinnerAdapter(Context context, ArrayList<ComponentVO>list){
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.spinner_component_dropdown, parent, false);
        }

        //데이터세팅
        ComponentVO vo = list.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(vo.getName());

        return convertView;
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
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.spinner_component_nomal, parent, false);
        }

        if(list!=null){
            //데이터세팅
            ComponentVO vo = list.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(vo.getName());
        }

        return convertView;


    }
}
