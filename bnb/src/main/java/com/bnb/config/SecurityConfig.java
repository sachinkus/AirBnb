package com.bnb.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private JwtFilter jwtFilter;
    public  SecurityConfig(JwtFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    )throws  Exception {
        //h(cd)2
        http.csrf().disable().cors().disable();
        //haap Securitiy
       http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/city/**","/api/v1/country/**")
                .permitAll()
                .requestMatchers("/api/v1/rooms/**","/api/v1/images/getimagebypropertyId","/api/v1/images/addImage")
                .hasRole("OWNER")
                .requestMatchers("/api/v1/auth/createuser","/api/v1/auth/login","/api/v1/auth/createpropertyowner")
                .permitAll()
                .requestMatchers("/api/v1/booking/**","/api/v1/review/createreview","/api/v1/review/userreviews")
                .hasRole("USER")
                .requestMatchers("/api/v1/auth/createpropertymanager","/api/v1/images/removeimage")
                .hasAnyRole("ADMIN","OWNER")
                .requestMatchers("/api/v1/property/**")
                .hasRole("OWNER")
                .anyRequest().permitAll();

//                .requestMatchers("/api/v1/auth/createuser","/api/v1/auth/login","/api/v1/auth/createpropertyowner")
//                .permitAll()
//                .requestMatchers("/api/v1/city/**")
//                .permitAll()
//                .requestMatchers("/api/v1/auth/createpropertymanager")
//                .hasAnyRole("ADMIN","OWNER")
//                .requestMatchers("/api/auth/property/addProperty")
//                .hasRole("ADMIN")
//                .anyRequest().authenticated();
        return http.build();
    }
}
