package com.example.smkguide.ui.info;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.Adpter.InfoMainListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.InfoVO;
import com.example.smkguide.task.info.InfoMainTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    InfoVO vo;
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
        Criteria cri1  = new Criteria(1,4); cri1.setType("NOTICE");
        InfoMainTask task1 = new InfoMainTask(getActivity(),view,cri1); task1.execute();
        Criteria cri2  = new Criteria(1,4); cri2.setType("NEWS");
        InfoMainTask task2 = new InfoMainTask(getActivity(),view,cri2); task2.execute();
        Criteria cri3  = new Criteria(1,4); cri3.setType("INFO");
        InfoMainTask task3 = new InfoMainTask(getActivity(),view,cri3); task3.execute();


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                InfoViewTask t  = new InfoFragment.InfoViewTask();
                vo= (InfoVO)parent.getItemAtPosition(position);
                t.execute(vo.getInfoId());
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                InfoViewTask t  = new InfoFragment.InfoViewTask();
                vo= (InfoVO)parent.getItemAtPosition(position);
                t.execute(vo.getInfoId());
            }
        });

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                InfoViewTask t  = new InfoFragment.InfoViewTask();
                vo= (InfoVO)parent.getItemAtPosition(position);
                t.execute(vo.getInfoId());
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
                date = "2019-12-03 PM 02:00";
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
    @Data
    @SuppressLint("NewApi")
    public class InfoViewTask extends AsyncTask<String, Void, InfoVO> {
        private String str, receiveMsg;
        private final static String InfoURL = "http://ggi4111.cafe24.com/info/";

        @Override
        protected InfoVO doInBackground(String... urls) {
            URL url = null;
            try {
                url = new URL(InfoURL+urls[0]+".json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();

                    JSONObject jsonObject = new JSONObject(receiveMsg);

                    return new InfoVO(jsonObject);
                } else {
                    Log.i("통신 결과", conn.getResponseCode() + "에러");
                }


            } catch (Exception e) {
                Log.d("error", "error");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(InfoVO infoVO) {
            super.onPostExecute(infoVO);
            vo = infoVO;
            Log.d("WhatDoesInfo",infoVO.toString());
            InfoDialog customDialog = new InfoDialog(getContext());
            customDialog.call(vo.getTitle(), vo.getContent(), vo.getName(), vo.getDate());
        }
    }
}