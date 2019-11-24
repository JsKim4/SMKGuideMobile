package com.example.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private Long memberId;			//사용자 고유번호
    private String email;			//사용자 이메일
    private String memberName;		//사용자 이름
    public MemberVO(JSONObject jObject){
        try {
            this.setMemberName(jObject.getString("memberName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            try {
                this.setMemberId(jObject.getLong("memberId"));
                this.setEmail(jObject.getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
