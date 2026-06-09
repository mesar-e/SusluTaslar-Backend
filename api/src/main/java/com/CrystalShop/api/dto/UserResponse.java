package com.CrystalShop.api.dto;

import com.CrystalShop.api.enums.Role;
import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
