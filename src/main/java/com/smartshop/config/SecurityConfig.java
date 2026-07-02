package com.smartshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()
                .antMatchers("/login", "/403", "/error").permitAll()
                .antMatchers("/product", "/cart/**").authenticated()
                .antMatchers("/product/form", "/product/save", "/product/delete").hasRole("admin")
                .antMatchers("/category/**").hasRole("admin")
                .antMatchers("/order/form", "/order/save", "/order/delete").hasRole("admin")
                .antMatchers("/user/**").hasRole("admin")
                .antMatchers("/system/**").hasRole("admin")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
            .csrf().disable()
            .headers()
                .frameOptions().sameOrigin();

        return http.build();
    }
}
