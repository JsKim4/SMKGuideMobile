package com.example.smkguide.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.smkguide.R;
public class HomeFragment extends Fragment {
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }



    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        CardView area= (CardView) root.findViewById(R.id.cardViewArea);
        CardView info= (CardView) root.findViewById(R.id.cardViewInfo);
        CardView list= (CardView) root.findViewById(R.id.cardViewList);
        CardView myPage= (CardView) root.findViewById(R.id.cardViewMyPage);
        CardView register= (CardView) root.findViewById(R.id.cardViewRegister);
        CardView setting= (CardView) root.findViewById(R.id.cardViewSetting);
        View.OnClickListener navigation = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
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
        area.setOnClickListener(navigation);
        info.setOnClickListener(navigation);
        list.setOnClickListener(navigation);
        myPage.setOnClickListener(navigation);
        register.setOnClickListener(navigation);
        setting.setOnClickListener(navigation);

        return root;
    }
}
