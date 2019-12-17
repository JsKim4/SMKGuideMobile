package com.mjc.smkguide.task.comment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.mjc.smkguide.Adpter.CommentListViewAdapter;
import com.mjc.smkguide.Adpter.PaginationViewAdapter;
import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.CommentVO;
import com.mjc.smkguide.domain.PageDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import lombok.Data;


@Data
@SuppressLint("NewApi")
public class CommentTask extends AsyncTask<String, Void, ArrayList<CommentVO>> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private ListView commentListView;
    private RecyclerView paginationView;
    private CommentListViewAdapter adapter;
    private final static String CommentURL = "http://ggi4111.cafe24.com/mobile/comment/pages/T";
    PaginationViewAdapter paginationAdapter;

    public CommentTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }

    @Override
    protected ArrayList<CommentVO> doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(CommentURL + urls[0]);
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

                ArrayList<CommentVO> list = new ArrayList<CommentVO>();
                JSONObject jsonObject = new JSONObject(receiveMsg);
                JSONArray array = jsonObject.getJSONArray("list");

                PageDTO pageDTO = new PageDTO(jsonObject.getJSONObject("commentPage"));


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
        commentListView = root.findViewById(R.id.listComment);
        adapter = new CommentListViewAdapter(context, commentVOS);
        commentListView.setAdapter(adapter);
        paginationView = root.findViewById(R.id.recyclePagination);
        paginationView.setAdapter(paginationAdapter);
    }
}