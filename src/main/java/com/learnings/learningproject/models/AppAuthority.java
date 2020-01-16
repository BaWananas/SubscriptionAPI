package com.learnings.learningproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
public class AppAuthority implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
