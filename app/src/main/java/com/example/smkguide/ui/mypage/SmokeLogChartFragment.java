package com.example.smkguide.ui.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smkguide.R;
import com.example.smkguide.domain.SearchDateBySmokelog;
import com.example.smkguide.task.member.mypage.MyTask;
import com.example.smkguide.task.smokelog.LogByDateTask;

import java.util.Calendar;

public class SmokeLogChartFragment extends Fragment {
    public static SmokeLogChartFragment newInstance(){
        return new SmokeLogChartFragment();
    }

    public static SmokeLogChartFragment getInstance()    {
        return new SmokeLogChartFragment();
    }

    SearchDateBySmokelog searchDateBySmokelog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smokelog_char, container, false);
        searchDateBySmokelog = new SearchDateBySmokelog();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        searchDateBySmokelog.setDateFormat("%25Y-%25m-%25d");
        searchDateBySmokelog.setEndDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.DATE));
        cal.add(Calendar.DATE, -5);
        searchDateBySmokelog.setStartDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.get(cal.DATE));
        LogByDateTask task = new LogByDateTask(getActivity(),root);
        task.execute(searchDateBySmokelog);
        return root;
    }
}
