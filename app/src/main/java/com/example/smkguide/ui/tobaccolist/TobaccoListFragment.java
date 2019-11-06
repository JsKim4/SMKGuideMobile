package com.example.smkguide.ui.tobaccolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smkguide.ListViewAdapter.TobaccoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TobaccoListFragment extends Fragment {

    public static TobaccoListFragment newInstance(){
        return  new TobaccoListFragment();
    }
    TobaccoListViewAdapter adapter;

    ListView tobaccoListView ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tobaccolist, container, false);

        tobaccoListView = (ListView)root.findViewById(R.id.listTobacco);
        TobaccoTask task = new TobaccoTask(getActivity(),new Criteria());
        task.execute("http://ggi4111.cafe24.com/mobile/tobacco/list.json");
        return root;
    }




    @SuppressLint("NewApi")
    public class TobaccoTask extends AsyncTask<String,Void, ArrayList<TobaccoVO>> {
        private Activity context;

        private Criteria cri;
        private String str, receiveMsg;
        public TobaccoTask(Activity context,Criteria cri){
            this.context = context;
            this.cri = cri;
        }
        @Override
        protected ArrayList<TobaccoVO> doInBackground(String... urls) {
            URL url=null;
            try{
                url =  new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestMethod("POST");

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
            tobaccoListView.setAdapter(adapter);
        }
    }
}