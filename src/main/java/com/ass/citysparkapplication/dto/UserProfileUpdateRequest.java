package com.ass.citysparkapplication.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserProfileUpdateRequest {
    private String contactNo;
    private String description;
    private LocalDate birthday;
    private Integer imageId;
    private List<Integer> preferences;
}
