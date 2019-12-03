package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDateBySmokelog {
    String dateFormat;
    String startDate;
    String endDate;
    public String getSearchDateToUrl(){
        return "/"+dateFormat+"/"+startDate+"/"+endDate+".json";
    }
}
