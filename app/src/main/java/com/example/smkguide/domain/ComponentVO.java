package com.example.smkguide.domain;

import lombok.Data;

@Data
public class ComponentVO {	//company, country, brand, type 묶음
    Long id;			// 타입별 고유번호
    String name;		// 타입별 이름
    String type;		// 어느 table 참조인지 정함
    public ComponentVO(String type) {
        this.type = type;
    }
}