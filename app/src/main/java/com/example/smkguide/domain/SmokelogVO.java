package com.example.smkguide.domain;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmokelogVO {
    private Long smokelogId;
    private TobaccoVO tobacco;
    private MemberVO member;
    private String cdate;

    public SmokelogVO(JSONObject jObject) {
        try {
            this.setTobacco(getTobacco(jObject.getJSONObject("tobacco")));
            this.setMember(getMember(jObject.getJSONObject("member")));
            Date date = new Date(Long.valueOf(jObject.getString("cdate")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+9")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            Log.d(formattedDate,formattedDate);
            this.setCdate(formattedDate);
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
    public JSONObject smokelogToJson(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("tobacco",tobacco.tobaccoToJson());
        } catch (JSONException e) {
        }
        try {
            jsonObject.putOpt("member",member.tokenToJson());
        } catch (JSONException e) {
        }
        return jsonObject;
    }
}
