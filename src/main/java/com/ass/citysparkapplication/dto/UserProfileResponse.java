package com.ass.citysparkapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private String email;
    private String contactNo;
    private String description;
    private LocalDate birthday;
    private Integer imageId;
    private List<String> preferences;
}
