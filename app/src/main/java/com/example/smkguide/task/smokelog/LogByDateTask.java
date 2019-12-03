package com.example.smkguide.task.smokelog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smkguide.Adpter.TobaccoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.DateBySmokelog;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.SearchDateBySmokelog;
import com.example.smkguide.domain.TobaccoVO;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
@SuppressLint("NewApi")
public class LogByDateTask extends AsyncTask<SearchDateBySmokelog,Void, ArrayList<DateBySmokelog>>  {
    private Activity context;
    private View root;
    private String str, receiveMsg;
    private String SmokeLogURL = "http://ggi4111.cafe24.com/mobile/smokelog/list";
    SearchDateBySmokelog SEARCH_DATE;
    public LogByDateTask(Activity context, View root){
        this.context = context;

        this.root = root;
    }
    @Override
    protected ArrayList<DateBySmokelog> doInBackground(SearchDateBySmokelog... searchDate) {
        URL url=null;

        try{
            SEARCH_DATE = searchDate[0];
            url =  new URL(SmokeLogURL+searchDate[0].getSearchDateToUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod("POST");
            SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
            OutputStream os = conn.getOutputStream();
            MemberVO vo = new MemberVO();
            vo.setToken(pref.getString("token", null));
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
                receiveMsg = receiveMsg.substring(1);

                receiveMsg = "{\"dateBySmokelogList\":["+receiveMsg;
                receiveMsg+="}";

                ArrayList<DateBySmokelog> list = new ArrayList<DateBySmokelog>();

                JSONObject jsonObject = new JSONObject(receiveMsg);

                JSONArray array = jsonObject.getJSONArray("dateBySmokelogList");

                int j=0;
                Log.d("taskContent",searchDate[0].toString());
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date from = transFormat.parse(searchDate[0].getStartDate());
                Date to = transFormat.parse(searchDate[0].getEndDate());
                Calendar cFrom = Calendar.getInstance();cFrom.setTime(from);
                Calendar cTo = Calendar.getInstance();cTo.setTime(to);
                int i;
                for(i=0;j<array.length()&&cFrom.compareTo(cTo)!=0;i++,cFrom.add(Calendar.DATE,1)){
                    JSONObject object  = array.getJSONObject(j);
                    DateBySmokelog smokelog = new DateBySmokelog(object);
                    Log.d("taskContent",Integer.valueOf((cFrom.get(Calendar.YEAR)+""+(cFrom.get(Calendar.MONTH)+1)+""+(cFrom.get(Calendar.DATE)<10?"0"+cFrom.get(Calendar.DATE):cFrom.get(Calendar.DATE))))+"");
                    Log.d("taskContent",Integer.valueOf(Integer.valueOf(smokelog.getDate().replace("-","")))+"");

                    if(Integer.valueOf(cFrom.get(Calendar.YEAR)+""+(cFrom.get(Calendar.MONTH)+1)+""+(cFrom.get(Calendar.DATE)<10?"0"+cFrom.get(Calendar.DATE):cFrom.get(Calendar.DATE)))
                            .equals(Integer.valueOf(smokelog.getDate().replace("-","")))){
                        smokelog.setDate(String.valueOf(i));
                        list.add(smokelog);
                        j++;
                    }else
                        list.add(new DateBySmokelog(String.valueOf(i),0));
                }
                for(;i<5;i++){
                    list.add(new DateBySmokelog(String.valueOf(i),0));
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
    protected void onPostExecute(ArrayList<DateBySmokelog> dateBySmokelogs) {
        super.onPostExecute(dateBySmokelogs);
        TextView tvFromDate = root.findViewById(R.id.tvFromDate);
        TextView tvToDate = root.findViewById(R.id.tvToDate);
        tvFromDate.setText(SEARCH_DATE.getStartDate());
        tvToDate.setText(SEARCH_DATE.getEndDate());
        Button btnPrev = root.findViewById(R.id.btnPrev);
        Button btnNext = root.findViewById(R.id.btnNext);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                SEARCH_DATE.setDateFormat("%25Y-%25m-%25d");
                SEARCH_DATE.setEndDate(SEARCH_DATE.getStartDate());
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date endDate = null;
                try {
                    endDate = transFormat.parse(SEARCH_DATE.getEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(endDate);
                cal.add(Calendar.DATE, -5);
                SEARCH_DATE.setStartDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.DATE));
                LogByDateTask task = new LogByDateTask(context,root);
                task.execute(SEARCH_DATE);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.util.Calendar cal = java.util.Calendar.getInstance();
                SEARCH_DATE.setDateFormat("%25Y-%25m-%25d");
                SEARCH_DATE.setStartDate(SEARCH_DATE.getEndDate());
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                try {
                    startDate = transFormat.parse(SEARCH_DATE.getEndDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                cal.setTime(startDate);
                cal.add(Calendar.DATE, +5);
                SEARCH_DATE.setEndDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.DATE));
                LogByDateTask task = new LogByDateTask(context,root);
                task.execute(SEARCH_DATE);
            }
        });


        LineChart lineChart = root.findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<>();
        for(int i=0; i<dateBySmokelogs.size(); i++){
            entries.add(new Entry(Integer.valueOf(dateBySmokelogs.get(i).getDate().replace("-","")),dateBySmokelogs.get(i).getCnt()));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "개비수");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(1000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();
    }
}
