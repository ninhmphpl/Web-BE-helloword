package com.spring.web.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private String role;

    public JwtResponse(String accessToken, Long id, String username, String name, String roles) {
        this.token = accessToken;
        this.username = username;
        this.role = roles;
        this.name = name;
        this.id = id;
    }
}
