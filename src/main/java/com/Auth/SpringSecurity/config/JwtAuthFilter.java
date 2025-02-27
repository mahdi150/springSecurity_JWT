package com.Auth.SpringSecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContext;
import com.Auth.SpringSecurity.service.JwtUtils;
import com.Auth.SpringSecurity.service.OurUserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	 @Autowired
	    private JwtUtils jwtUtils;
	    @Autowired
	    private OurUserDetailsService ourUserDetailsService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	        final String authHeader = request.getHeader("Authorization");
	        final  String jwtToken;
	        final String userEmail;
	        if (authHeader == null || authHeader.isBlank()) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	        jwtToken = authHeader.substring(7);
	        userEmail = jwtUtils.extractUsername(jwtToken);
	        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);

	            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
	                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
	                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
	                        userDetails, null, userDetails.getAuthorities()
	                );
	                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                securityContext.setAuthentication(token);
	                SecurityContextHolder.setContext(securityContext);
	            }
	        }
	        filterChain.doFilter(request, response);
	    }
	
	

}
