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
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.task.member.RegisterTask;

public class MemberRegistrationFragment extends Fragment {

    public static MemberRegistrationFragment newInstance(){
        return  new MemberRegistrationFragment();
    }


    View root;
    EditText edtId,edtPassword,edtName,edtTelephone,edtAddress;
    Button registerButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_member_registration, container, false);
        init();
        setEvent();

        LinearLayout main = root.findViewById(R.id.registrationMain);
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
    public void init(){
        edtId = root.findViewById(R.id.edtRegisterId);
        edtPassword = root.findViewById(R.id.edtRegisterPassword);
        edtName = root.findViewById(R.id.edtRegisterName);
        edtTelephone = root.findViewById(R.id.edtRegisterTelephone);
        edtAddress = root.findViewById(R.id.edtRegisterAddress);
        registerButton = root.findViewById(R.id.registerButton);
    }
    public void setEvent(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberVO vo = new MemberVO();
                vo.setEmail(edtId.getText().toString());
                vo.setMemberName(edtName.getText().toString());
                vo.setPassword(edtPassword.getText().toString());
                vo.setTelephone(edtTelephone.getText().toString());
                vo.setAddress(edtAddress.getText().toString());
                RegisterTask task = new RegisterTask(getActivity(),root,getChildFragmentManager());
                task.execute(vo);
                Fragment fg;
                fg = HomeFragment.newInstance();
                setFragment(fg);
                Toast.makeText(getContext(),"가입하였습니다.", Toast.LENGTH_SHORT).show(); // 파싱 후 try catch에 추가
            }
        });
    }
}
