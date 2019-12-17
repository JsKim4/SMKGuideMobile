package com.mjc.smkguide.domain;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings("serial")
public class TobaccoVO implements Serializable  {        //담배
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
    private int gradeSum;
    private int gradeNum;



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
        } catch (JSONException e) {
        }
        try {
            this.setTar(jObject.getDouble("tar"));
        } catch (JSONException e) {
        }
        try {
            this.setNicotine(jObject.getDouble("nicotine"));
        } catch (JSONException e) {
        }
        try {
            this.setPrice(jObject.getLong("price"));
        } catch (JSONException e) {
        }
        try {
            this.setQuantity(jObject.getDouble("quantity"));
        } catch (JSONException e) {
        }
        try {
            this.setType(getComponent(jObject.getJSONObject("type")));
        } catch (JSONException e) {
        }
        try {
            this.setBrand(getComponent(jObject.getJSONObject("brand")));
        } catch (JSONException e) {
        }
        try {
            this.setCompany(getComponent(jObject.getJSONObject("company")));
        } catch (JSONException e) {
        }
        try {
            this.setCountry(getComponent(jObject.getJSONObject("country")));
        } catch (JSONException e) {
        }
        try {
            this.setCommentCnt(jObject.getInt("commentCnt"));
        } catch (JSONException e) {
        }
        try {
            this.setAttach(getAttach(jObject.getJSONObject("attach")));
        } catch (JSONException e) {
        }

        try {
            this.setTobaccoId(jObject.getLong("tobaccoId"));
        } catch (JSONException e) {
        }
        try {
            this.setTobaccoName(jObject.getString("tobaccoName"));
        } catch (JSONException e) {
        }
        try {
            this.setGradeNum(jObject.getInt("gradeNum"));
        } catch (JSONException e) {
        }
        try {
            this.setGradeSum(jObject.getInt("gradeSum"));
        } catch (JSONException e) {
        }

    }

    public JSONObject tobaccoToJson(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("tobaccoId",tobaccoId);
        }catch (Exception e){
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

}