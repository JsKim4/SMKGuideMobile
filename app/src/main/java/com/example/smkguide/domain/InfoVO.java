package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoVO {
    String infoId;
    String title;
    String content;
    String name;
    String date;
    String board;
}
