package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.smkguide.R;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.task.member.LoginTask;
import com.example.smkguide.ui.info.InfoFragment;
import com.example.smkguide.ui.mypage.MyPageFragment;
import com.example.smkguide.ui.setting.SettingFragment;
import com.example.smkguide.ui.smokearea.SmokeAreaFragment;
import com.example.smkguide.ui.tobaccolist.TobaccoListFragment;
public class HomeFragment extends Fragment {
    public static HomeFragment newInstance(){
        return new HomeFragment();
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

        CardView area= (CardView) root.findViewById(R.id.cardViewArea);
        CardView info= (CardView) root.findViewById(R.id.cardViewInfo);
        CardView list= (CardView) root.findViewById(R.id.cardViewList);
        CardView myPage= (CardView) root.findViewById(R.id.cardViewMyPage);
        CardView register= (CardView) root.findViewById(R.id.cardViewRegister);
        CardView setting= (CardView) root.findViewById(R.id.cardViewSetting);

        View.OnClickListener fragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fg;
                switch (view.getId()) {
                    case R.id.guestLoginText:
                        fg = CategoryFragment.newInstance();
                        setFragment(fg);
                    case R.id.cardViewArea:
                        Navigation.findNavController(view).navigate(R.id.nav_area);
                        break;
                    case R.id.cardViewInfo:
                        Navigation.findNavController(view).navigate(R.id.nav_info);
                        break;
                    case R.id.cardViewList:
                        Navigation.findNavController(view).navigate(R.id.nav_tobaccoList);
                        break;
                    case R.id.cardViewRegister:
                        Navigation.findNavController(view).navigate(R.id.nav_MemberRegistration);
                        break;
                    case R.id.cardViewMyPage:
                        Navigation.findNavController(view).navigate(R.id.nav_myPage);
                        break;
                    case R.id.cardViewSetting:
                        Navigation.findNavController(view).navigate(R.id.nav_setting);
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
        area.setOnClickListener(fragment);
        info.setOnClickListener(fragment);
        list.setOnClickListener(fragment);
        myPage.setOnClickListener(fragment);
        register.setOnClickListener(fragment);
        setting.setOnClickListener(fragment);

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
