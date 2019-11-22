package com.example.smkguide.ui.tobaccolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.smkguide.R;
import com.example.smkguide.domain.TobaccoVO;

public class TobaccoViewFragment extends Fragment {
    public static TobaccoViewFragment newInstance(TobaccoVO vo) {
        TobaccoViewFragment fragment = new TobaccoViewFragment();
        Bundle args = new Bundle();
        args.putSerializable("TobaccoView", vo);
        fragment.setArguments(args);
        return fragment;
    }


    View root;
    LinearLayout topLayout;
    TextView tvTobaccoName, tvPrice, tvQuantity, tvTar, tvNicotine, tvCountry, tvCompany, tvBrand, tvType;
    ImageView imgTobacco;
    private final String URL="http://ggi4111.cafe24.com/display?fileName=";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccoview, container, false);
        init();
        return root;
    }

    public void init() {
        TobaccoVO vo = (TobaccoVO) getArguments().get("TobaccoView");
        topLayout = root.findViewById(R.id.tobaccoViewTopLayout);
        tvTar = root.findViewById(R.id.tvTar);tvTar.setText(String.valueOf(vo.getTar()));
        tvPrice = root.findViewById(R.id.tvPrice);tvPrice.setText(String.valueOf(vo.getPrice()));
        tvQuantity = root.findViewById(R.id.tvQuantity);tvQuantity.setText(String.valueOf(vo.getQuantity().intValue()));
        tvTobaccoName = root.findViewById(R.id.tvTobaccoName);tvTobaccoName.setText(vo.getTobaccoName());
        tvNicotine = root.findViewById(R.id.tvNicotine);tvNicotine.setText(String.valueOf(vo.getNicotine()));
        tvCompany = root.findViewById(R.id.tvCompany);tvCompany.setText(vo.getCompany().getName());
        tvCountry = root.findViewById(R.id.tvCountry);tvCountry.setText(vo.getCountry().getName());
        tvBrand = root.findViewById(R.id.tvBrand);tvBrand.setText(vo.getBrand().getName());
        tvType = root.findViewById(R.id.tvType);
        imgTobacco = root.findViewById(R.id.imgTobacco);  Glide.with(root).load(URL+vo.getAttach().getAttachFileName()).into(imgTobacco);
        setEvent();
    }

    public void setEvent() {
        topLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

}
