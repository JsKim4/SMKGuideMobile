package com.mjc.smkguide.task.grade;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mjc.smkguide.domain.GradeVO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddGradeTask extends AsyncTask<GradeVO, Void, Void> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private final static String LoginURL = "http://ggi4111.cafe24.com/mobile/grade/new";
    private String tobaccoName,score;

    public AddGradeTask(Activity context, View root) {
        this.context = context;
        this.root = root;
    }

    @Override
    protected Void doInBackground(GradeVO... vo) {
        try{
            URL url = new URL(LoginURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            tobaccoName = vo[0].getTobacco().getTobaccoName();
            score = String.valueOf(vo[0].getScore());
            OutputStream os = conn.getOutputStream();
            os.write(vo[0].gradeToJson().toString().getBytes("UTF-8"));
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
        Toast.makeText(context,tobaccoName+"에 "+score+"점을 주었습니다.",Toast.LENGTH_LONG).show();
    }
}
