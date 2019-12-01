package com.example.smkguide.ui.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smkguide.Adpter.InfoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.InfoVO;
import com.example.smkguide.task.InfoTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public class InfoFragmentFirst extends Fragment {
    InfoVO vo;
    Criteria cri = new Criteria();
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
        cri.setType("NOTICE");
        cri.setAmount(1000);
        InfoTask task = new InfoTask(getActivity(), view,cri);
        task.execute("");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                InfoViewTask t  = new InfoViewTask();
                vo= (InfoVO)parent.getItemAtPosition(position);
                t.execute(vo.getInfoId());
            }
        });

        LinearLayout main = view.findViewById(R.id.infoFirstMain);
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return view;
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