package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(){
        return  new HomeFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button login= (Button) root.findViewById(R.id.loginBtn);
        TextView register = (TextView) root.findViewById(R.id.registerText);
        TextView guestLogin = (TextView) root.findViewById(R.id.guestLoginText);

        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.guestLoginText:
                    case R.id.loginBtn:
                        fg = CategoryFragment.newInstance();
                        setFragment(fg);
                        break;
                    case R.id.registerText:
                        fg = MemberRegistrationFragment.newInstance();
                        setFragment(fg);
                        break;
                }
            }
        };
        login.setOnClickListener(fragment);
        register.setOnClickListener(fragment);
        guestLogin.setOnClickListener(fragment);

        return root;
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