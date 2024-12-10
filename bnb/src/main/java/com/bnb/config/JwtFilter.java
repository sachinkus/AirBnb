package com.bnb.config;


import com.bnb.entity.User;
import com.bnb.repository.UserRepository;
import com.bnb.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private UserRepository userRepository;
    private JWTService jwtService;
    public JwtFilter(JWTService jwtService,UserRepository userRepository) {
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException
    {
        System.out.println("outSide Bearer");
        String token=request.getHeader("Authorization");
        System.out.println(token);
        if(token!=null && token.startsWith("Bearer "))
        {
            token=token.substring(8,token.length()-1);
            System.out.println("Inside user name:"+token);
            String username=jwtService.getUsername(token);
            Optional<User> opUser=this.userRepository.findByUsername(username);
                if(opUser.isPresent()){
                    User user=opUser.get();
                    UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

        }
        filterChain.doFilter(request,response);

    }
}
