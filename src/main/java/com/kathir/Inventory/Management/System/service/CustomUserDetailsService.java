package com.kathir.Inventory.Management.System.service;

import com.kathir.Inventory.Management.System.entity.User;
import com.kathir.Inventory.Management.System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username)
                        throws UsernameNotFoundException {

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                String roleWithoutPrefix = user.getRole().name().startsWith("ROLE_") 
                        ? user.getRole().name().substring(5) 
                        : user.getRole().name();

                return org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUsername())
                                .password(user.getPassword())
                                .roles(roleWithoutPrefix)
                                .build();
        }
}