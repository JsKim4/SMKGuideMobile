package com.example.smkguide.domain;

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
        try{
            this.setUuid(jObject.getString("uuid"));
            this.setFileName(jObject.getString("fileName"));
            this.setUploadPath(jObject.getString("uploadPath"));
        }catch (Exception e){
            e.printStackTrace();
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
