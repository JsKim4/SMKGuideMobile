package com.mjc.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmokeAreaVO {
    private Long areaId;
    private String areaName;
    private double latitude;
    private double longitude;

    public SmokeAreaVO(JSONObject jObject){
        try {
            this.setAreaId(jObject.getLong("areaId"));
        } catch (JSONException e) {
        }
        try {
            this.setAreaName(jObject.getString("areaName"));
        } catch (JSONException e) {
        }
        try {
            this.setLatitude(jObject.getDouble("latitude"));
        } catch (JSONException e) {
        }
        try {
            this.setLongitude(jObject.getDouble("longitude"));
        } catch (JSONException e) {
        }
    }
}
