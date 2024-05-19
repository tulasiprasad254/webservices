package com.techies.dtlr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(1) // Specify a different order value
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("username")
            .password("password") // Passwords must be encoded. "{noop}" means plaintext.
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 // Disable CSRF protection
        http.csrf().disable();

        http
            .authorizeRequests()
                .antMatchers("/Auth/**").permitAll()
                .antMatchers("/getproducts/**").permitAll()
                .antMatchers("/itemsuploads/**").permitAll()
                .antMatchers("/custorders/**").permitAll()     
                .antMatchers("/admincusttailorders/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }
}

