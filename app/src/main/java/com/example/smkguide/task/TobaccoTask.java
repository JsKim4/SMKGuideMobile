package com.example.smkguide.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smkguide.Adpter.TobaccoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import lombok.Data;

@Data
@SuppressLint("NewApi")
public class TobaccoTask extends AsyncTask<String,Void, ArrayList<TobaccoVO>>  {
    private Activity context;
    private View root;
    private Criteria cri;
    private String str, receiveMsg;
    TobaccoListViewAdapter adapter;
    ListView tobaccoListView;
    public TobaccoTask(Activity context,View root,Criteria cri){
        Log.d("taskStart",cri.toString());
        this.context = context;
        this.cri = cri;
        this.root = root;
    }
    @Override
    protected ArrayList<TobaccoVO> doInBackground(String... urls) {
        URL url=null;
        try{
            url =  new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            Log.d("criInfo",cri.toString());
            os.write(cri.toJson().toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            //conn.setRequestProperty("cri",cri.toJson().toString());
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
                Log.i("receiveMsg : ", receiveMsg);

                ArrayList<TobaccoVO> list = new ArrayList<TobaccoVO>();

                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray array = jsonObject.getJSONArray("tobaccoList");

                for(int i=0; i<array.length(); i++)
                {
                    JSONObject tobaccoObject = array.getJSONObject(i);
                    Log.i("tobaccoVV",tobaccoObject.toString());
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
        Log.i("list accepted",tobaccoVOS.toString());
        adapter = new TobaccoListViewAdapter(tobaccoVOS);
        tobaccoListView = (ListView)root.findViewById(R.id.listTobacco);
        tobaccoListView.setAdapter(adapter);
    }
}
