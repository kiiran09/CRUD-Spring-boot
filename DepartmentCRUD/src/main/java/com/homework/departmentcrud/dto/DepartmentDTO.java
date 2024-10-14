package com.homework.departmentcrud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private long id;

    @NotBlank(message = "Field Required for Department - title")
    @Size(min = 3, max = 50, message = "Department's title should be in a range - [3,50]")
    private String title;

    @JsonProperty("isActive")
    private boolean isActive;

    @PastOrPresent(message = "Creation data of Department can not be in FUTURE")
    private LocalDate createdAt;
}
