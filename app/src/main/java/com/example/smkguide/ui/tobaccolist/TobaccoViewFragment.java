package com.example.smkguide.ui.tobaccolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smkguide.R;
import com.example.smkguide.domain.CommentVO;
import com.example.smkguide.domain.GradeVO;
import com.example.smkguide.domain.MemberVO;
import com.example.smkguide.domain.TobaccoVO;
import com.example.smkguide.task.comment.CommentTask;
import com.example.smkguide.task.comment.CommentWriteTask;
import com.example.smkguide.task.grade.AddGradeTask;
import com.example.smkguide.task.grade.GradeTask;

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
    TextView tvTobaccoName, tvPrice, tvQuantity, tvTar, tvNicotine, tvCountry, tvCompany, tvBrand, tvType,tvGrade;
    EditText etCommentBox;
    RecyclerView recyclerPagination;
    ImageView imgTobacco;
    CommentTask commentTask;
    TobaccoVO vo;
    LinearLayout addWriting, layoutComment;
    ImageView upDownArrow;
    boolean upDownStatus = false;
    String token;
    Button btnCommentSubmit,btnGrade;
    Spinner spGrade;
    InputMethodManager imm;
    private final String URL="http://ggi4111.cafe24.com/display?fileName=";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tobaccoview, container, false);
        init();
        return root;
    }

    public void init() {
        SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
        token =pref.getString("token", null);
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
        tvType = root.findViewById(R.id.tvType);tvType.setText(vo.getType().getName());
        tvGrade = root.findViewById(R.id.tvGrade);
        if(vo.getGradeNum()==0)
            tvGrade.setText("Non Grade");
        else
            tvGrade.setText(String.valueOf(Math.ceil(((double)vo.getGradeSum()/vo.getGradeNum())*10)/10.0));
        //spGrade = root.findViewById(R.id.spGrade);
        ArrayList<String> gradeList = new ArrayList<>(); for(int i=5;i>=1;i--)gradeList.add(String.valueOf(i));
        SpinnerAdapter gradeAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,gradeList);
        spGrade.setAdapter(gradeAdapter);
        //btnGrade = root.findViewById(R.id.btnGrade);
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
                if(token==null||token.length()<1) {
                    Toast.makeText(getContext(),"작성 권한이 없습니다 로그인 이후 이용해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
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
                Toast.makeText(getContext(),"작성하였습니다.",Toast.LENGTH_SHORT).show();
                closeSearchText();
                etCommentBox.setText("");
            }
        });
        btnGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("user-info", getActivity().MODE_PRIVATE);
                String token =pref.getString("token", null);
                if(token==null||token.length()<1){
                    Toast.makeText(getContext(),"작성 권한이 없습니다 로그인 이후 이용해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                GradeVO gradeVO = new GradeVO();
                gradeVO.setScore((long)Integer.valueOf(spGrade.getSelectedItem().toString()));
                MemberVO member = new MemberVO();
                member.setToken(token);
                gradeVO.setMember(member);
                gradeVO.setTobacco(vo);
                AddGradeTask task = new AddGradeTask(getActivity(),root);
                task.execute(gradeVO);
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
