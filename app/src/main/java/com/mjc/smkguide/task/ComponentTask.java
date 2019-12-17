package com.mjc.smkguide.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.mjc.smkguide.Adpter.ComponentSpinnerAdapter;
import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.ComponentVO;

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
public class ComponentTask extends AsyncTask<String,Void, ArrayList<ComponentVO>> {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private String type;
    Spinner spinner;
    ComponentSpinnerAdapter componentSpinnerAdapter;
    public ComponentTask(Activity context,View root,String type){
        this.context = context;
        this.type = type;
        this.root = root;
    }
    @Override
    protected ArrayList<ComponentVO> doInBackground(String... urls) {
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

                receiveMsg = "{\"componentList\":["+receiveMsg;
                receiveMsg+="}";

                ArrayList<ComponentVO> list = new ArrayList<ComponentVO>();
                list.add(new ComponentVO(0L,type,type));
                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray array = jsonObject.getJSONArray("componentList");

                for(int i=0; i<array.length(); i++)
                {
                    JSONObject componentObject = array.getJSONObject(i);
                    list.add(new ComponentVO(componentObject));
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
    protected void onPostExecute(ArrayList<ComponentVO> componentVOS) {
        super.onPostExecute(componentVOS);
        Log.i("list accepted",componentVOS.toString());
        if(type.equals("brand")){
            spinner = (Spinner) root.findViewById(R.id.spBrand);
        }
        if(type.equals("type")){
            spinner = (Spinner) root.findViewById(R.id.spType);
        }
        if(type.equals("country")){
            spinner = (Spinner) root.findViewById(R.id.spCountry);
        }
        componentSpinnerAdapter = new ComponentSpinnerAdapter(context,componentVOS);
        spinner.setAdapter(componentSpinnerAdapter);
    }
}