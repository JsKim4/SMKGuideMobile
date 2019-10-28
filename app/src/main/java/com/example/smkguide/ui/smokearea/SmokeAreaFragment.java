package com.example.smkguide.ui.smokearea;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smkguide.R;

public class SmokeAreaFragment extends Fragment {

    public static SmokeAreaFragment newInstance(){
        return  new SmokeAreaFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_smokearea, container, false);




        return root;
    }


}