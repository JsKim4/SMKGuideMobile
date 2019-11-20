package com.example.smkguide.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoVO {
    String notice;
    String content;
    String name;
    String date;
}
