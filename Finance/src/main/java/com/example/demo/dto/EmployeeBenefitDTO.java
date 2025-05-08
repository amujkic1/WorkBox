package com.example.demo.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


public class EmployeeBenefitDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer EmployeeBenefitId;

    @NotNull(message = "EmployeeBenefit type cannot be null)")
    private String type;

    @NotNull(message = "EmployeeBenefit status cannot be null)")
    private String status;

    private String details;

    private Integer userId;

    public EmployeeBenefitDTO(Integer employeeBenefitId, String type, String status, String details, Integer userId) {
        EmployeeBenefitId = employeeBenefitId;
        this.type = type;
        this.status = status;
        this.details = details;
        this.userId = userId;
    }

    public EmployeeBenefitDTO() {
    }

    public Integer getEmployeeBenefitId() {
        return EmployeeBenefitId;
    }

    public void setEmployeeBenefitId(Integer employeeBenefitId) {
        EmployeeBenefitId = employeeBenefitId;
    }

    public @NotNull(message = "EmployeeBenefit type cannot be null)") String getType() {
        return type;
    }

    public void setType(@NotNull(message = "EmployeeBenefit type cannot be null)") String type) {
        this.type = type;
    }

    public @NotNull(message = "EmployeeBenefit status cannot be null)") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "EmployeeBenefit status cannot be null)") String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
