package com.example.smkguide.task.member.mypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.Adpter.CommentListViewAdapter;
import com.example.smkguide.Adpter.PaginationViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.PageDTO;

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
public class MyTask extends AsyncTask<String, Void, ArrayList<CommentVO>> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private ListView listView;
    private RecyclerView paginationView;
    private BaseAdapter adapter;
    private final static String TaskURL = "http://ggi4111.cafe24.com/mobile/";
    private  String keyword;
    PaginationViewAdapter paginationAdapter;

    public MyTask(Activity context, View root,String keyword) {
        this.context = context;
        this.root = root;
        this.keyword = keyword;
    }

    @Override
    protected ArrayList<CommentVO> doInBackground(String... urls) {
        URL url = null;
        try {
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

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();

                ArrayList<CommentVO> list;
                JSONObject jsonObject;
                JSONArray array;
                PageDTO pageDTO;


                list = new ArrayList<CommentVO>();
                jsonObject = new JSONObject(receiveMsg);
                array= jsonObject.getJSONArray("list");
                pageDTO = new PageDTO(jsonObject.getJSONObject("commentPage"));

                ArrayList<String> itemList = new ArrayList<>();
                if(pageDTO.isPrev())
                    itemList.add("<");
                for (int i = pageDTO.getStartPage(); i <= pageDTO.getEndPage(); i++) {
                    itemList.add(String.valueOf(i));
                }
                if(pageDTO.isNext())
                    itemList.add(">");
                paginationAdapter = new PaginationViewAdapter(context, itemList,root,urls[0]);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject commentObject = array.getJSONObject(i);
                    list.add(new CommentVO(commentObject));
                }
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
    protected void onPostExecute(ArrayList<CommentVO> commentVOS) {
        super.onPostExecute(commentVOS);

        //adapter = new CommentListViewAdapter(context, commentVOS);
        
        //listView = root.findViewById(R.id.listComment);
        listView.setAdapter(adapter);

        //paginationView = root.findViewById(R.id.recyclePagination);
        paginationView.setAdapter(paginationAdapter);
    }
}