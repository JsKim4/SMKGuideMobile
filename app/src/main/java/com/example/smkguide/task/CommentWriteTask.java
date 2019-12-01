package com.example.smkguide.task;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.MemberVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;


public class CommentWriteTask extends AsyncTask<CommentVO, Void, Void> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private final static String LoginURL = "http://ggi4111.cafe24.com/mobile/comment/new";


    public CommentWriteTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }

    @Override
    protected Void doInBackground(CommentVO... vo) {
        try{
            URL url = new URL(LoginURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            Log.d("comment", vo[0].commentToJson().toString());
            os.write(vo[0].commentToJson().toString().getBytes("UTF-8"));
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
            e.printStackTrace();
        }
        return null;
    }
}
