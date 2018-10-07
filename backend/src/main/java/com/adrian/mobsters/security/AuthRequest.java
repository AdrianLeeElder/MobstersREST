package com.adrian.mobsters.security;

import lombok.Data;

@Data
public class AuthRequest {
    private final String username;
    private final String password;
}
