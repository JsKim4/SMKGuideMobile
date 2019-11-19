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

import com.example.smkguide.ListViewAdapter.InfoListViewAdapter;
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
        list.add(new InfoVO("공지사항 111111111111111111111111111111111","관리자","2019-11-01"));
        list.add(new InfoVO("공지사항 2222222222222222222222222","관리자","2019-11-02"));
        list.add(new InfoVO("공지사항 333333333333333333333333333","관리자","2019-11-03"));
        list.add(new InfoVO("공지사항 44444444444","관리자","2019-11-04"));
        list.add(new InfoVO("공지사항 555","관리자","2019-11-05"));
        list.add(new InfoVO("공지사항 6666666666666666666666","관리자","2019-11-06"));
        list.add(new InfoVO("공지사항 7","관리자","2019-11-07"));
        list.add(new InfoVO("공지사항 8","관리자","2019-11-08"));
        list.add(new InfoVO("공지사항 9","관리자","2019-11-09"));
        list.add(new InfoVO("공지사항 10","관리자","2019-11-10"));
        list.add(new InfoVO("공지사항 11","관리자","2019-11-11"));
        list.add(new InfoVO("공지사항 12","관리자","2019-11-12"));
        ListAdapter adapter = new InfoListViewAdapter(getContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                InfoVO info = (InfoVO) parent.getAdapter().getItem(position);
                builder.setTitle(info.getNotice()).setMessage(info.getContent());
                builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return view;
    }
}