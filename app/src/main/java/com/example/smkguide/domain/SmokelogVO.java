package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmokelogVO {
    private Long smokelogId;
    private TobaccoVO tobacco;
    private MemberVO member;
    private String cdate;
}
