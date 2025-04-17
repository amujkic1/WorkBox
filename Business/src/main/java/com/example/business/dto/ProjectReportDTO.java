package com.example.business.dto;

import java.util.Date;

public class ProjectReportDTO {
    private String title;
    private String status;

    public ProjectReportDTO() {}
    public ProjectReportDTO(String title, Date endDate) {
        this.title = title;
        this.status = status;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
