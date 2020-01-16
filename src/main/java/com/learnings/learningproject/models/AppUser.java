package com.learnings.learningproject.models;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Setter
@AllArgsConstructor
public class AppUser implements UserDetails {

    private String username;
    private String password;
    private boolean isExpired = false;
    private boolean isLocked = false;
    private boolean isEnabled = true;
    private boolean isCredExpired = false;
    private Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

    public AppUser(String username, String password, Collection<GrantedAuthority> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isCredExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
