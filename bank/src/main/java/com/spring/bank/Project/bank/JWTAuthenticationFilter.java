package com.spring.bank.Project.bank;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String readHeader=request.getHeader("Authorization");
        if (readHeader==null || readHeader.startsWith("Bearer") ){
            filterChain.doFilter(request,response);
            return;
        }
        String token=readHeader.substring(7);
        try{
            String email =jwtService.extractEmail(token);

            if (jwtService.isTokenValid(token)){
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);
    }
}
