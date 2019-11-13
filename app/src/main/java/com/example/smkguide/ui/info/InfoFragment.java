package com.example.smkguide.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;

public class InfoFragment extends Fragment {

    public static InfoFragment newInstance(){
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);


        Button btn_first = (Button) view.findViewById(R.id.btn_first);
        Button btn_second = (Button) view.findViewById(R.id.btn_second);
        Button btn_third = (Button) view.findViewById(R.id.btn_third);
        Button btn_fourth = (Button) view.findViewById(R.id.btn_fourth);

        View.OnClickListener movePageListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.btn_first:
                        fg = InfoFragmentFirst.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.btn_second:
                        fg = InfoFragmentSecond.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.btn_third:
                        fg = InfoFragmentThird.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.btn_fourth:
                        fg = InfoFragmentFourth.newInstance();
                        setFragment(fg);
                        break;
                }
            }
        };

        btn_first.setOnClickListener(movePageListener);
        btn_second.setOnClickListener(movePageListener);
        btn_third.setOnClickListener(movePageListener);
        btn_fourth.setOnClickListener(movePageListener);

        return view;
    }

    private void setFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.fragment, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}