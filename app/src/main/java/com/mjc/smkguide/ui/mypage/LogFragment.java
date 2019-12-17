package com.mjc.smkguide.ui.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mjc.smkguide.R;
import com.mjc.smkguide.task.member.mypage.MyTask;

public class LogFragment extends Fragment {
    public LogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mypage_log, container, false);
        MyTask task = new MyTask(getActivity(),root,"smokelog");
        task.execute("smokelog/pages/1");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerPagination;
        recyclerPagination = root.findViewById(R.id.recycle);
        recyclerPagination.setLayoutManager(layoutManager);
        return root;
    }
}
