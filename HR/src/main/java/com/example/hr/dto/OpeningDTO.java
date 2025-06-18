package com.example.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningDTO {

    private Integer id;
    private String openingName;
    private String description;
    private String conditions;
    private String benefits;
    private Date startDate;
    private Date endDate;
    private String result;
    private Integer userId;
    private Integer applicationCount = 0;

    public OpeningDTO(Integer id, String openingName, String description, String conditions, String benefits,
                      Date startDate, Date endDate, String result, Integer userId) {
        this.id = id;
        this.openingName = openingName;
        this.description = description;
        this.conditions = conditions;
        this.benefits = benefits;
        this.startDate = startDate;
        this.endDate = endDate;
        this.result = result;
        this.userId = userId;
    }

}