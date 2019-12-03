package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.task.member.RegisterTask;

import java.util.regex.Pattern;

public class MemberRegistrationFragment extends Fragment {

    public static MemberRegistrationFragment newInstance(){
        return  new MemberRegistrationFragment();
    }


    View root;
    EditText edtId,edtPassword,edtName,edtTelephone,edtAddress;
    TextView tvFailMsg;
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
        tvFailMsg = root.findViewById(R.id.tvFailMsg);
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
                Log.d("register",vo.toString());
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(vo.getEmail()).matches()){
                    Toast.makeText(getActivity(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    tvFailMsg.setText("Register Fail !\n이메일 형식이 아닙니다\n#### @ #### . ### 형식으로 입력해 주십시요.");
                    tvFailMsg.setVisibility(View.VISIBLE);
                    return;
                }
                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{6,20}$", vo.getPassword())){
                    Toast.makeText(getActivity(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    tvFailMsg.setText("Register Fail ! \n올바른 비밀번호가 아닙니다.\n6알파벳과 숫자, 특수문자가 모두 포함된 6글자이상 20글자이하여야 합니다.");
                    tvFailMsg.setVisibility(View.VISIBLE);
                    return;
                }

                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", vo.getTelephone())&&
                        !Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", vo.getTelephone())&&
                        !Pattern.matches("^01(?:0|1|[6-9]) (?:\\d{3}|\\d{4}) \\d{4}$", vo.getTelephone())&&
                        !Pattern.matches("^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) -  \\d{4}$", vo.getTelephone())){
                    Toast.makeText(getActivity(),"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                    tvFailMsg.setText("Register Fail ! \n올바른 핸드폰 번호가 아닙니다.\n01#-####-#### 형식 혹은 \n숫자만 입력해 주십시요");
                    tvFailMsg.setVisibility(View.VISIBLE);
                    return;
                }

                task.execute(vo);
            }
        });
    }
}
