package com.example.smkguide.ui.tobaccolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smkguide.ListViewAdapter.TobaccoListViewAdapter;
import com.example.smkguide.R;

public class TobaccoListFragment extends Fragment {

    public static TobaccoListFragment newInstance(){
        return  new TobaccoListFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tobaccolist, container, false);

        ListView tobaccoListView = (ListView)root.findViewById(R.id.listTobacco);
        TobaccoListViewAdapter adapter = new TobaccoListViewAdapter();
        tobaccoListView.setAdapter(adapter);

        return root;
    }
}