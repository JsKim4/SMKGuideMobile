package com.example.smkguide.ui.mypage;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import com.example.smkguide.Adpter.SectionPagerAdapter;
import com.example.smkguide.MainActivity;
import com.example.smkguide.R;
import com.example.smkguide.task.member.mypage.MyInfoTask;
import com.example.smkguide.ui.home.LoginFragment;
import com.google.android.material.tabs.TabLayout;


public class MyPageFragment extends Fragment {

    Fragment fragmentView;
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    Button btnLogout,btnLogView;
    public static MyPageFragment newInstance(){
        return new MyPageFragment();
    }

    public static MyPageFragment getInstance()    {
        return new MyPageFragment();
    }

    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
        token = pref.getString("token", null);
        if(token==null) {
            Log.d("token","null");
            ((MainActivity)getActivity()).replaceFragment(LoginFragment.newInstance());
            return null;
        } else {
            myFragment = inflater.inflate(R.layout.fragment_mypage, container, false);
            btnLogout = myFragment.findViewById(R.id.logoutBtn);
            MyInfoTask task  = new MyInfoTask(getActivity(),myFragment);
            task.execute(token);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("token");
                    editor.commit();
                    Navigation.findNavController(myFragment).navigate(R.id.nav_home);
                }
            });
            viewPager = myFragment.findViewById(R.id.viewPager);
            tabLayout = myFragment.findViewById(R.id.tabLayout);
            Log.d("token",token);
            btnLogView = myFragment.findViewById(R.id.btnLogVIew);

            btnLogView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentView = SmokeLogChartFragment.newInstance();
                    FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
                    if (!fragmentView.isAdded()) {
                        childFt.replace(R.id.fragmentMyPage, fragmentView);
                        childFt.addToBackStack(null);
                        childFt.commit();
                    }
                }

            });
        }

        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(token==null)
            return;
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        if(token==null)
            return;
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new LogFragment(), "LOG");
        adapter.addFragment(new CommentFragment(), "COMMENT");
        adapter.addFragment(new LikeFragment(),"Grade");

        viewPager.setAdapter(adapter);
    }
}