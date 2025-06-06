package com.loyalbridge.adminpanel.dto;

import com.loyalbridge.adminpanel.model.Role;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}

