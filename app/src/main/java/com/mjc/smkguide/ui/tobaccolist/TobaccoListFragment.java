package com.mjc.smkguide.ui.tobaccolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mjc.smkguide.Adpter.TobaccoListViewAdapter;
import com.mjc.smkguide.R;
import com.mjc.smkguide.domain.ComponentVO;
import com.mjc.smkguide.domain.Criteria;
import com.mjc.smkguide.domain.MemberVO;
import com.mjc.smkguide.domain.SmokelogVO;
import com.mjc.smkguide.domain.TobaccoVO;
import com.mjc.smkguide.task.ComponentTask;
import com.mjc.smkguide.task.TobaccoTask;
import com.mjc.smkguide.task.smokelog.AddSmokelogTask;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class TobaccoListFragment extends Fragment {

    public static TobaccoListFragment newInstance() {
        return new TobaccoListFragment();
    }

    InputMethodManager imm;
    TobaccoTask tobaccoTask;
    ComponentTask brandTask, typeTask, countryTask;
    View root;
    Fragment fragmentView;
    ListView tobaccoListView;
    Spinner spBrand, spType, spCountry;
    Button btnMost, btnBest, btnReset;
    LinearLayout linearLayout;
    EditText etSearchTobacco;
    Criteria cri = new Criteria();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccolist, container, false);
        Init();
        return root;
    }

    public void Init() {
        imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        linearLayout = root.findViewById(R.id.tobaccoListLayout);
        tobaccoListView = root.findViewById(R.id.listTobacco);
        spBrand = root.findViewById(R.id.spBrand);
        spType = root.findViewById(R.id.spType);
        spCountry = root.findViewById(R.id.spCountry);
        etSearchTobacco = root.findViewById(R.id.etSearchTobacco);
        btnMost = root.findViewById(R.id.btnMost);
        btnBest = root.findViewById(R.id.btnBest);
        btnReset = root.findViewById(R.id.btnReset);
        setEvent();
    }

    public void setEvent() {
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeSearchText();
                return false;
            }
        });

        etSearchTobacco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cri.setKeyword(String.valueOf(s));
                search();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spBrand.setSelection(0);
                spCountry.setSelection(0);
                spType.setSelection(0);
                etSearchTobacco.setText("");
                cri = new Criteria();
                search();
            }
        });

        btnMost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cri.setOrder("MOST");
                search();
            }
        });
        btnBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cri.setOrder("BEST");
                search();
            }
        });

        tobaccoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TobaccoVO vo = (TobaccoVO) parent.getAdapter().getItem(position);
                setTobaccoVIew(vo);
                closeSearchText();
            }
        });
        tobaccoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
                String token =pref.getString("token", null);
                if(token==null||token.length()<1){
                    Toast.makeText(getContext(),"작성 권한이 없습니다 로그인 이후 이용해주세요.",Toast.LENGTH_SHORT).show();
                    return true;
                }
                SmokelogVO vo = new SmokelogVO();
                MemberVO member = new MemberVO();
                TobaccoVO tobacco = (TobaccoVO)parent.getItemAtPosition(position);
                member.setToken(token);
                vo.setMember(member);
                vo.setTobacco(tobacco);
                AddSmokelogTask task = new AddSmokelogTask(getActivity(),root);
                task.execute(vo);
                return true;
            }
        });
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ComponentVO vo = (ComponentVO) parent.getAdapter().getItem(position);
                cri.setBId(vo.getId());
                search();
                closeSearchText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ComponentVO vo = (ComponentVO) parent.getAdapter().getItem(position);
                cri.setTId(vo.getId());
                search();
                closeSearchText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ComponentVO vo = (ComponentVO) parent.getAdapter().getItem(position);
                cri.setNId(vo.getId());
                search();
                closeSearchText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void search() {
        TobaccoListViewAdapter adapter = (TobaccoListViewAdapter) tobaccoListView.getAdapter();
        adapter.filter(cri);
    }
    public void closeSearchText(){
        imm.hideSoftInputFromWindow(etSearchTobacco.getWindowToken(), 0);
    }
    private void setTobaccoVIew(TobaccoVO vo) {
        closeSearchText();
        fragmentView = TobaccoViewFragment.newInstance(vo);
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        if (!fragmentView.isAdded()) {
            childFt.replace(R.id.fragmentTobaccoList, fragmentView);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        tobaccoTask.cancel(true);
        brandTask.cancel(true);
        typeTask.cancel(true);
        countryTask.cancel(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        tobaccoTask = new TobaccoTask(getActivity(), root);
        tobaccoTask.execute("http://ggi4111.cafe24.com/mobile/tobacco/list.json");
        brandTask = new ComponentTask(getActivity(), root, "brand");
        brandTask.execute("http://ggi4111.cafe24.com/mobile/component/list/brand.json");
        typeTask = new ComponentTask(getActivity(), root, "type");
        typeTask.execute("http://ggi4111.cafe24.com/mobile/component/list/type.json");
        countryTask = new ComponentTask(getActivity(), root, "country");
        countryTask.execute("http://ggi4111.cafe24.com/mobile/component/list/country.json");
    }
}