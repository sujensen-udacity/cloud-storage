package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationService authProvider;

    public SecurityConfig(AuthenticationService authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Restrict unauthorized users from accessing pages other than login and signup
        http.authorizeRequests()
                .antMatchers("/login", "/signup").permitAll()
                .anyRequest().authenticated();

        // Login page
        http.formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/home", true);;
    }

}
