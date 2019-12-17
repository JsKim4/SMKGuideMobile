package com.mjc.smkguide.domain;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    public JSONObject gradeToJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("tobacco",tobacco.tobaccoToJson());
        } catch (JSONException e) {
        }
        try {
            jsonObject.putOpt("member",member.tokenToJson());
        } catch (JSONException e) {
        }
        try {
            jsonObject.putOpt("score",score);
        } catch (JSONException e) {
        }
        return jsonObject;
    }
}
