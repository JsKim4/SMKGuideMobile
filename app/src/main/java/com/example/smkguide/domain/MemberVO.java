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
    private String password;
    private String address;
    private String telephone;
    private String token;
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

    public JSONObject memberToJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("email",this.getEmail());
            object.put("password",this.getPassword());
            object.put("memberName",this.getMemberName());
            object.put("address",this.getAddress());
            object.put("telephone",this.getTelephone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    public JSONObject tokenToJson(){
        JSONObject object = new JSONObject();
        try {
            object.put("token",this.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
