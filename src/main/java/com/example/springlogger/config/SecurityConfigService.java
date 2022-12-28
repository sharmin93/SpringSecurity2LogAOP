package com.example.springlogger.config;

import com.example.springlogger.filter.CustomAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfigService {

    @Autowired
    private UserDetailsService userService;
  @Autowired private AuthenticationConfiguration authConfiguration;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().
                authorizeHttpRequests()
                .requestMatchers("/api/v1/user/userRole").permitAll()
                .requestMatchers("/api/v1/user").permitAll()
                .requestMatchers("/api/v1/user/roleToUser").permitAll()
                .requestMatchers("/api/v1/user/UserByName").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/api/v1/welcome").permitAll()
                .anyRequest().authenticated().and().formLogin().permitAll()
                .and().exceptionHandling();
        http.addFilter(new CustomAuthenticationFilter(authenticationManager(authConfiguration)));

        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }
}
