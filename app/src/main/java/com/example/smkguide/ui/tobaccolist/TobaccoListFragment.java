package com.example.smkguide.ui.tobaccolist;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.example.smkguide.Adpter.TobaccoListViewAdapter;
import com.example.smkguide.R;
import com.example.smkguide.domain.ComponentVO;
import com.example.smkguide.domain.Criteria;
import com.example.smkguide.domain.TobaccoVO;
import com.example.smkguide.task.ComponentTask;
import com.example.smkguide.task.TobaccoTask;

import org.w3c.dom.Text;


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
    Spinner spBrand, spType, spCountry;
    Button btnMost,btnBest;
    EditText etSearchTobacco;
    Criteria cri = new Criteria();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccolist, container, false);
        Init();
        return root;
    }

    public void Init() {
        tobaccoListView = root.findViewById(R.id.listTobacco);
        spBrand = root.findViewById(R.id.spBrand);
        spType = root.findViewById(R.id.spType);
        spCountry = root.findViewById(R.id.spCountry);
        etSearchTobacco = root.findViewById(R.id.etSearchTobacco);
        btnMost = root.findViewById(R.id.btnMost);
        btnBest = root.findViewById(R.id.btnBest);
        setEvent();
    }

    public void setEvent() {
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
                cri.setOrder("");
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

    public void search() {
        TobaccoListViewAdapter adapter = (TobaccoListViewAdapter) tobaccoListView.getAdapter();
        adapter.filter(cri);
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