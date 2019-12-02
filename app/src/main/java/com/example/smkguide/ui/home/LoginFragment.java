package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.task.member.LoginTask;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance(){
        return  new LoginFragment();
    }


    EditText idEdt,passwordEdt;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);

        Button login= (Button) root.findViewById(R.id.loginBtn);
        TextView register = (TextView) root.findViewById(R.id.registerText);
        idEdt = root.findViewById(R.id.idEdt);
        passwordEdt = root.findViewById(R.id.passwordEdt);

        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
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

        LinearLayout main = root.findViewById(R.id.loginMain);
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

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