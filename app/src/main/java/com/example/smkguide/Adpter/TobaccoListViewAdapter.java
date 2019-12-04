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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TobaccoListViewAdapter extends BaseAdapter {
    private ArrayList<TobaccoVO> list = new ArrayList<TobaccoVO>();
    private ArrayList<TobaccoVO> searchList = new ArrayList<TobaccoVO>();
    private ArrayList<TobaccoVO> tempList = new ArrayList<TobaccoVO>();
    private Criteria cri = new Criteria();
    private final String URL="http://ggi4111.cafe24.com/display?fileName=";
    public TobaccoListViewAdapter(ArrayList<TobaccoVO>list){
        this.list = list;
        this.tempList.addAll(list);
        this.searchList.addAll(list);
        Log.i("list",list.toString());
    }
    public TobaccoListViewAdapter(Criteria cri){
        this.cri = cri;

    }
    public TobaccoListViewAdapter(ArrayList<TobaccoVO>list,Criteria cri){
        this.list = list;
        this.tempList.addAll(list);
        this.searchList.addAll(list);
        this.cri = cri;
        Log.i("list",list.toString());
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
        TextView grade = (TextView)convertView.findViewById(R.id.tvGrade);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        TobaccoVO listViewItem = list.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tobaccoName.setText(listViewItem.getTobaccoName());
        price.setText(String.valueOf(listViewItem.getPrice()));
        type.setText(listViewItem.getType().getName());
        nicotine.setText(String.valueOf(Math.ceil(listViewItem.getNicotine())));
        tar.setText(String.valueOf(listViewItem.getTar()));
        if(listViewItem.getGradeNum()==0)
            grade.setText("Non Grade");
        else
            grade.setText(String.valueOf(Math.ceil(((double)listViewItem.getGradeSum()/listViewItem.getGradeNum())*10)/10.0));
        Glide.with(convertView).load(URL+listViewItem.getAttach().getAttachFileName()).into(img);

        return convertView;
    }
    public void filter(Criteria cri){
        searchList.clear();
        searchList.addAll(tempList);
        filterName(cri.getKeyword());
        filterBrand(cri.getBId());
        filterCountry(cri.getNId());
        filterType(cri.getTId());
        filterOrder(cri.getOrder());
        notifyDataSetChanged();
    }
    public void filterName(String charText) {
        if(charText==null)
            return;
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(searchList);
        } else {
            for (TobaccoVO vo: searchList) {
                if (vo.getTobaccoName().toLowerCase().contains(charText)) {
                    list.add(vo);
                }
            }
            searchList.clear();
            searchList.addAll(list);
        }
    }
    public void filterBrand(Long brandId) {
        if(brandId==null)
            return;
        list.clear();
        if (brandId == 0) {
            list.addAll(searchList);
        } else {
            Log.d("filtering","brand");
            for (TobaccoVO vo: searchList) {
                if (vo.getBrand().getId()==brandId) {
                    list.add(vo);
                    Log.d("filtering",vo.toString());
                }
            }
            searchList.clear();
            searchList.addAll(list);
        }
    }
    public void filterType(Long typeId) {
        if(typeId==null)
            return;
        list.clear();
        if (typeId == 0) {
            list.addAll(searchList);
        } else {
            for (TobaccoVO vo: searchList) {
                if (vo.getType().getId()==typeId) {
                    list.add(vo);
                }
            }
            searchList.clear();
            searchList.addAll(list);
        }
    }
    public void filterCountry(Long countryId) {
        if(countryId==null)
            return;
        list.clear();
        if (countryId == 0) {
            list.addAll(searchList);
        } else {
            for (TobaccoVO vo: searchList) {
                if (vo.getCountry().getId()==countryId) {
                    list.add(vo);
                }
            }
            searchList.clear();
            searchList.addAll(list);
        }
    }
    public void filterOrder(String order) {
        if(order==null)
            return;
        if(order.equals("MOST")){
            Collections.sort(list, new Comparator<TobaccoVO>() {
                @Override
                public int compare(TobaccoVO s1, TobaccoVO s2) {
                    if (s1.getCommentCnt() < s2.getCommentCnt()) {
                        return 1;
                    } else if (s1.getCommentCnt() > s2.getCommentCnt()) {
                        return -1;
                    }
                    return 0;
                }
            });
        }else if(order.equals("BEST")){
            Collections.sort(list, new Comparator<TobaccoVO>() {
                @Override
                public int compare(TobaccoVO s1, TobaccoVO s2) {
                    if (s1.getGradeNum()==0 & s2.getGradeNum()==0)
                        return 0;
                    else if (s1.getGradeNum()==0 & s2.getGradeNum()!=0)
                        return 1;
                    else if (s1.getGradeNum()!=0 & s2.getGradeNum()==0)
                        return -1;
                    else if (s1.getGradeSum()/s1.getGradeNum() < s2.getGradeSum()/s2.getGradeNum()) {
                        return 1;
                    } else if (s1.getGradeSum()/s1.getGradeNum() > s2.getGradeSum()/s2.getGradeNum()) {
                        return -1;
                    }
                    return 0;
                }
            });
        }
    }

}
