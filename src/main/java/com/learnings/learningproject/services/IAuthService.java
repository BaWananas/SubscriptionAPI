package com.learnings.learningproject.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    public UserDetails getUserById(String id);
}
