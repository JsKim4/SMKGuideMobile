package com.example.smkguide.ui.tobaccolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smkguide.R;
import com.example.smkguide.domain.ComponentVO;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;
import com.example.smkguide.task.ComponentTask;
import com.example.smkguide.task.TobaccoTask;


public class TobaccoListFragment extends Fragment {

    public static TobaccoListFragment newInstance() {
        return new TobaccoListFragment();
    }

    TobaccoTask tobaccoTask;
    ComponentTask brandTask;
    ComponentTask typeTask;
    ComponentTask countryTask;
    View root;
    Fragment fragmentView;
    ListView tobaccoListView;
    Spinner spBrand;
    Spinner spType;
    Spinner spCountry;
    Button btnSearch;
    EditText etSearchTobacco;
    Criteria cri = new Criteria();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccolist, container, false);
        Init();
        return root;
    }
    public void Init(){
        tobaccoListView = root.findViewById(R.id.listTobacco);
        spBrand = root.findViewById(R.id.spBrand);
        spType = root.findViewById(R.id.spType);
        spCountry = root.findViewById(R.id.spCountry);
        btnSearch = root.findViewById(R.id.btnSearchTobacco);
        etSearchTobacco = root.findViewById(R.id.etSearchTobacco);
        setEvent();
    }
    public void setEvent(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cri.setKeyword(etSearchTobacco.getText().toString());
                search();
            }
        });

        tobaccoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TobaccoVO vo = (TobaccoVO) parent.getAdapter().getItem(position);
                setTobaccoVIew(vo);
            }
        });
        spBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ComponentVO vo = (ComponentVO) parent.getAdapter().getItem(position);
                cri.setBId(vo.getId());
                search();
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void search(){
        cri.setType("");
        if(cri.getBId()!=null&&cri.getBId()!=0L)
            cri.setType(cri.getType()+"B");
        if(cri.getTId()!=null&&cri.getTId()!=0L)
            cri.setType(cri.getType()+"T");
        if(cri.getNId()!=null&&cri.getNId()!=0L)
            cri.setType(cri.getType()+"N");
        tobaccoTask = new TobaccoTask(getActivity(), root, cri);
        tobaccoTask.execute("http://ggi4111.cafe24.com/mobile/tobacco/list.json");
    }

    private void setTobaccoVIew(TobaccoVO vo) {
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
        search();
        brandTask = new ComponentTask(getActivity(), root, "brand");
        brandTask.execute("http://ggi4111.cafe24.com/mobile/component/list/brand.json");
        typeTask = new ComponentTask(getActivity(), root, "type");
        typeTask.execute("http://ggi4111.cafe24.com/mobile/component/list/type.json");
        countryTask = new ComponentTask(getActivity(), root, "country");
        countryTask.execute("http://ggi4111.cafe24.com/mobile/component/list/country.json");
    }
}