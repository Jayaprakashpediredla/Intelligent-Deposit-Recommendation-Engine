package com.nexuswealth.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUserService {

    UserDetails loadUserByUsername(String username);
}