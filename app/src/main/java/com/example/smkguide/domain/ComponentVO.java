package com.example.smkguide.domain;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentVO {	//company, country, brand, type 묶음
    Long id;			// 타입별 고유번호
    String name;		// 타입별 이름
    String type;		// 어느 table 참조인지 정함
    public ComponentVO(String type) {
        this.type = type;
    }
    public ComponentVO(JSONObject jObject){
        try{
            this.setName(jObject.getString("name"));
            this.setId(jObject.getLong("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}