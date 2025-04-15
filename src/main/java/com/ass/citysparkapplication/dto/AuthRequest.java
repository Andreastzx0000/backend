package com.ass.citysparkapplication.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
