package com.example.smkguide.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.smkguide.Adpter.TobaccoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;

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
public class TobaccoTask extends AsyncTask<String,Void, ArrayList<TobaccoVO>>  {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    TobaccoListViewAdapter adapter;
    ListView tobaccoListView;
    public TobaccoTask(Activity context,View root){
        this.context = context;

        this.root = root;
    }
    @Override
    protected ArrayList<TobaccoVO> doInBackground(String... urls) {
        URL url=null;
        try{
            url =  new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                receiveMsg = receiveMsg.substring(1);

                receiveMsg = "{\"tobaccoList\":["+receiveMsg;
                receiveMsg+="}";

                ArrayList<TobaccoVO> list = new ArrayList<TobaccoVO>();

                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray array = jsonObject.getJSONArray("tobaccoList");

                for(int i=0; i<array.length(); i++)
                {
                    JSONObject tobaccoObject = array.getJSONObject(i);
                    list.add(new TobaccoVO(tobaccoObject));

                }

                return list;
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }


        }catch(Exception e){
            Log.d("error","error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TobaccoVO> tobaccoVOS) {
        super.onPostExecute(tobaccoVOS);
        adapter = new TobaccoListViewAdapter(tobaccoVOS);
        tobaccoListView = (ListView)root.findViewById(R.id.listTobacco);
        tobaccoListView.setAdapter(adapter);

    }
}
