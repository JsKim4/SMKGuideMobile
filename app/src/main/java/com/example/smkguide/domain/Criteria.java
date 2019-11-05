package com.example.smkguide.domain;

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

    public String[] getTypeArr() {
        return type==null ? new String[] {}: type.split("");
    }
}