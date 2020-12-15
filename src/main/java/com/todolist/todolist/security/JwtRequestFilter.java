package com.todolist.todolist.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todolist.todolist.Service.CustomUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String jwtToken = request.getHeader("Authorization");
		String email = null;
		String jwt = null;

		if (StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer ")) {
			jwt = jwtToken.substring(7);
		}

		if (StringUtils.hasText(jwt) && jwtUtil.isTokenValid(jwt)) {
			email = jwtUtil.extractUsername(jwt);
			if (StringUtils.hasText(email)) {
				try {
					UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
					if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				} catch (UsernameNotFoundException ex) {
					throw new BadCredentialsException("Invalid Credentials");
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
