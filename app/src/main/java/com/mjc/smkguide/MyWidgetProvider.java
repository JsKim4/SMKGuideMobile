package com.mjc.smkguide;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mjc.smkguide.domain.MemberVO;
import com.mjc.smkguide.domain.SmokelogVO;
import com.mjc.smkguide.domain.TobaccoVO;
import com.mjc.smkguide.task.smokelog.AddSmokelogTask;

public class MyWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Bundle extras = intent.getExtras();
        if(extras!=null){
            if(extras.getBoolean("LogAdd",false)) {
                SharedPreferences pref = context.getSharedPreferences("user-info", context.MODE_PRIVATE);
                String token = pref.getString("token", null);
                Long tobaccoId = pref.getLong("likeTobacco",127);
                if (token == null || token.length() < 1) {
                    Toast.makeText(context, "작성 권한이 없습니다 로그인 이후 이용해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                SmokelogVO vo = new SmokelogVO();
                MemberVO member = new MemberVO();
                TobaccoVO tobacco = new TobaccoVO();
                member.setToken(token);
                tobacco.setTobaccoId(tobaccoId);
                vo.setMember(member);
                vo.setTobacco(tobacco);
                AddSmokelogTask task = new AddSmokelogTask(context, null);
                task.execute(vo);
            }
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildViews(context);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
    private RemoteViews buildViews(Context context){
        Intent in = new Intent(context,MyWidgetProvider.class);
        in.putExtra("LogAdd",true);
        PendingIntent pi = PendingIntent.getBroadcast(context,0,in,PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.logAddBtn,pi);
        return views;
    }



}
