package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance(){
        return  new LoginFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button login= (Button) view.findViewById(R.id.loginBtn);
        TextView register = (TextView) view.findViewById(R.id.registerText);
        TextView guestLogin = (TextView) view.findViewById(R.id.guestLoginText);

        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.guestLoginText:
                    case R.id.loginBtn:
                        fg = HomeFragment.newInstance();
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

        LinearLayout main = view.findViewById(R.id.loginMain);
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

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