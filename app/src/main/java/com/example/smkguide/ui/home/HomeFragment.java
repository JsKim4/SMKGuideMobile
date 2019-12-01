package com.example.smkguide.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.task.member.LoginTask;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance(){
        return  new HomeFragment();
    }



    EditText idEdt,passwordEdt;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        Button login= (Button) root.findViewById(R.id.loginBtn);
        TextView register = (TextView) root.findViewById(R.id.registerText);
        TextView guestLogin = (TextView) root.findViewById(R.id.guestLoginText);
        idEdt = root.findViewById(R.id.idEdt);
        passwordEdt = root.findViewById(R.id.passwordEdt);
        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.guestLoginText:
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberVO vo = new MemberVO();
                vo.setPassword(passwordEdt.getText().toString());
                vo.setEmail(idEdt.getText().toString());
                LoginTask task = new LoginTask(getActivity(),root,getChildFragmentManager());
                task.execute(vo);
            }
        });
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