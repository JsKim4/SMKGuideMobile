package com.example.smkguide.task.member;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smkguide.Adpter.PaginationViewAdapter;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.PageDTO;

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

public class LoginTask extends AsyncTask<MemberVO, Void, String> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private final static String LoginURL = "http://ggi4111.cafe24.com/mobile/login";


    public LoginTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }


    @Override
    protected String doInBackground(MemberVO... memberVOS) {
        try {
            URL url = new URL(LoginURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            MemberVO vo = new MemberVO();
            vo.setEmail("email");
            vo.setPassword("1");
            OutputStream os = conn.getOutputStream();
            Log.d("member", vo.toString());
            os.write(vo.memberToJson().toString().getBytes("UTF-8"));
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
                String token = receiveMsg;
                Log.d("token", token);
                SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("token");
                editor.putString("token", token);
                editor.commit();

                //SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
               // Toast.makeText(getActivity(),pref.getString("token", null),Toast.LENGTH_LONG).show();
                return token;
            }
        } catch (Exception e) {
            Log.d("error", "error");
            e.printStackTrace();
        }
        return null;
    }
}
