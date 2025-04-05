package com.fct.visitation.models.dto;

import lombok.Data;
@Data
public class LoginRequest {
    // Make fields public to avoid getter/setter issues
    public String username;
    public String password;
}
