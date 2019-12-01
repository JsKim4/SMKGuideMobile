package com.example.smkguide.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.Adpter.InfoMainListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.InfoVO;
import com.example.smkguide.task.InfoTask;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info, container, false);
        //InfoTask task = new InfoTask(getActivity(), view);
       // task.execute("INFO");
        Button btn_first = (Button) view.findViewById(R.id.btn_first);
        Button btn_second = (Button) view.findViewById(R.id.btn_second);
        Button btn_third = (Button) view.findViewById(R.id.btn_third);
        ListView listView1 = (ListView) view.findViewById(R.id.listView1);
        ListView listView2 = (ListView) view.findViewById(R.id.listView2);
        ListView listView3 = (ListView) view.findViewById(R.id.listView3);
        TextView textView = (TextView) view.findViewById(R.id.textView);

        List<InfoVO> list1 = new ArrayList<InfoVO>();
        List<InfoVO> list2 = new ArrayList<InfoVO>();
        List<InfoVO> list3 = new ArrayList<InfoVO>();
        ListAdapter adapter1 = new InfoMainListViewAdapter(getContext(),list1);
        ListAdapter adapter2 = new InfoMainListViewAdapter(getContext(),list2);
        ListAdapter adapter3 = new InfoMainListViewAdapter(getContext(),list3);
        list1.add(new InfoVO("1","공지사항 11111111111111111111","내용2\n2\n2\n2\n2\n2\n222222222222","관리자","2019-11-02","1"));    // 테스트용 데이터
        list1.add(new InfoVO("2","공지사항 22222222222222222222222","내용2\n2\n2\n2\n2\n2\n222222222222","관리자","2019-11-02","1"));
        list1.add(new InfoVO("3","공지사항 33333333333333333333333333333333333333333333333","내용2\n2\n2\n2\n2\n2\n222222222222","관리자","2019-11-02","1"));
        list1.add(new InfoVO("4","공지사항 444444444444444","내용2\n2\n2\n2\n2\n2\n222222222222","관리자","2019-11-02","1"));
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        listView3.setAdapter(adapter3);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                title = "제목 1";     // 테스트용 커스텀 다이얼로그 데이터
                content = "내용 1";
                name = "관리자";
                date = "2000-01-01 AM 00:00";
                customDialog.call(title, content, name, date);
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                title = "제목 1";     // 테스트용 커스텀 다이얼로그 데이터
                content = "내용 1";
                name = "관리자";
                date = "2000-01-01 AM 00:00";
                customDialog.call(title, content, name, date);
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                title = "제목 1";     // 테스트용 커스텀 다이얼로그 데이터
                content = "내용 1";
                name = "관리자";
                date = "2000-01-01 AM 00:00";
                customDialog.call(title, content, name, date);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.btn_first:
                        fg = InfoFragmentFirst.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.btn_second:
                        fg = InfoFragmentSecond.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.btn_third:
                        fg = InfoFragmentThird.newInstance();
                        setFragment(fg);
                        break;
                }
            }
        };
        btn_first.setOnClickListener(listener);
        btn_second.setOnClickListener(listener);
        btn_third.setOnClickListener(listener);

        View.OnClickListener dialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                title = "명지전문대 전자공학과";
                content = "2015041001 김준섭\n2015041010 이근수\n2015041050 박성종\n2011041068 이승혁";
                name = "관리자";
                date = "2000-01-01 AM 03:30";
                customDialog.call(title, content, name, date);
            }
        };
        textView.setOnClickListener(dialog);

        return view;
    }

    private void setFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.fragment, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}