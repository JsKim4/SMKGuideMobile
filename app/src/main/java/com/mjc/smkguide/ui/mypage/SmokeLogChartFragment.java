package com.mjc.smkguide.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.SearchDateBySmokelog;
import com.mjc.smkguide.task.smokelog.LogByDateTask;

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
        searchDateBySmokelog.setStartDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)+1)+"-"+cal.getActualMinimum(cal.DAY_OF_MONTH));
        cal.add(Calendar.MONTH,1);
        searchDateBySmokelog.setEndDate(cal.get(cal.YEAR)+"-"+(cal.get(cal.MONTH)==11?1:cal.get(cal.MONTH)+1)+"-"+cal.getActualMinimum(cal.DAY_OF_MONTH));
        LogByDateTask task = new LogByDateTask(getActivity(),root);
        task.execute(searchDateBySmokelog);
        return root;
    }
}
