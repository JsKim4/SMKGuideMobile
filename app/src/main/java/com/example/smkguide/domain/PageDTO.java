package com.example.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDTO {				//페이지 처리를 위한 DTO
    private int startPage;			//시작 페이지
    private int endPage;			//끝페이지

    private boolean prev, next;		//전페이지(startPage-1) 후페이지(endPage+1) 사용 여부

    private int total;				//전체 게시글 수
    private Criteria cri;			//페이지당 개수, 전달받은 페이지 번호 사용

    public PageDTO(Criteria cri, int total) {		//초기화
        this.cri = cri;
        this.total = total;

        this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;		//현재 페이지 1의자리수 올림
        this.startPage = this.endPage - 9;									//마지막 페이지 -9
        int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));		//실제 마지막 페이지 번호

        if(realEnd<this.endPage)				//마지막페이지보다 현재 페이지가 클 경우
            this.endPage = realEnd;

        this.prev = this.startPage > 1;			//시작페이지가 첫째 페이지보다 클경우 prev사용

        this.next = this.endPage < realEnd;		//현재 마지막 페이지보다  실제 마지막 페이지가 클 경우 next 사용
    }

    public PageDTO(JSONObject jsonObject){
        try {
            this.setStartPage(jsonObject.getInt("startPage"));
            this.setEndPage(jsonObject.getInt("endPage"));
            this.setPrev(jsonObject.getBoolean("prev"));
            this.setNext(jsonObject.getBoolean("next"));
            this.setTotal(jsonObject.getInt("total"));
        } catch (JSONException e) {
        }

    }
}
