package com.example.smkguide.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;
import com.example.smkguide.task.InfoTask;

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
        Button btn_fourth = (Button) view.findViewById(R.id.btn_fourth);
        TextView textView1_2 = (TextView) view.findViewById(R.id.textView1_2);
        TextView textView1_3 = (TextView) view.findViewById(R.id.textView1_3);
        TextView textView1_4 = (TextView) view.findViewById(R.id.textView1_4);
        TextView textView1_5 = (TextView) view.findViewById(R.id.textView1_5);
        TextView textView2_2 = (TextView) view.findViewById(R.id.textView2_2);
        TextView textView2_3 = (TextView) view.findViewById(R.id.textView2_3);
        TextView textView2_4 = (TextView) view.findViewById(R.id.textView2_4);
        TextView textView2_5 = (TextView) view.findViewById(R.id.textView2_5);
        TextView textView3_2 = (TextView) view.findViewById(R.id.textView3_2);
        TextView textView3_3 = (TextView) view.findViewById(R.id.textView3_3);
        TextView textView3_4 = (TextView) view.findViewById(R.id.textView3_4);
        TextView textView3_5 = (TextView) view.findViewById(R.id.textView3_5);
        TextView textView4_2 = (TextView) view.findViewById(R.id.textView4_2);

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
                    case R.id.btn_fourth:
                        fg = InfoFragmentFourth.newInstance();
                        setFragment(fg);
                        break;
                }
            }
        };

        btn_first.setOnClickListener(listener);
        btn_second.setOnClickListener(listener);
        btn_third.setOnClickListener(listener);
        btn_fourth.setOnClickListener(listener);

        View.OnClickListener dialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                switch (view.getId()) {
                    case R.id.textView1_2:
                        title = "공지사항 1";    // 파싱 데이터 넣어야됨
                        content = "내용1\n11111\n1\n111\n1\n11111";
                        name = "관리자A";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView1_3:
                        title = "공지사항 22222222";
                        content = "내용22222\n2222\n22\n2\n2\n2\n2\n2\n222\n222\n2\n222\n222\n2\n222\n222\n2\n222\n222\n2\n222\n222";
                        name = "관리자BBBBB";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView1_4:
                        title = "공지사항 333333333333333333333333333333333333333333";
                        content = "내용3\n33\n3\n3\n3\n3\n3\n3333333\n3\n3\n3333\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n33333333n3\n3\n3\n3\n3\n3\n3\n3\n3\n33333333";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView1_5:
                        title = "공지사항 4444444444444444444";
                        content = "내용4\n44\n4\n4\n4\n44444\n4\n344443\n4444444\n4\n4\n444\n4\n4\n4\n44\n44\n4444444";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView2_2:
                        title = "뉴스 1";
                        content = "내용1\n11111\n1\n111\n1\n11111";
                        name = "관리자A";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView2_3:
                        title = "뉴스 2";
                        content = "내용22222\n2222\n22\n2\n2\n2\n2\n2\n2222222\n2";
                        name = "관리자BBBBB";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView2_4:
                        title = "뉴스 3333333333333333333333333333333333333333333";
                        content = "내용3\n33\n3\n3\n3\n3\n3\n3333333\n3\n3\n3333\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n33333333";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView2_5:
                        title = "뉴스 4444444444444444444";
                        content = "내용4\n44\n4\n4\n4\n44444\n4\n344443\n4444444\n4\n4\n444\n4\n4\n4\n44\n44\n4444444";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView3_2:
                        title = "게시판 1";
                        content = "내용1\n11111\n1\n111\n1\n11111";
                        name = "관리자A";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView3_3:
                        title = "게시판 2";
                        content = "내용22222\n2222\n22\n2\n2\n2\n2\n2\n22222222";
                        name = "관리자BBBBB";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView3_4:
                        title = "게시판 3333333333333333333333333333333333333333";
                        content = "내용3\n33\n3\n3\n3\n3\n3\n3333333\n3\n3\n3333\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n3\n33333333";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView3_5:
                        title = "게시판 4444444444444444444";
                        content = "내용4\n44\n4\n4\n4\n44444\n4\n344443\n4444444\n4\n4\n444\n4\n4\n4\n44\n44\n4444444";
                        name = "관리자C";
                        date = "2000-01-01 AM 03:30";
                        break;
                    case R.id.textView4_2:
                        title = "명지전문대 전자공학과";
                        content = "2015041001 김준섭\n2015041010 이근수\n2015041050 박성종\n2011041068 이승혁";
                        name = "Admin";
                        date = "2000-01-01 AM 03:30";
                        break;
                    default:
                        title = "";
                        content = "";
                        name = "";
                        date = "";
                }
                customDialog.call(title, content, name, date);
            }
        };

        textView1_2.setOnClickListener(dialog);
        textView1_3.setOnClickListener(dialog);
        textView1_4.setOnClickListener(dialog);
        textView1_5.setOnClickListener(dialog);
        textView2_2.setOnClickListener(dialog);
        textView2_3.setOnClickListener(dialog);
        textView2_4.setOnClickListener(dialog);
        textView2_5.setOnClickListener(dialog);
        textView3_2.setOnClickListener(dialog);
        textView3_3.setOnClickListener(dialog);
        textView3_4.setOnClickListener(dialog);
        textView3_5.setOnClickListener(dialog);
        textView4_2.setOnClickListener(dialog);

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