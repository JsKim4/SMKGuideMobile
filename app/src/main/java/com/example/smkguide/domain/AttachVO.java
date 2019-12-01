package com.example.smkguide.domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttachVO implements Serializable{
    private String uuid;
    private String uploadPath;
    private String fileName;

    private Long tobaccoId;
    public AttachVO(JSONObject jObject){
        try {
            this.setUuid(jObject.getString("uuid"));
        } catch (JSONException e) {
        }
        try {
            this.setFileName(jObject.getString("fileName"));
        } catch (JSONException e) {
        }
        try {
            this.setUploadPath(jObject.getString("uploadPath"));
        } catch (JSONException e) {
        }
    }
    public String getAttachFileName() {
        if(uuid!=null&&uuid.length()>0) {
            return this.uploadPath.replace("\\", "/")+"/"+this.uuid+"_"+this.fileName;
        }
        return "";
    }

    public String getThumbnailFileName() {
        if(uuid!=null&&uuid.length()>0) {
            return this.uploadPath.replace("\\", "/")+"/S_"+this.uuid+"_"+this.fileName;
        }
        return "";
    }

}
