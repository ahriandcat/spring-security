package com.example.demosecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class FilterCustom extends OncePerRequestFilter {
    @Autowired
    private UserDetailServiceCustom userDetailServiceCustom;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //lay username trong session
        String userEmail = (String) request.getSession().getAttribute("MY_SESSION");

        UsernamePasswordAuthenticationToken authentication = getAuthentication(userEmail);

        //tao doi tuong xac thuc
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        //luu vao context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String email){
        if (email == null){
            return null;
        }
        UserDetails user = userDetailServiceCustom.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
    }
}
