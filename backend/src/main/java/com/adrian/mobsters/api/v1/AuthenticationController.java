package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.repository.UserReactiveRepository;
import com.adrian.mobsters.security.AuthRequest;
import com.adrian.mobsters.security.AuthResponse;
import com.adrian.mobsters.security.JWTUtil;
import com.adrian.mobsters.security.PBKDF2Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PBKDF2Encoder passwordEncoder;

    @Autowired
    private UserReactiveRepository userReactiveRepository;

    @PostMapping
    public Mono<ResponseEntity<AuthResponse>> auth(@RequestBody AuthRequest ar) {
        return userReactiveRepository.findByUsername(ar.getUsername()).map((userDetails) -> {
            log.info("Attempting password check for: {}", passwordEncoder.encode(ar.getPassword()));
            if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        });
    }
}
