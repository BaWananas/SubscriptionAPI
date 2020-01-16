package com.learnings.learningproject.services.implementations;

import com.learnings.learningproject.models.AppAuthority;
import com.learnings.learningproject.models.AppUser;
import com.learnings.learningproject.services.IAuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthServiceImpl implements IAuthService, UserDetailsService {

    private Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

    public AuthServiceImpl() {
        this.roles.add(new AppAuthority("ROLE_ADMIN"));
    }

    @Override
    public UserDetails getUserById(String id) {
        return new AppUser("user", "password", this.roles);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.getUserById(s);
    }
}
