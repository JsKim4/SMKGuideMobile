package com.example.smkguide.domain;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoVO {
    String infoId;
    String title;
    String content;
    String name;
    String date;
    String board;
    public InfoVO(JSONObject jObject){
        try{
            this.setName(jObject.getString("name"));
            this.setTitle(jObject.getString("title"));
            this.setContent(jObject.getString("content"));
            this.setInfoId(jObject.getString("infoId"));
            this.setBoard(jObject.getString("board"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
