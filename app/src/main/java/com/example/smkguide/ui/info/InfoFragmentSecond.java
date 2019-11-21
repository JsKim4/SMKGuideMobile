package com.example.smkguide.ui.info;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smkguide.Adpter.InfoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.InfoVO;

import java.util.ArrayList;
import java.util.List;

public class InfoFragmentSecond extends Fragment {

    public static InfoFragmentSecond newInstance(){
        return new InfoFragmentSecond();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_second, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<InfoVO> list = new ArrayList<InfoVO>();
        list.add(new InfoVO("1","뉴스 111111111111111111111111111111111","내용1","관리자","2019-11-01","2"));
        list.add(new InfoVO("2","뉴스 2222222222222222222222222","내용2","관리자","2019-11-02","2"));
        list.add(new InfoVO("3","뉴스 333333333333333333333333333","내용3","관리자","2019-11-03","2"));
        list.add(new InfoVO("4","뉴스 44444444444","내용4","관리자","2019-11-04","2"));
        list.add(new InfoVO("5","뉴스 555","내용5","관리자","2019-11-05","2"));
        list.add(new InfoVO("6","뉴스 6666666666666666666666","내용6","관리자","2019-11-06","2"));
        list.add(new InfoVO("7","뉴스 7","내용7","관리자","2019-11-07","2"));
        list.add(new InfoVO("8","뉴스 8","내용8","관리자","2019-11-08","2"));
        list.add(new InfoVO("9","뉴스 9","내용9","관리자","2019-11-09","2"));
        list.add(new InfoVO("10","뉴스 10","내용10","관리자","2019-11-10","2"));
        list.add(new InfoVO("11","뉴스 11","내용11","관리자","2019-11-11","2"));
        list.add(new InfoVO("12","뉴스 12","내용12","관리자","2019-11-12","2"));
        ListAdapter adapter = new InfoListViewAdapter(getContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String title, content, name, date;
                InfoVO info = (InfoVO) parent.getAdapter().getItem(position);
                InfoDialog customDialog = new InfoDialog(getContext());
                title=info.getTitle();
                content=info.getContent();
                name=info.getName();
                date=info.getDate();
                customDialog.call(title, content, name, date);
            }
        });

        return view;
    }
}