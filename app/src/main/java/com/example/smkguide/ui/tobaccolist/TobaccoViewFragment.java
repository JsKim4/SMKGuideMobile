package com.example.smkguide.ui.tobaccolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.TobaccoVO;
import com.example.smkguide.task.CommentTask;
import com.example.smkguide.task.CommentWriteTask;

import org.w3c.dom.Comment;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    EditText etCommentBox;
    RecyclerView recyclerPagination;
    ImageView imgTobacco;
    CommentTask commentTask;
    TobaccoVO vo;
    LinearLayout addWriting, layoutComment;
    ImageView upDownArrow;
    boolean upDownStatus = false;
    String token;
    Button btnCommentSubmit;
    InputMethodManager imm;
    private final String URL="http://ggi4111.cafe24.com/display?fileName=";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccoview, container, false);
        init();
        SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
        token =pref.getString("token", null);
        return root;
    }

    public void init() {
        vo = (TobaccoVO) getArguments().get("TobaccoView");
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
        recyclerPagination = root.findViewById(R.id.recyclePagination);
        addWriting = root.findViewById(R.id.addWriting);
        layoutComment = root.findViewById(R.id.layoutComment);
        upDownArrow = root.findViewById(R.id.imgDownUp);
        btnCommentSubmit = root.findViewById(R.id.btnCommentSubmit);
        etCommentBox = root.findViewById(R.id.etCommentBox);
        imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        setEvent();
        getComment(vo,"1");
    }

    public void setEvent() {



        topLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        addWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upDownStatus){
                    btnCommentSubmit.setVisibility(View.GONE);
                    layoutComment.setVisibility(View.GONE);
                    upDownArrow.setImageResource(R.drawable.img_arrow_down);
                    closeSearchText();
                }else{
                    btnCommentSubmit.setVisibility(View.VISIBLE);
                    layoutComment.setVisibility(View.VISIBLE);
                    upDownArrow.setImageResource(R.drawable.img_arrow_up);
                }
                upDownStatus=!upDownStatus;
            }
        });

        btnCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("token",token);
                if(token.length()<1)
                    return;
                String content = etCommentBox.getText().toString();
                CommentWriteTask task = new CommentWriteTask(getActivity(),root);
                CommentVO comment = new CommentVO();
                comment.setTobacco(vo);
                MemberVO member = new MemberVO();
                member.setToken(token);
                comment.setMember(member);
                comment.setContent(content);
                task.execute(comment);
                getComment(vo,"1");
                btnCommentSubmit.setVisibility(View.GONE);
                layoutComment.setVisibility(View.GONE);
                upDownArrow.setImageResource(R.drawable.img_arrow_down);
                closeSearchText();

            }
        });
    }
    public void closeSearchText(){
        imm.hideSoftInputFromWindow(etCommentBox.getWindowToken(), 0);
    }
    public void getComment(TobaccoVO vo,String page){
        commentTask = new CommentTask(getActivity(),root);
        commentTask.execute("/"+vo.getTobaccoId()+"/"+page+".json");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPagination.setLayoutManager(layoutManager);
    }


}
