package com.example.smkguide.Adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;

import java.util.ArrayList;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TobaccoListViewAdapter extends BaseAdapter {
    private ArrayList<TobaccoVO> list = new ArrayList<TobaccoVO>();
    private Criteria cri = new Criteria();
    private final String URL="http://ggi4111.cafe24.com/display?fileName=";
    public TobaccoListViewAdapter(ArrayList<TobaccoVO>list){
        this.list = list;
        Log.i("list",list.toString());
    }
    public TobaccoListViewAdapter(Criteria cri){
        this.cri = cri;

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
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tobacco_listview_item, parent, false);
        }


        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView tobaccoName = (TextView)convertView.findViewById(R.id.tvTobaccoName);
        TextView price = (TextView)convertView.findViewById(R.id.tvPrice);
        TextView type = (TextView)convertView.findViewById(R.id.tvType);
        TextView nicotine = (TextView)convertView.findViewById(R.id.tvNicotine);
        TextView tar = (TextView)convertView.findViewById(R.id.tvTar);
        ImageView img = (ImageView)convertView.findViewById(R.id.imgTobacco);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        TobaccoVO listViewItem = list.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tobaccoName.setText(listViewItem.getTobaccoName());
        price.setText(String.valueOf(listViewItem.getPrice()));
        type.setText(listViewItem.getType().getName());
        nicotine.setText(String.valueOf(listViewItem.getNicotine()));
        tar.setText(String.valueOf(listViewItem.getTar()));
        Log.d("url : ",URL+listViewItem);
        Glide.with(convertView).load(URL+listViewItem.getAttach().getAttachFileName()).into(img);

        return convertView;
    }

}
