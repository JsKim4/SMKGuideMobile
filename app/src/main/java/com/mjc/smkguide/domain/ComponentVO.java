package com.mjc.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentVO implements Serializable {	//company, country, brand, type 묶음
    Long id;			// 타입별 고유번호
    String name;		// 타입별 이름
    String type;		// 어느 table 참조인지 정함
    public ComponentVO(String type) {
        this.type = type;
    }
    public ComponentVO(JSONObject jObject){
        try{
            this.setName(jObject.getString("name"));
        }catch (Exception e){
        }
        try {
            this.setId(jObject.getLong("id"));
        } catch (JSONException e) {
        }
    }
}