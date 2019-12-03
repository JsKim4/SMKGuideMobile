package com.example.smkguide.task.member.mypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import androidx.recyclerview.widget.RecyclerView;
import lombok.Data;


@Data
@SuppressLint("NewApi")
public class MyInfoTask extends AsyncTask<String, Void,MemberVO> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private final static String TaskURL = "http://ggi4111.cafe24.com/mobile/userInfo";

    public MyInfoTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }


    @Override
    protected MemberVO doInBackground(String... token) {
        URL url = null;
        try {
            Log.d("debugging","0");
            url = new URL(TaskURL+".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            MemberVO vo = new MemberVO();
            SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
            vo.setToken(token[0]);
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
                JSONObject jsonObject = new JSONObject(receiveMsg);

                return new MemberVO(jsonObject);
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
    protected void onPostExecute(MemberVO vo) {
        super.onPostExecute(vo);
        if(vo==null){
            vo = new MemberVO();
            vo.setEmail("No Such");
            vo.setMemberName("No Such");
            vo.setAddress("No Such");
            vo.setTelephone("No SUch");
        }
        TextView tvEmail = root.findViewById(R.id.id_text);tvEmail.setText(vo.getEmail());
        TextView tvName = root.findViewById(R.id.name_text);tvName.setText(vo.getMemberName());
        TextView tvPhone = root.findViewById(R.id.phoneNum_text);tvPhone.setText(vo.getTelephone());
        TextView tvAddress = root.findViewById(R.id.address_text);tvAddress.setText(vo.getAddress());
    }
}