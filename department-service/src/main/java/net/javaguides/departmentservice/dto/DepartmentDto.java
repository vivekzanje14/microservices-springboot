package net.javaguides.departmentservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long id;

    @NotEmpty(message = "Department name should not be null or empty")
    private String deptName;

    @NotEmpty(message = "Department description should not be null or empty")
    private String deptDesc;

    @NotEmpty(message = "Department code should not be null or empty")
    private String deptCode;
}
