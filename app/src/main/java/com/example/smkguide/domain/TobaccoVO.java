package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TobaccoVO {        //담배
    private Long tobaccoId;                //담배고유번호
    private String tobaccoName;            //담배이름
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
    }
}