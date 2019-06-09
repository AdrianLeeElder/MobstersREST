package com.adrian.mobsters.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    @GetMapping
    public Principal getUser(Principal principal) {
        return principal;
    }
}
