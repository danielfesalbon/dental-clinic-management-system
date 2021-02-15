package com.rest.app.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.rest.app.util.EventUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilterUtil extends OncePerRequestFilter {

	@Autowired
	private AuthRepository authService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private EventUtil event;

	@SuppressWarnings("static-access")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(token);
				if (username != null && jwtUtil.validateToken(token, authService.loadUserByUsername(username))) {
					logger.info("still valid");
				}
			} catch (IllegalArgumentException e) {
				logger.error("Token not available");
			} catch (ExpiredJwtException e) {
				logger.error("Expired token");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails user = authService.loadUserByUsername(username);

			if (jwtUtil.validateToken(token, user)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(
				(HttpServletResponse) response);

		filterChain.doFilter(request, responseCacheWrapperObject);

		byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
		String responseString = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());

		event.LOG_EVENT(username, event.write(responseString));

		responseCacheWrapperObject.copyBodyToResponse();
	}

}