package com.mjc.smkguide.task.smokelog;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mjc.smkguide.domain.SmokelogVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddSmokelogTask extends AsyncTask<SmokelogVO, Void, Void> {
    private Context context;
    private View root;
    private String str, receiveMsg;
    private final static String LoginURL = "http://ggi4111.cafe24.com/mobile/smokelog/new";


    public AddSmokelogTask(Context context, View root) {
        this.context = context;
        this.root = root;
    }

    public AddSmokelogTask() {
    }

    @Override
    protected Void doInBackground(SmokelogVO... vo) {
        try{
            URL url = new URL(LoginURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(vo[0].smokelogToJson().toString().getBytes("UTF-8"));
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
                Log.d("receiveMsg", receiveMsg);
            }
        }catch(Exception e){
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(context!=null){
            Toast.makeText(context,"흡연 로그가 추가되었습니다",Toast.LENGTH_LONG).show();
        }
    }
}
