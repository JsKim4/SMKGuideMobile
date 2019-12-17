package com.mjc.smkguide.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.mjc.smkguide.domain.SmokeAreaVO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MapTask extends AsyncTask<String, Void, ArrayList<SmokeAreaVO>> {

    private Activity context;
    private View root;
    private String str, receiveMsg;
    private GoogleMap map;

    public MapTask(Activity context, View root, GoogleMap map) {
        this.context = context;
        this.root = root;
        this.map = map;
    }


    @Override
    protected ArrayList<SmokeAreaVO> doInBackground(String... urls) {
        URL url = null;
        try {
            url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("GET");
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

                receiveMsg = "{\"smokeareaList\":[" + receiveMsg;
                receiveMsg += "}";
                Log.i("receiveMsg : ", receiveMsg);

                ArrayList<SmokeAreaVO> list = new ArrayList<SmokeAreaVO>();

                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray array = jsonObject.getJSONArray("smokeareaList");

                for (int i = 0; i < array.length(); i++) {
                    JSONObject areaObject = array.getJSONObject(i);
                    list.add(new SmokeAreaVO(areaObject));
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
    protected void onPostExecute(ArrayList<SmokeAreaVO> SmokeAreaVOS) {
        super.onPostExecute(SmokeAreaVOS);


        LatLng MarkerPosition ;
        MarkerOptions markerOptions = new MarkerOptions();

        for (int i = 0; i < SmokeAreaVOS.size(); i++) {
            MarkerPosition= new LatLng(SmokeAreaVOS.get(i).getLatitude(), SmokeAreaVOS.get(i).getLongitude());
            markerOptions.position(MarkerPosition);
            markerOptions.title(SmokeAreaVOS.get(i).getAreaName());
            map.addMarker(markerOptions);
        }
    }
}
