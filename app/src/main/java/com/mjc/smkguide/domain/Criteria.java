package com.mjc.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {			//담배 검색용 객체
    private int pageNum;		//담배 페이지 번호
    private int amount;			//페이지당 갯수
    private int startIndex;		//시작번호

    private String type;		//검색 타입
    private String keyword;		//검색 키워드
    private String order;
    private Long bId;
    private Long nId;
    private Long mId;
    private Long tId;

    public Criteria() {
        this(1, 10);
    }

    public void setStartIndex() {
        this.startIndex = (this.getPageNum()-1)*this.getAmount();
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        startIndex = (pageNum - 1) * amount;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        try {
            json.put("type",type);
            json.put("keyword",keyword);
            json.put("bid",bId);
            json.put("nid",nId);
            json.put("tid",tId);
            json.put("mid",mId);
            json.put("order",order);
            json.put("amount",amount);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String[] getTypeArr() {
        return type==null ? new String[] {}: type.split("");
    }
}