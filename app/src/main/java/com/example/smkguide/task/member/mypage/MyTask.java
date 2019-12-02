package com.example.smkguide.task.member.mypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.Adpter.MyPageListViewAdapter;
import com.example.smkguide.Adpter.MypagePaginationViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.GradeVO;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.PageDTO;
import com.example.smkguide.domain.SmokelogVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import lombok.Data;


@Data
@SuppressLint("NewApi")
public class MyTask extends AsyncTask<String, Void, ArrayList> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private ListView listView;
    private RecyclerView paginationView;
    private BaseAdapter adapter;
    private final static String TaskURL = "http://ggi4111.cafe24.com/mobile/";
    private  String keyword;
    MypagePaginationViewAdapter paginationAdapter;

    public MyTask(Activity context, View root,String keyword) {
        this.context = context;
        this.root = root;
        this.keyword = keyword;
    }


    @Override
    protected ArrayList doInBackground(String... urls) {
        URL url = null;
        try {
            Log.d("debugging","0");
            url = new URL(TaskURL + urls[0]+".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            MemberVO vo = new MemberVO();
            SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
            String token = pref.getString("token", null);
            vo.setToken(token);
            os.write(vo.tokenToJson().toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            Log.d("debugging","1");
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                Log.d("debugging","2");
                receiveMsg = buffer.toString();
                Log.d("receiveMsh",keyword);
                Log.d("receiveMsg",receiveMsg);
                JSONObject jsonObject = new JSONObject(receiveMsg);
                JSONArray array= jsonObject.getJSONArray("list");

                ArrayList list = null;
                PageDTO pageDTO = null;


                if(keyword.equals("smokelog")){
                    list = new ArrayList<SmokelogVO>();
                    pageDTO = new PageDTO(jsonObject.getJSONObject("smokelogPage"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new SmokelogVO(object));
                    }
                }
                else if(keyword.equals("grade")){
                    list = new ArrayList<GradeVO>();
                    pageDTO = new PageDTO(jsonObject.getJSONObject("gradePage"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new GradeVO(object));
                    }
                }
                else if(keyword.equals("comment")){
                    list = new ArrayList<CommentVO>();
                    pageDTO = new PageDTO(jsonObject.getJSONObject("commentPage"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        list.add(new CommentVO(object));
                    }
                }
                Log.d("pageDTO",pageDTO.toString());
                ArrayList<String> itemList = new ArrayList<>();
                if(pageDTO.isPrev())
                    itemList.add("<");
                for (int i = pageDTO.getStartPage(); i <= pageDTO.getEndPage(); i++) {
                    itemList.add(String.valueOf(i));
                }
                if(pageDTO.isNext())
                    itemList.add(">");
                paginationAdapter = new MypagePaginationViewAdapter(context, itemList,root,keyword);
                Log.d("debugging","3");

                return list;
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
    protected void onPostExecute(ArrayList VOS) {
        super.onPostExecute(VOS);
        adapter = new MyPageListViewAdapter(context, VOS,keyword);
        listView = root.findViewById(R.id.list);
        paginationView = root.findViewById(R.id.recycle);
        listView.setAdapter(adapter);
        paginationView.setAdapter(paginationAdapter);
    }
}