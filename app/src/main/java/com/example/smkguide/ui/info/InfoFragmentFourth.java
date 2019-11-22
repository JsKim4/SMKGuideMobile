package com.example.smkguide.ui.info;

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

public class InfoFragmentFourth extends Fragment {

    public static InfoFragmentFourth newInstance(){
        return new InfoFragmentFourth();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_fourth, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<InfoVO> list = new ArrayList<InfoVO>();
        list.add(new InfoVO("1","테스트 입니다.","내용11\n111\n\11\n1\n1","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("2","테스트 2222222222 입니다.","내용2\n222\n2\n222\n22222\n2\n222\n2\n222\n22222\n2\n222\n2\n222\n22222\n2\n222\n2\n222\n22222\n2","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("3","테스트 3333333333333333333333333333333333","내용33333333333\n33\n3\n3333\n3333\n3\n333333333\n33\n3\n3333\n3333\n3\n333333333\n33\n3\n3333\n3333\n3","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("4","테스트 44444444444444444444444444444444444444444444444444444444444444","내용4","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("5","테스트 55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555","내용5","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("6","테스트 6","내용6","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("7","테스트 7","내용7","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("8","테스트 8","내용8","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("9","테스트 9","내용9","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("10","테스트 10","내용10","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("11","테스트 11","내용11","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("12","테스트 12","내용12","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("13","테스트 13","내용13","관리자","2019-11-22 AM 03:25","4"));
        list.add(new InfoVO("14","테스트 14","내용14","관리자","2019-11-22 AM 03:25","4"));

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
