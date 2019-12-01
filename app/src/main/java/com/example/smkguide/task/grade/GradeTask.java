package com.example.smkguide.task.grade;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.Adpter.CommentListViewAdapter;
import com.example.smkguide.Adpter.PaginationViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.GradeVO;
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
public class GradeTask extends AsyncTask<String, Void, ArrayList<GradeVO>> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private ListView commentListView;
    //private RecyclerView paginationView;
   // private CommentListViewAdapter adapter;
    private final static String GRADE_URL = "http://ggi4111.cafe24.com/mobile/grade/pages";
//    PaginationViewAdapter paginationAdapter;

    public GradeTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }

    @Override
    protected ArrayList<GradeVO> doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(GRADE_URL + urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
            MemberVO vo = new MemberVO();
            vo.setToken(pref.getString("token", null));
            OutputStream os = conn.getOutputStream();
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
                Log.d("receiveMsg",receiveMsg);
                ArrayList<GradeVO> list = new ArrayList<GradeVO>();
                JSONObject jsonObject = new JSONObject(receiveMsg);
                JSONArray array = jsonObject.getJSONArray("list");

                PageDTO pageDTO = new PageDTO(jsonObject.getJSONObject("gradePage"));
                Log.d("receiveMsg",pageDTO.toString());

                ArrayList<String> itemList = new ArrayList<>();
                if(pageDTO.isPrev())
                    itemList.add("<");
                for (int i = pageDTO.getStartPage(); i <= pageDTO.getEndPage(); i++) {
                    itemList.add(String.valueOf(i));
                }
                if(pageDTO.isNext())
                    itemList.add(">");
                //paginationAdapter = new PaginationViewAdapter(context, itemList,root,urls[0]);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject gradeObject = array.getJSONObject(i);
                    list.add(new GradeVO(gradeObject));
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
    protected void onPostExecute(ArrayList<GradeVO> gradeVOS) {
        super.onPostExecute(gradeVOS);
        /*commentListView = root.findViewById(R.id.listComment);
        adapter = new CommentListViewAdapter(context, commentVOS);
        commentListView.setAdapter(adapter);
        paginationView = root.findViewById(R.id.recyclePagination);
        paginationView.setAdapter(paginationAdapter);*/
    }
}