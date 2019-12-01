package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;

public class MemberRegistrationFragment extends Fragment {

    public static MemberRegistrationFragment newInstance(){
        return  new MemberRegistrationFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_registration, container, false);

        Button register_ok= (Button) view.findViewById(R.id.registerButton);

        // 서버에 데이터 송신 필요
        EditText id = (EditText) view.findViewById(R.id.id);
        EditText pw = (EditText) view.findViewById(R.id.password);
        EditText name = (EditText) view.findViewById(R.id.name);
        EditText phone = (EditText) view.findViewById(R.id.phone_num);
        EditText add = (EditText) view.findViewById(R.id.address);

        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.registerButton:
                        fg = HomeFragment.newInstance();
                        setFragment(fg);
                        Toast.makeText(getContext(),"가입하였습니다.", Toast.LENGTH_SHORT).show(); // 파싱 후 try catch에 추가
                        break;
                }
            }
        };
        register_ok.setOnClickListener(fragment);

        LinearLayout main = view.findViewById(R.id.registrationMain);
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
