package com.kathir.Inventory.Management.System.config;

import com.kathir.Inventory.Management.System.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        private final CustomUserDetailsService userDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authenticationProvider(authenticationProvider())
                                .authorizeHttpRequests(auth -> auth
                                                // Public resources
                                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                                .requestMatchers("/login").permitAll()

                                                // Admin only — user management
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                // Admin & Manager — write operations on inventory
                                                .requestMatchers("/products/new", "/products/save", "/products/edit/**",
                                                                "/products/delete/**")
                                                .hasAnyRole("ADMIN", "MANAGER")
                                                .requestMatchers("/categories/new", "/categories/save",
                                                                "/categories/edit/**", "/categories/delete/**")
                                                .hasAnyRole("ADMIN", "MANAGER")
                                                .requestMatchers("/suppliers/new", "/suppliers/save",
                                                                "/suppliers/edit/**", "/suppliers/delete/**")
                                                .hasAnyRole("ADMIN", "MANAGER")

                                                // Admin & Manager — purchase orders and reports
                                                .requestMatchers("/purchase-orders/new", "/purchase-orders/save",
                                                                "/purchase-orders/delete/**")
                                                .hasAnyRole("ADMIN", "MANAGER")
                                                .requestMatchers("/reports/**").hasAnyRole("ADMIN", "MANAGER")

                                                // All authenticated — read-only views, dashboard, sales, stock logs
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/dashboard", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll());

                return http.build();
        }
}