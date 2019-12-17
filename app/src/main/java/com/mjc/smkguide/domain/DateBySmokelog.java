package com.mjc.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateBySmokelog {
    String date;
    int cnt;

    public DateBySmokelog(JSONObject jObject) {
        try {
            this.setDate(jObject.getString("date"));
        } catch (JSONException e) {
        }
        try {
            this.setCnt(jObject.getInt("cnt"));
        } catch (JSONException e) {
        }
    }
    public DateBySmokelog(JSONObject jObject,int i) {
        try {
            this.setDate(jObject.getString("date"));
        } catch (JSONException e) {
        }
        try {
            this.setCnt(jObject.getInt("cnt"));
        } catch (JSONException e) {
        }
        setDate(String.valueOf(i));
    }
}
