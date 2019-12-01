package com.example.smkguide.domain;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;

@Data
public class GradeVO {
    private MemberVO member;
    private TobaccoVO tobacco;
    private Long score;
    public GradeVO(JSONObject jObject) {
        try {
            this.setScore(jObject.getLong("score"));
            this.setTobacco(getTobacco(jObject.getJSONObject("tobacco")));
            this.setMember(getMember(jObject.getJSONObject("member")));
        } catch (JSONException e) {
            Log.d("error",e.toString());
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
