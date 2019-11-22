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

public class InfoFragmentThird extends Fragment {

    public static InfoFragmentThird newInstance(){
        return new InfoFragmentThird();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_third, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<InfoVO> list = new ArrayList<InfoVO>();
        list.add(new InfoVO("1","게시글 111111111111111111111111111111111","내용1","관리자","2019-11-01","3"));
        list.add(new InfoVO("2","게시글 2222222222222222222222222","내용2","관리자","2019-11-02","3"));
        list.add(new InfoVO("3","게시글 333333333333333333333333333","내용3","관리자","2019-11-03","3"));
        list.add(new InfoVO("4","게시글 44444444444","내용4","관리자","2019-11-04","3"));
        list.add(new InfoVO("5","게시글 555","내용5","관리자","2019-11-05","3"));
        list.add(new InfoVO("6","게시글 6666666666666666666666","내용6","관리자","2019-11-06","3"));
        list.add(new InfoVO("7","게시글 7","내용7","관리자","2019-11-07","3"));
        list.add(new InfoVO("8","게시글 8","내용8","관리자","2019-11-08","3"));
        list.add(new InfoVO("9","게시글 9","내용9","관리자","2019-11-09","3"));
        list.add(new InfoVO("10","게시글 10","내용10","관리자","2019-11-10","3"));
        list.add(new InfoVO("11","게시글 11","내용11","관리자","2019-11-11","3"));
        list.add(new InfoVO("12","게시글 12","내용12","관리자","2019-11-12","3"));
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