package com.veljkoilic.instagramclone.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtServiceImpl jwtService;
	private final UserDetailsService userDetailsService;

	// This method intercepts every request. We can extract data, set data to
	// headers. Contains list of other filters in the chain
	// in the filterChain parameter.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extracting authentication header.
		final String authHeader = request.getHeader("Authorization");
		final String username;

		// Do an early return if there is no authentication header.
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extracting token from authentication header. Starting from the index 7
		// because first 7 characters are "Bearer ".
		final String jwt = authHeader.substring(7);

		// Extracting username from the token.
		username = jwtService.extractUsername(jwt);

		// Check if username is successfully extracted and if the user is already
		// authenticated
		// If the check fails, we try to authenticate the user.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Load user details from database.
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			// Check is jwt token is valid
			if (jwtService.isTokenValid(jwt, userDetails)) {
				// If the token is valid, update the security context and send the request to
				// dispatcher servlet

				// This token is needed to update the security context
				// We are passing credentials (second parameter) as null, because user is not
				// authenticated yet
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				// Add additional details.
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Update the security context
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		// We always need to pass to the next filter, so we call filterChain.doFilter
		filterChain.doFilter(request, response);
	}

}
