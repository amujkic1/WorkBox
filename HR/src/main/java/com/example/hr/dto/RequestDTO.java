package com.example.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
    public Integer id;
    private String type;
    private String text;
    private Date date;
    private String status;
    private Integer userId;
}