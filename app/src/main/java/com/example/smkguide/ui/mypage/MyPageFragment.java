package com.example.smkguide.ui.mypage;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smkguide.R;



public class MyPageFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);


        TabHost host=(TabHost)getActivity().findViewById(R.id.host);
        host.setup();

        TabHost.TabSpec spec = host.newTabSpec("log");
        spec.setIndicator("log");
        host.addTab(spec);


        spec = host.newTabSpec("comment");
        spec.setIndicator("comment");
        host.addTab(spec);


        spec = host.newTabSpec("grade");
        spec.setIndicator("grade");
        host.addTab(spec);

        return view;
    }


}




