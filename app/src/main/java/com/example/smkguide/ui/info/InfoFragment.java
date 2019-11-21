package com.example.smkguide.ui.info;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.smkguide.domain.InfoVO;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        Button btn_first = (Button) view.findViewById(R.id.btn_first);
        Button btn_second = (Button) view.findViewById(R.id.btn_second);
        Button btn_third = (Button) view.findViewById(R.id.btn_third);
        TextView textView1_2 = (TextView) view.findViewById(R.id.textView1_2);
        TextView textView1_3 = (TextView) view.findViewById(R.id.textView1_3);
        TextView textView1_4 = (TextView) view.findViewById(R.id.textView1_4);
        TextView textView2_2 = (TextView) view.findViewById(R.id.textView2_2);
        TextView textView2_3 = (TextView) view.findViewById(R.id.textView2_3);
        TextView textView2_4 = (TextView) view.findViewById(R.id.textView2_4);
        TextView textView3_2 = (TextView) view.findViewById(R.id.textView3_2);
        TextView textView3_3 = (TextView) view.findViewById(R.id.textView3_3);
        TextView textView3_4 = (TextView) view.findViewById(R.id.textView3_4);
        TextView textView4_2 = (TextView) view.findViewById(R.id.textView4_2);

        View.OnClickListener listener = new View.OnClickListener()
        {
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

        // 파싱시 데이터 연결시켜야됨
        View.OnClickListener dialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, content, name, date;
                InfoDialog customDialog = new InfoDialog(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                switch(view.getId()) {
                    case R.id.textView1_2:
                        //builder.setTitle("공지사항 1").setMessage("1-111111111111111111111111111111111111111111111111");
                        //title=info.getTitle();
                        //content=info.getContent();
                        //name=info.getName();
                        //date=info.getDate();
                        //customDialog.call(title, content, name, date);
                        break;
                    case R.id.textView1_3:
                        builder.setTitle("공지사항 2").setMessage("1-2");
                        break;
                    case R.id.textView1_4:
                        builder.setTitle("공지사항 3").setMessage("1-3");
                        break;
                    case R.id.textView2_2:
                        builder.setTitle("뉴스 1").setMessage("2-1");
                        break;
                    case R.id.textView2_3:
                        builder.setTitle("뉴스 2").setMessage("2-2");
                        break;
                    case R.id.textView2_4:
                        builder.setTitle("뉴스 3").setMessage("2-3");
                        break;
                    case R.id.textView3_2:
                        builder.setTitle("게시글 1").setMessage("3-1");
                        break;
                    case R.id.textView3_3:
                        builder.setTitle("게시글 2").setMessage("3-2");
                        break;
                    case R.id.textView3_4:
                        builder.setTitle("게시글 3").setMessage("3-3");
                        break;
                    case R.id.textView4_2:
                        builder.setTitle("명지전문대학 전자공학과").setMessage("2015041001 김준섭\n2015041010 이근수\n2015041050 박성종\n2011041068 이승혁");
                        break;
                }
                builder.setPositiveButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };

        textView1_2.setOnClickListener(dialog);
        textView1_3.setOnClickListener(dialog);
        textView1_4.setOnClickListener(dialog);
        textView2_2.setOnClickListener(dialog);
        textView2_3.setOnClickListener(dialog);
        textView2_4.setOnClickListener(dialog);
        textView3_2.setOnClickListener(dialog);
        textView3_3.setOnClickListener(dialog);
        textView3_4.setOnClickListener(dialog);
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