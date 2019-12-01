package com.example.smkguide.domain;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings("serial")
public class TobaccoVO implements Serializable,Comparable  {        //담배
    private Long tobaccoId;                //담배고유번호
    private String tobaccoName;            //담배이름
    @Nullable
    private Boolean deleteFlag;            //삭제여부
    private ComponentVO country;        //국가
    private ComponentVO company;        //회사
    private ComponentVO type;            //타입
    private ComponentVO brand;            //브랜드
    private Double tar;                    //타르 함유량
    private Double nicotine;            //니코틴 함유량
    private Double quantity;            //개수
    private Long price;                    //가격
    private int commentCnt;                //comment 개수
    private AttachVO attach;            //첨부 이미지



    public TobaccoVO() {
        this(-1L);
    }

    public TobaccoVO(Long tobaccoId) {
        this.setQuantity(20D);
        this.setPrice(4500L);
        this.setTobaccoId(tobaccoId);
        this.setDeleteFlag(false);
    }

    public TobaccoVO(JSONObject jObject){
        try {
            this.setDeleteFlag(jObject.getBoolean("deleteFlag"));
            this.setTar(jObject.getDouble("tar"));
            this.setNicotine(jObject.getDouble("nicotine"));
            this.setPrice(jObject.getLong("price"));
            this.setQuantity(jObject.getDouble("quantity"));
            this.setType(getComponent(jObject.getJSONObject("type")));
            this.setBrand(getComponent(jObject.getJSONObject("brand")));
            this.setCompany(getComponent(jObject.getJSONObject("company")));
            this.setCountry(getComponent(jObject.getJSONObject("country")));
            this.setCommentCnt(jObject.getInt("commentCnt"));
            this.setAttach(getAttach(jObject.getJSONObject("attach")));
        } catch (JSONException e) {
           // e.printStackTrace();
        } finally {
            try {
                this.setTobaccoId(jObject.getLong("tobaccoId"));
                this.setTobaccoName(jObject.getString("tobaccoName"));
            } catch (JSONException e) {
              //  e.printStackTrace();
            }
        }

    }

    public JSONObject tobaccoToJson(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("tobaccoId",tobaccoId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    public AttachVO getAttach(JSONObject jObject){
        AttachVO vo = new AttachVO(jObject);
        return vo;
    }
    public ComponentVO getComponent(JSONObject jObject){
        ComponentVO vo = new ComponentVO(jObject);
        return vo;
    }

    @Override
    public int compareTo(Object o) {
        TobaccoVO vo = (TobaccoVO)o;
        return vo.getCommentCnt()>this.getCommentCnt()? 1:vo.getCommentCnt() == this.getCommentCnt()?0:-1;
    }
}