package com.example.hr.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
