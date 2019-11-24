package com.example.smkguide.domain;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {		//담배의 comment
    Long commentId;				//코멘트 고유번호
    TobaccoVO tobacco;			//작성된 담배
    MemberVO member;			//작성 사용자
    String content;				//내용
    Date cdate;					//작성일

    public String getCdateToString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(this.getCdate());
        return strDate;
    }
    public CommentVO(JSONObject jObject) {
        Log.d("commentjson",jObject.toString());
        try {
            this.setCommentId(jObject.getLong("commentId"));
            this.setContent(jObject.getString("content"));
            this.setCdate(new Date(jObject.getInt("cdate")));
            this.setTobacco(getTobacco(jObject.getJSONObject("tobacco")));
            this.setMember(getMember(jObject.getJSONObject("member")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TobaccoVO getTobacco(JSONObject jsonObject) throws JSONException {
        TobaccoVO vo = new TobaccoVO(jsonObject);
        return vo;
    }
    public MemberVO getMember(JSONObject jsonObject) throws JSONException {
        MemberVO vo = new MemberVO(jsonObject);
        return vo;
    }
}