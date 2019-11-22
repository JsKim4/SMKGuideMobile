package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoVO {
    String infoId;
    String title;
    String content;
    String name;
    String date;
    String board;
}
