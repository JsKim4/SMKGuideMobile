package com.example.smkguide.domain;

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

    public SmokeAreaVO(JSONObject jObject) throws JSONException {
        this.setAreaId(jObject.getLong("areaId"));
        this.setAreaName(jObject.getString("areaName"));
        this.setLatitude(jObject.getDouble("latitude"));
        this.setLongitude(jObject.getDouble("longitude"));
    }
}
