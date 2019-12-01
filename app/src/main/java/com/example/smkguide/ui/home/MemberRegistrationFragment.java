package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        return root;
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
            }
        });
    }
}
