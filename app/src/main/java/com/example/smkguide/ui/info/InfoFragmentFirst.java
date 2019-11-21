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

public class InfoFragmentFirst extends Fragment {

    public static InfoFragmentFirst newInstance(){
        return new InfoFragmentFirst();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_first, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<InfoVO> list = new ArrayList<InfoVO>();
        list.add(new InfoVO("1","공지사항 1","내용1111111\n1\n1\n1\n1\n11111\n1111111\n1\n111\n11\n1\n1\n1\n1\n1\n1\n1\n1\n\n1\n1\n11111111\n111111111111\n1\n11111111111111\n1111111111\n111111111\n111111111111111111111111","관리자","2019-11-01","1"));
        list.add(new InfoVO("2","공지사항 22222222222222222222222222222222222222222222222222222","내용2\n2\n2\n2\n2\n2\n222222222222","관리자","2019-11-02","1"));
        list.add(new InfoVO("3","공지사항 3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333","내용3","관리자","2019-11-03","1"));
        list.add(new InfoVO("4","공지사항 44444444444","내용4","관리자","2019-11-04","1"));
        list.add(new InfoVO("5","공지사항 555","내용5","관리자","2019-11-05","1"));
        list.add(new InfoVO("6","공지사항 6666666666666666666666","내용6","관리자","2019-11-06","1"));
        list.add(new InfoVO("7","공지사항 7","내용7","관리자","2019-11-07","1"));
        list.add(new InfoVO("8","공지사항 8","내용8","관리자","2019-11-08","1"));
        list.add(new InfoVO("9","공지사항 9","내용9","관리자","2019-11-09","1"));
        list.add(new InfoVO("10","공지사항 10","내용10","관리자","2019-11-10","1"));
        list.add(new InfoVO("11","공지사항 11","내용11","관리자","2019-11-11","1"));
        list.add(new InfoVO("12,", "공지사항 12","내용12","관리자","2019-11-12","1"));
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