package com.example.smkguide.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.Adpter.CommentListViewAdapter;
import com.example.smkguide.Adpter.InfoListViewAdapter;
import com.example.smkguide.Adpter.PaginationViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.InfoVO;
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
public class InfoTask extends AsyncTask<String, Void, ArrayList<InfoVO>> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private ListView infoListVIew;
    private InfoListViewAdapter adapter;
    private final static String InfoURL = "http://ggi4111.cafe24.com/info/list.json";
    private Criteria cri;
    public InfoTask(Activity context, View root,Criteria cri) {
        this.context = context;
        this.root = root;
        this.cri = cri;
    }

    @Override
    protected ArrayList<InfoVO> doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(InfoURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            Log.d("criInfo",cri.toString());
            os.write(cri.toJson().toString().getBytes("UTF-8"));
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
                receiveMsg = receiveMsg.substring(1);

                receiveMsg = "{\"infoList\":["+receiveMsg;
                receiveMsg+="}";
                ArrayList<InfoVO> list = new ArrayList<InfoVO>();
                JSONObject jsonObject = new JSONObject(receiveMsg);
                JSONArray array = jsonObject.getJSONArray("infoList");


                for (int i = 0; i < array.length(); i++) {
                    JSONObject infoObject = array.getJSONObject(i);
                    Log.d("addReceiveMsg",infoObject.toString());
                    list.add(new InfoVO(infoObject));
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
    protected void onPostExecute(ArrayList<InfoVO> infoVOS) {
        super.onPostExecute(infoVOS);
        infoListVIew = root.findViewById(R.id.listView);
        adapter = new InfoListViewAdapter(context, infoVOS);
        infoListVIew.setAdapter(adapter);
    }
}