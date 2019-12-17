package com.mjc.smkguide.task.member;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.MemberVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginTask extends AsyncTask<MemberVO, Void, String> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private FragmentManager maneger;
    private final static String LoginURL = "http://ggi4111.cafe24.com/mobile/login";


    public LoginTask(Activity context, View root,FragmentManager manager) {
        this.context = context;
        this.root = root;
        this.maneger = manager;
    }


    @Override
    protected String doInBackground(MemberVO... memberVOS) {
        try {
            URL url = new URL(LoginURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            Log.d("member", memberVOS[0].toString());
            os.write(memberVOS[0].memberToJson().toString().getBytes("UTF-8"));
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
                return token;
            }
        } catch (Exception e) {
            Log.d("error", "error");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!=null&&s.length()!=0){
            Navigation.findNavController(root).navigate(R.id.nav_home);
                Toast.makeText(context, "로그인 하였습니다.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"로그인 정보가 일치하지 않습니다",Toast.LENGTH_LONG).show();
        }
    }
}
